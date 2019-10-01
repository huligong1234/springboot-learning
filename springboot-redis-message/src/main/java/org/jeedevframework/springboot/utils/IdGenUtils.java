package org.jeedevframework.springboot.utils;

import java.util.UUID;

/**
 * 自定义ID生成工具类
 *
 */
public class IdGenUtils {

    private static final String TO_BASE_64_URL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";

    private IdGenUtils() {
    }

    /**
     * 封装JDK自带的UUID,并转成22位64进制字符
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        return base64Encode(uuid);
    }

    /**
     * UUID压缩为22位64进制字符
     *
     * @param uuid
     * @return
     */
    public static String base64Encode(UUID uuid) {
        char[] chs = new char[22];
        long most = uuid.getMostSignificantBits();
        long least = uuid.getLeastSignificantBits();
        int k = chs.length - 1;
        for (int i = 0; i < 10; i++, least >>>= 6) {
            chs[k--] = TO_BASE_64_URL.charAt((int) (least & 0x3f));
        }
        chs[k--] = TO_BASE_64_URL.charAt((int) ((least & 0x3f) | ((most & 0x03) << 4)));
        most >>>= 2;
        for (int i = 0; i < 10; i++, most >>>= 6) {
            chs[k--] = TO_BASE_64_URL.charAt((int) (most & 0x3f));
        }
        chs[k] = TO_BASE_64_URL.charAt((int) most);
        return new String(chs);
    }

    /**
     * 把去除-的UUID字符串压缩为22位64进制字符
     *
     * @param uuidStr
     * @return
     */
    public static String base64Encode(String uuidStr) {
        StringBuilder sb = new StringBuilder(uuidStr);
        sb.insert(20, '-');
        sb.insert(16, '-');
        sb.insert(12, '-');
        sb.insert(8, '-');
        UUID uuid = UUID.fromString(sb.toString());
        return base64Encode(uuid);
    }

    /**
     * 压缩的64进制字符还原为32位UUID字符串
     *
     * @param base64
     * @return
     */
    public static String base64Decode(String base64) {
        StringBuilder sb = new StringBuilder();
        for (int i = base64.length(); i > 0; i -= 2) {
            int c1 = TO_BASE_64_URL.indexOf(String.valueOf(base64.charAt(i - 1)));
            int c2 = TO_BASE_64_URL.indexOf(String.valueOf(base64.charAt(i - 2)));
            sb.insert(0, Integer.toHexString(c1 & 0xf));
            sb.insert(0, Integer.toHexString((c1 >>> 4 & 0x3) | (c2 << 2 & 0xc)));
            if (i != 2) {
                sb.insert(0, Integer.toHexString(c2 >>> 2 & 0xf));
            }
        }
        return sb.toString();
    }
}
