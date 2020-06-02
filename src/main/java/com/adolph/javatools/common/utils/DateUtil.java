package com.adolph.javatools.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;


/**
 * 时间计算工具类
 */
public class DateUtil {
    /**
     * date format yyyy-MM-dd HH:mm:ss
     */
    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);


    /**
     * 按传入的格式 返回格式化的当前日期
     *
     * @param pattern
     * @return
     */
    public static String currentDayStr(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 按传入的格式 返回格式化的传入日期
     *
     * @param pattern
     * @return
     */
    public static String currentDayStr(Date date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(date.toInstant());
    }

    /**
     * localDateTime 转 自定义格式string
     *
     * @param localDateTime
     * @param format        例：yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static String formatLocalDateTimeToString(LocalDateTime localDateTime, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return localDateTime.format(formatter);

        } catch (DateTimeParseException e) {
            logger.error("localDateTime类型：{} 转 自定义格式string 失败",localDateTime,e);
        }
        return null;
    }

    /**
     * string 转 LocalDateTime
     *
     * @param dateStr 例："2017-08-11 01:00:00"
     * @param format  例："yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String dateStr, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            logger.error("localDateTime类型：{} 转 {}格式 失败",dateStr,format,e);
        }
        return null;
    }

    public static Timestamp getCurrentDateTime() {
        return new Timestamp(Instant.now().toEpochMilli());

    }

    /**
     * 按传入的格式 返回格式化的传入日期
     *
     * @param pattern
     * @return
     */
    public static String dayStr(Date date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(date.toInstant());
    }

    /**
     * 把日期后的时间归0 变成(yyyy-MM-dd 00:00:00:000)
     *
     * @param date
     * @return LocalDateTime
     */
    public static final LocalDateTime zerolizedTime(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 0, 0, 0, 0);
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        long nanoOfSecond = (date.getTime() % 1000) * 1000000;
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(date.getTime() / 1000, (int) nanoOfSecond, ZoneOffset.of("+8"));
        return localDateTime;
    }

    /**
     * 把时间变成(yyyy-MM-dd 00:00:00:000)
     *
     * @param date
     * @return LocalDateTime
     */
    public static LocalDateTime zerolizedTime(Timestamp date) {
        LocalDateTime localDateTime = timestampToLocalDateTime(date);
        return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 0, 0, 0, 0);
    }

    /**
     * Timestamp 转 LocalDateTime
     *
     * @param date
     * @return LocalDateTime
     */
    public static LocalDateTime timestampToLocalDateTime(Timestamp date) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(date.getTime() / 1000, date.getNanos(), ZoneOffset.of("+8"));
        return localDateTime;
    }
    /**
     * 把日期的时间变成(yyyy-MM-dd 23:59:59:999)
     *
     * @param date
     * @return LocalDateTime
     */
    public static LocalDateTime getEndTime(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 23, 59, 59, 999 * 1000000);
    }

    /**
     * 把时间变成(yyyy-MM-dd 23:59:59:999)
     *
     * @param date
     * @return LocalDateTime
     */
    public static LocalDateTime getEndTime(Timestamp date) {
        LocalDateTime localDateTime = timestampToLocalDateTime(date);
        return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 23, 59, 59, 999 * 1000000);
    }


    /**
     * Date 转 LocalDateTime
     *
     * @param date
     * @return LocalDate
     */
    public static LocalDate dateToLocalDate(Date date) {

        return dateToLocalDateTime(date).toLocalDate();
    }

    /**
     * timestamp 转 LocalDateTime
     *
     * @param date
     * @return LocalDate
     */
    public static LocalDate timestampToLocalDate(Timestamp date) {

        return timestampToLocalDateTime(date).toLocalDate();
    }

    public static void main(String[] args) {
        System.out.println(getCurrentDateTime().getTime()-System.currentTimeMillis());
    }
}
