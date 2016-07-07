package com.fast.condition;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xjt520 on 16/1/1.
 */
public class DateKit {

    public static String format(final Date source, String pattern) {
        if (source == null) {
            return null;
        }
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(source);
    }

    public static Date parse(final String source, String pattern) throws ParseException {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(source);
    }

    /**
     * 格式化到天
     */
    public static String formatToDay(final Date source) {
        return format(source, "yyyy-MM-dd");
    }
}
