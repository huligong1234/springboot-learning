package org.jeedevframework.springboot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 *
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils {
    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
    private static final char SEPARATOR = '_';
    private static final Pattern IE_PATTERN = Pattern.compile(".*MSIE.*?;.*");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("####.####");
    private static final NumberFormat NUMBER_FORMAT;

    static {
        NUMBER_FORMAT = NumberFormat.getInstance();
        NUMBER_FORMAT.setGroupingUsed(false);
        NUMBER_FORMAT.setMaximumFractionDigits(15);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 浮点数转字符串，去掉无用小数点
     *
     * @param number
     * @return
     */
    public static String decimalFormat(Float number) {
        if (number == null) {
            return EMPTY;
        }

        return DECIMAL_FORMAT.format(number);
    }

    /**
     * 浮点数转字符串，去掉无用小数点
     *
     * @param number
     * @return
     */
    public static String decimalFormat(Double number) {
        if (number == null) {
            return EMPTY;
        }

        return DECIMAL_FORMAT.format(number);
    }

    /**
     * 任意对象转为字符串，数字最高保留小数点前后15位，并去掉无用的小数位0
     *
     * @param object
     * @return
     */
    public static String format(Object object) {
        if (object == null) {
            return EMPTY;
        }

        try {
            return NUMBER_FORMAT.format(object);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return String.valueOf(object);
    }
}
