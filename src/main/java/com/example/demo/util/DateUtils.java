package com.example.demo.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * @author ：jyk
 * @date ：Created in 2020/2/16 23:09
 * @description：
 */
public class DateUtils {

    public static final String PATTERN = "yyyy/M/d H:m:s";
    public static final String PATTERN2 = "yyyy-M-d H:m:s";
    public static final String YMD = "yyyy-M-d";
    public static final String YMD2 = "yyyy/M/d";

    /**
     * 时区偏移量,中国东8区
     */
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(8);

    private static final DateTimeFormatter YMD_HMS_FORMAT = DateTimeFormatter.ofPattern(PATTERN);
    private static final DateTimeFormatter YMD_HMS_FORMAT2 = DateTimeFormatter.ofPattern(PATTERN2);
    private static final DateTimeFormatter YMD_FORMAT = DateTimeFormatter.ofPattern(YMD);
    private static final DateTimeFormatter YMD_FORMAT2 = DateTimeFormatter.ofPattern(YMD2);

    /**
     * get date today
     *
     * @return
     */
    public static String getToday() {
        return LocalDate.now().toString();
    }

    public static int getYear() {
        return LocalDate.now().getYear();
    }

    public static int getMonth() {
        return LocalDate.now().getMonthValue();
    }

    public static int getDay() {
        return LocalDate.now().getDayOfMonth();
    }

    public static String getDate(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        return date.toString();
    }

    public static boolean equals(LocalDate date1, LocalDate date2) {
        return date1.equals(date2);
    }

    public static boolean equalsMonthDay(LocalDate date1, LocalDate date2) {
        MonthDay monthDay1 = MonthDay.of(date1.getMonthValue(), date1.getDayOfMonth());
        MonthDay monthDay2 = MonthDay.from(date2);
        return monthDay1.equals(monthDay2);
    }

    /**
     * get time now
     *
     * @return
     */
    public static String getTime() {
        return LocalTime.now().toString();
    }

    public static LocalTime addHour(LocalTime time, long hours) {
        return time.plusHours(hours);
    }

    public static LocalDate nextDate(LocalDate date, long value, ChronoUnit chronoUnit) {
        return date.plus(value, chronoUnit);
    }

    public static LocalDate preDate(LocalDate date, long value, ChronoUnit chronoUnit) {
        return date.minus(value, chronoUnit);
    }

    /**
     * get millis now
     *
     * @return
     */
    public static long getMillis() {
        Clock clock = Clock.systemUTC();
        return clock.millis();
    }

    public static long getDefaultMillis() {
        Clock clock = Clock.systemDefaultZone();
        return clock.millis();
    }

    public static boolean isAfter(LocalDate date1, LocalDate date2) {
        return date1.isAfter(date2);
    }

    public static boolean isBefore(LocalDate date1, LocalDate date2) {
        return date1.isBefore(date2);
    }

    public static String getDateInZone(String zone) {
        ZoneId zoneId = ZoneId.of(zone);
        ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.now(), zoneId);
        return dateTime.toString();
    }

    public static void getYearMonth() {
        YearMonth currentYearMonth = YearMonth.now();
        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());
        YearMonth creditCardExpiry = YearMonth.of(2019, Month.FEBRUARY);
        System.out.printf("Your credit card expires on %s %n", creditCardExpiry);
    }

    public static boolean isLeapYear(LocalDate date) {
        return date.isLeapYear();
    }

    @Deprecated
    public static long getBetweenDays(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return period.get(ChronoUnit.DAYS);
    }

    private static long getBetweenDay(LocalDate date1, LocalDate date2) {
        long time1 = date1.atStartOfDay().toEpochSecond(ZONE_OFFSET);
        long time2 = date2.atStartOfDay().toEpochSecond(ZONE_OFFSET);
        if (time1 >= time2) {
            long days = (time1 - time2) / (24 * 3600);
            return days;
        } else {
            long days = (time2 - time1) / (24 * 3600);
            return days;
        }
    }

    public static long getDifDays(String day1, String day2) {
        LocalDate localDate1;
        LocalDate localDate2;
        try {
            localDate1 = LocalDate.parse(day1, YMD_FORMAT);
            localDate2 = LocalDate.parse(day2, YMD_FORMAT);
        } catch (DateTimeParseException e) {
            localDate1 = LocalDate.parse(day1, YMD_FORMAT2);
            localDate2 = LocalDate.parse(day2, YMD_FORMAT2);
        }
        return getBetweenDay(localDate1, localDate2);
    }

    public static long getDifDays(String day) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(day, YMD_FORMAT);
        } catch (DateTimeParseException e) {
            localDate = LocalDate.parse(day, YMD_FORMAT2);
        }
        return getBetweenDay(LocalDate.now(), localDate);
    }

    public static long getTimestamp() {
        return Instant.now().toEpochMilli();
    }

    public static LocalDate parse(String date) {
        LocalDate formatted = LocalDate.parse(date,
                YMD_HMS_FORMAT);
        return formatted;
    }

    public static long getSecs(String date) {
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(date, YMD_HMS_FORMAT);
        } catch (DateTimeParseException e) {
            localDateTime = LocalDateTime.parse(date, YMD_HMS_FORMAT2);
        }
        return localDateTime.toEpochSecond(ZONE_OFFSET);
    }

    public static String convertSec2Date(long sec) {
        Instant instant = Instant.ofEpochSecond(sec);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE_OFFSET);
        return localDateTime.format(YMD_HMS_FORMAT);
    }

    public static String format(LocalDateTime dateTime) {
        return dateTime.format(YMD_HMS_FORMAT);
    }
}
