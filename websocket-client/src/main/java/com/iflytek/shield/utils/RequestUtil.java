package com.iflytek.shield.utils;

import com.iflytek.shield.common.AppConstant;
import com.iflytek.shield.model.WebSocketRequest;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import sun.misc.IOUtils;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 请求报文封装工具类
 */
public class RequestUtil {

    public static WebSocketRequest buildWebSocketRequest(String path) throws Exception {
        WebSocketRequest webSwocketRequest = new WebSocketRequest();
        //设置请求api path
        webSwocketRequest.setPath(path);
        //API网关基本参数headers
        String authNonce = UUID.randomUUID().toString();
        String authTimestamp = System.currentTimeMillis() + "";
        webSwocketRequest.getHeaders().put("S-Auth-Nonce", authNonce);
        webSwocketRequest.getHeaders().put("S-Auth-Version", "1");
        webSwocketRequest.getHeaders().put("S-Auth-Timestamp", authTimestamp);
        //webSwocketRequest.getHeaders().put("S-Auth-GroupId", "1");
        webSwocketRequest.getHeaders().put("User-Agent", "ShieldJavaSDK");
        webSwocketRequest.getHeaders().put("S-SDK-Version", "1.3");
        String contentMD5 = "";
        if(null != webSwocketRequest.getBody()) {
            contentMD5 = SignUtil.md5ThenBase64(webSwocketRequest.getBody());
            webSwocketRequest.getHeaders().put("S-Content-MD5", contentMD5);
        }
        webSwocketRequest.getHeaders().put("S-Auth-Stage", AppConstant.API_GW_STAGE);
        webSwocketRequest.getHeaders().put("S-Auth-AppId", AppConstant.API_GW_APPID);
        return webSwocketRequest;
    }

    /**
     *
     * @Title: cryptRequest
     * @Description: 加密请求
     * @return webSwocketRequest
     * @date 2017年8月1日 下午4:22:28
     * @throws
     */
    public static void cryptBody(WebSocketRequest webSwocketRequest) throws Exception {
        //step1：生一个随机数
        String randomKey = RandomUtils.generateKeyRadom() + "";
        //step2：使用公钥加密随机数，并保持到header中
        String rsaRandomKey = CryptoUtils.rsaEncode(AppConstant.API_GW_PUBLICKEY, randomKey);
        webSwocketRequest.getHeaders().put("S-Auth-RandomKey", rsaRandomKey);
        //step3：加密body的二进制数组
        if (null != webSwocketRequest.getBody() && webSwocketRequest.getBody().length > 0) {
            String base64 = CryptoUtils.aesEncode(randomKey, webSwocketRequest.getBody());
            webSwocketRequest.setBody(base64.getBytes("UTF-8"));
        }
    }

    /**
     *
     * @Title: cryptRequest
     * @Description: 签名
     * @return webSwocketRequest
     * @date 2017年8月1日 下午4:22:28
     * @throws
     */
    public static void sign(WebSocketRequest webSwocketRequest){
        Map<String, String> headers = webSwocketRequest.getHeaders();
        String authRandomKey = headers.get("S-Auth-RandomKey");
        String authNonce = headers.get("S-Auth-Nonce");
        String authTimestamp = headers.get("S-Auth-Timestamp");
        String contentMD5 = headers.get("S-Content-MD5");
        //签名计算
        String signature = SignUtil.sign(AppConstant.API_GW_APPSECRET, webSwocketRequest.getPath(),authRandomKey ,
                authNonce, authTimestamp, AppConstant.API_GW_APPID, contentMD5);
        webSwocketRequest.getHeaders().put("S-Auth-Signature", signature);
    }

}
