package com.iflytek.websocket;

import com.google.gson.Gson;
import com.iflytek.websocket.model.WebSocketRequest;
import com.iflytek.websocket.model.WebSocketResponse;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.CloseReason;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//注解规定了访问的URL
@ServerEndpoint("/websocket")
public class SocketServer {

    /**
     * 客户端有连接的时候就会调用这个方法
     */
    @OnOpen
    public void open(Session session){
        System.out.println(session.getId()+"连接成功，#############");
    }
    /**
     * 客户端连接断开就会调用此方法
     */
    @OnClose
    public void close(Session session,CloseReason reason){
        System.out.println(session.getId() + "连接关闭了");
    }
    
    /**
     * 接收到客户端的信息
     * @param msg
     */
    @OnMessage
    public void message(Session session,String msg){
        System.out.println("客户端说" + msg);
        try {
            WebSocketRequest webSocketRequest = new Gson().fromJson(msg, WebSocketRequest.class);
            // 返回应答消息
            String body = "后端服务应答消息";
            WebSocketResponse response = new WebSocketResponse(webSocketRequest.getTerminalNo(),"000000","成功",body.getBytes("UTF-8"));
            // 返回【谁发的发给谁】
            String jsonStr = new Gson().toJson(response);
            session.getBasicRemote().sendText(jsonStr);

            Thread.sleep(3000);//三秒后再发送一条信息，用于验证是否实现数据实时更新

            // 返回应答消息
            body = "三秒后再发送一条后端服务应答消息";
            response = new WebSocketResponse(webSocketRequest.getTerminalNo(),"000000","成功",body.getBytes("UTF-8"));
            // 返回【谁发的发给谁】
            jsonStr = new Gson().toJson(response);
            session.getBasicRemote().sendText(jsonStr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

     /**
     *错误监听（当没有关闭socket连接就关闭浏览器会异常）
     */
      @OnError
      public void error(Session session, Throwable error){
           String id = session.getId();
           System.out.println("出错的session的id是" + id);
      }

}