package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.ApiResult;
import com.example.demo.util.DateUtils;
import com.example.demo.util.HttpUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.annotation.Documented;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplicationTests.class)
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testDate() {
        System.out.println(DateUtils.getMillis());
        System.out.println(DateUtils.getDefaultMillis() / 1000);
        System.out.println(DateUtils.getTimestamp());
        System.out.println(DateUtils.getDifDays("2019-02-03"));
        System.out.println(DateUtils.getSecs("2020/4/6 1:12:34"));
        System.out.println(DateUtils.convertSec2Date(1586106754L));
    }

    @Test
    public void test() {
        float a = 0.125f; double b = 0.125d; System.out.println((a - b) == 0.0);
    }

    void g(Double s) {
        System.out.println("string");
    }

    void g(Integer s) {
        System.out.println("Integer");
    }

    @Test
    public void testHttp() {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=be811a63-dd76-4318-83a8-3511f9c8c6d8";
        Map<String, Object> params = new LinkedHashMap<>(2);
        params.put("msgtype", "text");
        JSONObject text = new JSONObject();
        text.put("content", "good morning!");
        params.put("text", text);
        ApiResult apiResult = HttpUtils.post(url, params);
        if (apiResult.getCode() == 200) {
            System.out.println(apiResult.getData());
        } else {
            System.out.println("error");
        }

    }
}
