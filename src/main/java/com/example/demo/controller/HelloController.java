package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：jyk
 * @date ：Created in 2020/4/5 19:47
 * @description：
 */
@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String hello() {
        return "hello world";
    }

    @RequestMapping(value = "/xap", method = RequestMethod.GET)
    public String xap() {
        return "小阿胖，这是小金的云服务，代号pbb。" +
                "以后你想要常见的工具类接口都可以给我提需求噢，前端正在努力开发中。";
    }
}
