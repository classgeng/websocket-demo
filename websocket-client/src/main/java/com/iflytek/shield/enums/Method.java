
package com.iflytek.shield.enums;

/**
 * 
 * @ClassName: Method
 * @Description: Http方法枚举
 * @author dwzhan
 * @date 2017年7月21日 上午10:34:14
 */
public enum Method {
    GET("GET"),

    POST("POST"),

    PUT("PUT"),

    PATCH("PATCH"),

    DELETE("DELETE"),

    HEAD("HEAD");

    private String name;

    Method(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
