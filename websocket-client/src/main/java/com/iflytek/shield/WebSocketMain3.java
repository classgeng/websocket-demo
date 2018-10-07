package com.iflytek.shield;

import com.google.gson.Gson;
import com.iflytek.shield.enums.ParamPosition;
import com.iflytek.shield.model.WebSocketRequest;
import com.iflytek.shield.utils.RequestUtil;
import com.iflytek.shield.websocket.JavaWebSocketClient;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;

import java.net.URISyntaxException;

/**
 * 模拟api网关后端服务client，请求api网关后端服务
 * Created by xfgeng on 2018/6/4.
 */
public class WebSocketMain3 {

    public static JavaWebSocketClient client;

    static {
        try {
            client = new JavaWebSocketClient("ws://localhost:4979/websocket");
            client.connect();
           /* while(!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
                System.out.println("正在连接...");
            }
            System.out.println("连接成功...");*/
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        for (int i=0;i<3;i++){
            WebSocketRequest webSwocketRequest = new WebSocketRequest();
            String body = "自定义消息,可以是json格式或者二进制格式";
            webSwocketRequest.setBody(body.getBytes("UTF-8"));
            //将对象转成json字符串
            String msg = new Gson().toJson(webSwocketRequest);
            //发送消息
            client.sendMsg(msg);
        }
    }



}
