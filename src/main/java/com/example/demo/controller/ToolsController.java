package com.example.demo.controller;

import com.example.demo.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：jyk
 * @date ：Created in 2020/4/5 20:40
 * @description：
 */
@Slf4j
@RestController
@RequestMapping(value = "/tools")
public class ToolsController {

    @GetMapping(value = "/getMillis")
    public String getMillis(String dateTime) {
        try {
            long val = convert2Millis(dateTime);
            return String.valueOf(val);
        } catch (Exception e) {
            log.error("parse date error, dateTime = " + dateTime, e);
            return "Unsupported date format!";
        }
    }

    @GetMapping(value = "/getSecs")
    public String getSecs(String dateTime) {
        try {
            long val = convert2Millis(dateTime) / 1000;
            return String.valueOf(val);
        } catch (Exception e) {
            log.error("parse date error, dateTime = " + dateTime, e);
            return "Unsupported date format!";
        }
    }

    private long convert2Millis(String dateTime) {
        if (dateTime == null || "".equals(dateTime)) {
            return DateUtils.getMillis();
        }
        return DateUtils.getSecs(dateTime) * 1000;
    }

    @GetMapping(value = "/getDate")
    public String getDate(Long sec) {
        return DateUtils.convertSec2Date(sec);
    }

    @GetMapping(value = "/getDifDays")
    public String getBetweenDay(String day1, String day2) {
        long val;
        try {
            if (day2 == null || "".equals(day2)) {
                val = DateUtils.getDifDays(day1);
            } else {
                val = DateUtils.getDifDays(day1, day2);
            }
        } catch (Exception e) {
            log.error("parse date error, day1=" + day1 + ", day2=" + day2, e);
            return "Unsupported date format!";
        }

        return String.valueOf(val);
    }
}
