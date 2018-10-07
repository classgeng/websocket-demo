
package com.iflytek.websocket.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: WebSocketRequest
 * @Description: websocket 请求类
 * @author xfgeng
 * @date 2018年6月5日 下午1:54:10
 */
public final class WebSocketRequest implements Serializable, Cloneable {

    private static final long serialVersionUID = 5736536680650635945L;

    /**
     * 终端号，唯一
     */
    private String terminalNo;

    private byte[] body;

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

}
