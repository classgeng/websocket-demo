
package com.iflytek.shield.websocket;

import com.iflytek.websocket.model.WebSocketRequest;
import com.iflytek.websocket.model.WebSocketResponse;

/**
 * 
 * @ClassName: WebSocketCallback
 * @Description: 用于异步调用时的回调逻辑
 * @author xfgeng
 * @date 2018年6月6日 下午1:55:46
 */
public interface WebSocketCallback {

    /**
     * 请求失败
     *
     * @param request
     *            封装后的请求对象，包含部分http相关信息
     * @param e
     *            导致失败的异常
     */
    void onFailure(WebSocketRequest request, Exception e);

    /**
     * 收到应答
     *
     * @param request
     *            封装后的请求对象，包含部分http相关信息
     * @param response
     *            封装后的应答对象，包含部分http相关信息，可以调用.getBody()获取content
     */
    void onResponse(WebSocketRequest request, WebSocketResponse response);
}
