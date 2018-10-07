package com.iflytek.shield.websocket;

import com.google.gson.Gson;
import com.iflytek.websocket.enums.Scheme;
import com.iflytek.websocket.model.WebSocketRequest;
import com.iflytek.websocket.model.WebSocketResponse;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by xfgeng on 2018/6/4.
 */
public class JavaWebSocketClient extends WebSocketClient {

    //private static CallbackManager callbackManager;

    public JavaWebSocketClient(String url) throws URISyntaxException {
        super(new URI(url));
    }

    @Override
    public void onOpen(ServerHandshake shake) {
        System.out.println("连接建立，【URL】"+this.getURI());
    }

    @Override
    public void onMessage(String paramString) {
        System.out.println("接收到消息："+paramString);
        WebSocketResponse webSocketResponse = new Gson().fromJson(paramString, WebSocketResponse.class);
        String body = null;
        try {
            body = new String(webSocketResponse.getBody(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("code:"+webSocketResponse.getRespCode()+",message:"+webSocketResponse.getRespMsg()+",body:"+body);
    }

    @Override
    public void onClose(int paramInt, String paramString, boolean paramBoolean) {
        System.out.println("连接关闭，【URL】"+this.getURI());
        System.out.println("[paramInt]:"+paramInt+",[paramString]:"+paramString+",[paramBoolean]:"+paramBoolean);
    }

    @Override
    public void onError(Exception e) {
        System.out.println("异常"+e);

    }

    /**
     * 发送异步请求
     * @param webSocketRequest
     * @param webSocketCallback
     */
    /*public void sendAsyncRequest(WebSocketRequest webSocketRequest, WebSocketCallback webSocketCallback) {
        String requestNo = webSocketRequest.getHeaders().get("S-Ca-RequestNo");
        this.callbackManager.add(requestNo, new WebSocketContext(webSocketCallback, webSocketRequest));
        String requestStr = new Gson().toJson(webSocketRequest);
        this.send(requestStr);
    }*/

    public void sendMsg(String msg) {
        if(this.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            this.send(msg);
        }else{
            System.out.println("发送失败...");
        }
    }

}
