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
public class WebSocketMain1 {

    public static JavaWebSocketClient client;

    static {
        try {
            String servAddr = "ws://localhost:4999";
            client = new JavaWebSocketClient(servAddr);
            client.connect();
            boolean connectFlag = true;
            long connectTimeout = 5000;
            long startTime = System.currentTimeMillis();
            while(!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
                System.out.println("正在连接...");
                long connectTime = System.currentTimeMillis() - startTime;
                System.out.println("connectTime:"+connectTime);
                if(connectTime > connectTimeout){
                    connectFlag = false;
                    System.out.println("连接超时【servAddr】"+servAddr);
                    break;
                }
            }
            if(connectFlag){
                System.out.println("连接成功...");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread("Thread-1"){
            public void run(){
                try {
                    sendMsg();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread("Thread-2"){
            public void run(){
                try {
                    sendMsg();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    private static void sendMsg() throws Exception {
        for (int i=0;i<1;i++){
            //构建请求报文
            WebSocketRequest webSwocketRequest = RequestUtil.buildWebSocketRequest("/test_websocket");
            //设置body
            String bodyMsg = "要发送的消息内容";
            webSwocketRequest.setBody(bodyMsg.getBytes("UTF-8"));
            //转成json字符串
            String jsonMsg = new Gson().toJson(webSwocketRequest);
            //发送消息
            client.sendMsg(jsonMsg);
        }
    }

}
