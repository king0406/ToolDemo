package com.example.demo.controller;

import com.example.demo.encrypt.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author ：jyk
 * @date ：Created in 2020/5/7 17:30
 * @description：
 */
@Slf4j
@RestController
@RequestMapping(value = "/aes")
public class AesController {

    public static String taxKey = "123456";

    @GetMapping(value = "/encrypt")
    public String encrypt(String content, String key) {
        try {
            return AesUtil.encode(content, key);
        } catch (UnsupportedEncodingException e) {
            log.error("加密失败" + e.getMessage(), e);
            return e.getMessage();
        }
    }

    @GetMapping(value = "/decrypt")
    public String decrypt(String content, String key) {
        try {
            return AesUtil.decode(content, key);
        } catch (UnsupportedEncodingException e) {
            log.error("解密失败" + e.getMessage(), e);
            return e.getMessage();
        }
    }

    @GetMapping(value = "/encode")
    public String encrypt(String content) {
        try {
            return AesUtil.encode(content, taxKey);
        } catch (UnsupportedEncodingException e) {
            log.error("加密失败" + e.getMessage(), e);
            return e.getMessage();
        }
    }

    @GetMapping(value = "/decode")
    public String decrypt(String content) {
        try {
            return AesUtil.decode(content, taxKey);
        } catch (UnsupportedEncodingException e) {
            log.error("解密失败" + e.getMessage(), e);
            return e.getMessage();
        }
    }

    @PostMapping(value = "/modifyKey")
    public String modifyTaxKey(String key) {
        taxKey = key;
        return "modify ok";
    }
}
