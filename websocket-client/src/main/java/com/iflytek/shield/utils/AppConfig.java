package com.iflytek.shield.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by xfgeng on 2018/6/22.
 */
public class AppConfig {

    private static Properties p = new Properties();

    static {
        InputStream in = AppConfig.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return p.getProperty(key);
    }

    public static String getValue(String key,String defaultValue){
        if(null != p.getProperty(key)){
            return p.getProperty(key);
        }
        return defaultValue;
    }

    public static Integer getValue(String key,int defaultValue){
        if(null != p.getProperty(key)){
            return Integer.parseInt(p.getProperty(key));
        }
        return defaultValue;
    }

    public static void main(String[] args) {
        System.out.println(getValue("api.gw.httpPort",1111));

    }

}
