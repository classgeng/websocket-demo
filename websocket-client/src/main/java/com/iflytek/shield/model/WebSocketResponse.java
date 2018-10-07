package com.iflytek.websocket.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @ClassName: WebSocketResponse
 * @Description: api 返回
 * @author xfgeng
 * @date 2018年6月5日 下午1:56:28
 */
public final class WebSocketResponse implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    public WebSocketResponse(String terminalNo,String respCode,String respMsg){
        this.terminalNo = terminalNo;
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    /**
     * 终端号，唯一
     */
    private String terminalNo;

    /**
     * 响应码
     */
    private String respCode;

    /**
     * 响应消息
     */
    private String respMsg;

    /**
     * contentType 信息
     */
    private String contentType;

    /**
     * 头信息
     */
    private Map<String, String> headers;

    /**
     * 具体的消息体二进制数组
     */
    private byte[] body;

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getContentType() {
        return contentType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "WebSocketResponse{" + "respCode=" + respCode + "respMsg=" + respMsg + ", contentType='" + contentType + '\'' + ", body=" + Arrays.toString(body)
                + ", headers=" + headers + '}';
    }
}
