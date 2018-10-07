package com.iflytek.websocket.netty;

import com.google.gson.Gson;
import com.iflytek.websocket.model.WebSocketRequest;
import com.iflytek.websocket.model.WebSocketResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理websocket请求的handler
 * Created by xfgeng on 2018/6/5.
 */
public class WebSocketRequestHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channelActive:"+ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channelInactive:"+ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        if (msg instanceof WebSocketFrame) {
            handlerWebSocketFrame(ctx,(WebSocketFrame) msg);
        }else{
            logger.error(ctx.channel().remoteAddress()+"消息格式不支持"+",msg:"+msg);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage());
        ctx.close();
    }

    /**
     * websocket 处理
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame webSocketFrame) throws InterruptedException {
        // 判断是否关闭链路的指令
        if (webSocketFrame instanceof CloseWebSocketFrame) {
            logger.debug("CloseWebSocketFrame...");
            return;
        }
        // 判断是否ping消息
        if (webSocketFrame instanceof PingWebSocketFrame) {
            logger.debug("PingWebSocketFrame...");
            ctx.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
            return;
        }
        if (webSocketFrame instanceof TextWebSocketFrame) {   // 文本消息
            String result = ((TextWebSocketFrame) webSocketFrame).text();
            logger.debug("服务端收到API网关请求消息：" + result);
            WebSocketRequest webSocketRequest = new Gson().fromJson(result, WebSocketRequest.class);
            // 返回应答消息
            String body = "后端服务应答消息";
            WebSocketResponse response = new WebSocketResponse(webSocketRequest.getTerminalNo(),"000000","成功",body.getBytes());
            // 返回【谁发的发给谁】
            String jsonStr = new Gson().toJson(response);
            ctx.channel().writeAndFlush(new TextWebSocketFrame(jsonStr));
        } else if(webSocketFrame instanceof BinaryWebSocketFrame){   //二进制消息
            // 返回【谁发的发给谁】
            ctx.channel().writeAndFlush(webSocketFrame.content());
        }else{
            logger.debug("本例程仅支持文本消息和二进制消息");
            throw new UnsupportedOperationException(String.format("%s frame types not supported", webSocketFrame.getClass().getName()));
        }
    }


}