
package com.iflytek.shield.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: WebSocketRequest
 * @Description: websocket 请求类
 * @author xfgeng
 * @date 2018年6月5日 下午1:54:10
 */
public final class WebSocketRequest implements Serializable, Cloneable {

    private static final long serialVersionUID = 5736536680650635945L;

    //请求API网关路径
    private String path;

    //请求headers
    private Map<String, String> headers = new HashMap<String, String>();

    //请求消息体
    private byte[] body;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body;
    }
    public void setBody(byte[] body) {
        this.body = body;
    }

}
