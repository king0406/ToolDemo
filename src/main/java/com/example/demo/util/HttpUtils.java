package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.ApiResult;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author ：jyk
 * @date ：Created in 2020/4/6 20:35
 * @description：
 */
public class HttpUtils {

    public static ApiResult post(String url, Map params) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map> entity = new HttpEntity<>(params, headers);
        ResponseEntity<ApiResult> result = restTemplate.exchange(url, HttpMethod.POST, entity, ApiResult.class);
        return result.getBody();
    }

    public void get(String url) {

    }
}
