package com.ssafy.happyhouse.global.batch.service;

import com.ssafy.happyhouse.entity.auction.Auction;
import com.ssafy.happyhouse.entity.auction.AuctionEditor;
import com.ssafy.happyhouse.entity.news.News;
import com.ssafy.happyhouse.entity.news.NewsLetter;
import com.ssafy.happyhouse.global.batch.mapper.BatchMapper;
import com.ssafy.happyhouse.global.common.AESUtil;
import com.ssafy.happyhouse.global.common.GoogleMail;
import com.ssafy.happyhouse.global.crawling.AuctionCrawling;
import com.ssafy.happyhouse.global.crawling.NewsCrawling;
import com.ssafy.happyhouse.repository.AuctionRepository;
import com.ssafy.happyhouse.repository.NewsLetterRepository;
import com.ssafy.happyhouse.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BatchService {

    private final NewsCrawling newsCrawling;
    private final AuctionCrawling auctionCrawling;
    private final GoogleMail googleMail;
    private final NewsRepository newsRepository;
    private final NewsLetterRepository newsLetterRepository;
    private final AuctionRepository auctionRepository;

    /**
     * 당일 부동산 관련 상위 5개의 뉴스를 크롤링한다.
     * @throws Exception
     */
    @Transactional
    public void crawlingNews() throws Exception {

        List<News> newsList = newsCrawling.getRelatedNews();
        try {
            for(News news : newsList) {
                newsRepository.save(news);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Transactional
    public void crawlingAuctionsInfo() throws Exception {

        try {

            String[] courtArr = {"서울중앙지방법원", "서울동부지방법원", "서울서부지방법원", "서울남부지방법원", "서울북부지방법원",
                    "의정부지방법원", "고양지원","남양주지원", "인천지방법원", "부천지원", "수원지방법원", "성남지원", "여주지원",
                    "평택지원", "안산지원", "안양지원", "춘천지방법원", "강릉지원", "원주지원", "속초지원", "영월지원", "청주지방법원"
            };

            String[] courtArr2 = {
                    "충주지원", "제천지원", "영동지원", "대전지방법원", "홍성지원", "논산지원","천안지원","공주지원","서산지원","대구지방법원",
                    "안동지원","경주지원","김천지원","상주지원","의성지원","영덕지원","포항지원","대구서부지원","부산지방법원","부산동부지원",
                    "부산서부지원","울산지방법원","창원지방법원", "마산지원","진주지원","통영지원","밀양지원","거창지원","광주지방법원","목포지원",
                    "장흥지원","순천지원","해남지원","전주지방법원","군산지원","정읍지원","남원지원","제주지방법원"
            };

            List<Auction> auctionList = new ArrayList<>();
            for(String court : courtArr) {
                log.info("=========================== 크롤링 지방법웝 " + court);
                auctionList.addAll(auctionCrawling.getAuctionInfo(court));
            }
            for(Auction auction : auctionList) {
                auctionRepository.save(auction);
            }
            auctionList = new ArrayList<>();
            for(String court : courtArr2) {
                log.info("=========================== 크롤링 지방법웝 " + court);
                auctionList.addAll(auctionCrawling.getAuctionInfo(court));
            }
            for(Auction auction : auctionList) {
                auctionRepository.save(auction);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  전송되지 않은 이메일을 찾아서 전송한다.
     */
    @Transactional
    public void sendEmail() throws Exception {
        List<NewsLetter> list = newsLetterRepository.findNewslettersNotSent();
        for (NewsLetter newsLetter : list) {
            sendToRecipients(newsLetter);
        }
        for (NewsLetter newsLetter : list) {
            newsLetter.updateIsSendStatus();
        }
    }

    private void sendToRecipients(NewsLetter newsLetter) throws Exception {
        String[] recipients = newsLetter.getRecipients().split(",");
        for(String recipient : recipients) {
            googleMail.sendmail(recipient, newsLetter.getTitle(), newsLetter.getContent());
        }
    }

    /**
     * 주소를 기반으로 경매정보의 위도,경도를 수정한다.
     */
    @Transactional
    public void setAuctionInfo() {

        List<Auction> auctions =  auctionRepository.findAuctionsByLngIsNull();

        String clientId = "gksinmkzt9"; // 네이버 클라우드 플랫폼에서 발급받은 Client ID
        String clientSecret = "9AxOiZX3Fj564Ofemm5Z0eOkhtuOPuJZSd3kcQfQ"; // 네이버 클라우드 플랫폼에서 발급받은 Client Secret
        try {

            for(Auction auction : auctions) {

                String address = auction.getLocation();
                String encodedAddress = URLEncoder.encode(address, "UTF-8");
                String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + encodedAddress;

                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
                con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else { // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }

                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();

                log.info("Response: " + response.toString());

                // JSON 파싱
                JSONObject jsonObj = new JSONObject(response.toString());
                JSONArray addresses = jsonObj.getJSONArray("addresses");
                if (addresses.length() > 0) {
                    JSONObject firstAddress = addresses.getJSONObject(0);
                    double lat = firstAddress.getDouble("x");
                    double lng = firstAddress.getDouble("y");

                    String sido = "";
                    String gugun = "";
                    String dongmyun = "";

                    JSONArray addressElements = firstAddress.getJSONArray("addressElements");
                    for (int i = 0; i < addressElements.length(); i++) {
                        JSONObject element = addressElements.getJSONObject(i);
                        JSONArray types = element.getJSONArray("types");
                        for (int j = 0; j < types.length(); j++) {
                            String type = types.getString(j);
                            if (type.equals("SIDO")) {
                                sido = element.getString("longName");
                            } else if (type.equals("SIGUGUN")) {
                                gugun = element.getString("longName");
                            } else if (type.equals("DONGMYUN")) {
                                dongmyun = element.getString("longName");
                            }
                        }
                    }

                    AuctionEditor auctionEditor = AuctionEditor.builder()
                            .lat(String.valueOf(lat))
                            .lng(String.valueOf(lng))
                            .sido(sido)
                            .gugun(gugun)
                            .dong(dongmyun)
                            .build();

                    auction.updateLocation(auctionEditor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
