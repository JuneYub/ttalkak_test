package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.board.Board;

import java.util.List;
import java.util.Map;


public interface BoardMapper {

    void writeBoard(Map<String, String> map);

    List<Board> findAll();

    Board findById(Long id);

    void updateBoard(Map<String, Object> map);

    void deleteBoard(Long id);
}
