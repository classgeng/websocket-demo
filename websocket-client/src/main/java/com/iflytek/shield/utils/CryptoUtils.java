package com.iflytek.shield.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * 加解密工具类 Created by jcwang on 2017/7/6.
 */
public class CryptoUtils {

    public static final String KEY_ALGORITHM_RSA = "RSA";

    public static final String KEY_ALGORITHM_AES = "AES";

    public static final String CIPHER_ALGORITHM_AES = "AES/CBC/PKCS5Padding";

    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    
    public static final int KEY_SIZE_RSA = 512;

    public static final int KEY_SIZE_AES = 128;

    /**
     * aes加密运算
     *
     * @param key
     *            aeskey
     * @param plainText
     *            明文
     * @return
     */
    public static String aesEncode(String key, String plainText) throws Exception {
        return aesEncode(key, plainText.getBytes("UTF-8"));
    }

    public static String aesEncode(String key, byte[] data) throws Exception {
        // 由于操作系统随机数生成策略有差异，不再使用随机密钥
        String aesKey = getAesKey(key);
        byte[] enCodeFormat = aesKey.getBytes("UTF-8");

        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM_AES);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(new byte[16]));
        byte[] result = cipher.doFinal(data);
        return Base64.encodeBase64String(result);
    }

    /**
     * aes解密
     *
     * @param key
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] aesDecode(String key, byte[] content) throws Exception {
        String aesKey = getAesKey(key);
        byte[] enCodeFormat = aesKey.getBytes("UTF-8");
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM_AES);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(new byte[16]));
        byte[] result = cipher.doFinal(Base64.decodeBase64(content));
        return result;
    }

    /**
     * 公钥加密
     *
     * @param publicKey
     *            rsa公钥
     * @param plainText
     *            明文
     * @return
     * @throws Exception
     */
    public static String rsaEncode(String publicKey, String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, restorePublicKey(hexStringToBytes(publicKey)));
        byte[] ci = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.encodeBase64String(ci);
    }

    /**
     * 公钥解密
     *
     * @param publicKey
     * @param encodedText
     * @return
     * @throws Exception
     */
    public static String rsaPubDecode(String publicKey, String encodedText) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, restorePublicKey(hexStringToBytes(publicKey)));
        return new String(cipher.doFinal(Base64.decodeBase64(encodedText)), "UTF-8");
    }

    /**
     * 获取aeske'y'
     *
     * @param str
     *            初始字符串
     * @return
     * @throws Exception
     */
    private static String getAesKey(String str) throws Exception {
        MessageDigest messageDigest = null;
        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(str.getBytes("UTF-8"));
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        md5StrBuff.append(bytesToHexString(byteArray));
        return md5StrBuff.substring(8, 24).toLowerCase();
    }

    public static PublicKey restorePublicKey(byte[] keyBytes) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
        PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
        return publicKey;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
