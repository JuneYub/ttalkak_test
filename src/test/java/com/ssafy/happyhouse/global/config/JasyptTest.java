package com.ssafy.happyhouse.global.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JasyptTest {


    @Test
    public void jasyptTest(){

        String password = "PASSWORD";

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(4);
        encryptor.setPassword(password);
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

        String content = "jdbc:mysql://database.ttalkak.com:13875/ab246f5899?useSSL=false";
        String encryptedContent = encryptor.encrypt(content);
        String decryptedContent = encryptor.decrypt(encryptedContent);

        System.out.println("Enc : " + encryptedContent + ", Dec : " + decryptedContent);
    }
}