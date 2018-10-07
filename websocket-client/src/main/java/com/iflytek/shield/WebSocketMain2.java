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
 * 模拟api网关client，请求api网关
 * Created by xfgeng on 2018/6/5.
 */
public class WebSocketMain2 {

    public static WebSocketClient client;

    static {
        try {
            client = new JavaWebSocketClient("ws://10.4.4.54:4999");
            client.connect();
            while(!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
                System.out.println("正在连接...");
            }
            System.out.println("连接成功...");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i=0;i<1;i++){
            //构建请求报文
            WebSocketRequest webSwocketRequest = RequestUtil.buildWebSocketRequest("/test_websocket");
            //设置body
            String bodyMsg = "要发送的消息内容";
            webSwocketRequest.setBody(bodyMsg.getBytes("UTF-8"));
            //body加密
            RequestUtil.cryptBody(webSwocketRequest);
            //签名
            RequestUtil.sign(webSwocketRequest);
            //转成json字符串
            String jsonMsg = new Gson().toJson(webSwocketRequest);
            //发送消息
            client.send(jsonMsg);
        }
    }


}
