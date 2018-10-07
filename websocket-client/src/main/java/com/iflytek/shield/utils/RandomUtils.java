package com.iflytek.shield.utils;

/**
 * 随机数工具类
 * Created by jcwang on 2017/7/13.
 */
public class RandomUtils {

    /**
     * 生成加密密钥，6位随机数
     *
     * @return
     */
    public static int generateKeyRadom() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }
}
