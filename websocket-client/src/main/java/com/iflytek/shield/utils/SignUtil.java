package com.iflytek.shield.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 签名工具
 */
public class SignUtil {
    public static final String ENCODING = "UTF-8";
    public static final String HMAC_SHA256 = "HmacSHA256";
    public static final String SEPARATOR = "|";

    /**
     * hmacsha256签名计算
     *
     * @param secret
     *            签名密钥
     * @param url
     *            请求完整路径
     * @param authRandomKey
     *            加密密钥密文
     * @param authNonce
     *            随机数
     * @param authTimestamp
     *            时间戳
     * @param authAppId
     *            appid
     * @param contentMD5
     *            请求体md5
     * @return
     */
    public static String sign(String secret, String url, String authRandomKey, String authNonce,
                              String authTimestamp, String authAppId, String contentMD5) {
        try {
            String[] values = new String[6];
            values[0] = url;
            values[1] = authRandomKey;
            values[2] = authNonce;
            values[3] = authTimestamp;
            values[4] = authAppId;
            values[5] = contentMD5;

            String stringToSign = buildStringToSign(values);
            Mac hmacSha256 = Mac.getInstance(HMAC_SHA256);
            byte[] keyBytes = secret.getBytes(ENCODING);
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, HMAC_SHA256));
            return Base64.encodeBase64String(hmacSha256.doFinal(stringToSign.getBytes(ENCODING)));
            //return new HexBinaryAdapter().marshal(hmacSha256.doFinal(stringToSign.getBytes(ENCODING)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据请求头部元素构建签名字段
     */
    private static String buildStringToSign(String[] headValues) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < headValues.length; i++) {
            if (null != headValues[i] && headValues[i].length()>0) {// 如果为空则跳过
                sb.append(headValues[i]).append(SEPARATOR);
            }
        }
        String stringToSign = sb.substring(0, sb.lastIndexOf(SEPARATOR));
        return stringToSign;
    }

    /**
     * 先进行MD5摘要再进行Base64编码获取摘要字符串
     *
     * @return
     */
    public static String md5ThenBase64(byte[] bytes) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            String newstr = Base64.encodeBase64String(md5.digest(bytes));
            return newstr;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("unknown algorithm MD5");
        }
    }
}
