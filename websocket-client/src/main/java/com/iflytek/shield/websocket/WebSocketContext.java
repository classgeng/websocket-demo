package com.iflytek.shield.websocket;

import com.iflytek.websocket.model.WebSocketRequest;

import java.util.Date;

/**
 * websocket 请求上下文
 * Created by xfgeng on 2018/6/4.
 */
public class WebSocketContext {

    WebSocketCallback callback;
    WebSocketRequest request;
    long startTime;

    public WebSocketContext(WebSocketCallback callback, WebSocketRequest request) {
        this.callback = callback;
        this.request = request;
        this.startTime = (new Date()).getTime();
    }

    public WebSocketCallback getCallback() {
        return callback;
    }

    public void setCallback(WebSocketCallback callback) {
        this.callback = callback;
    }

    public WebSocketRequest getRequest() {
        return request;
    }

    public void setRequest(WebSocketRequest request) {
        this.request = request;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
