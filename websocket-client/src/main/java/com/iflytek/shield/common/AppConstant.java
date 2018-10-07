package com.iflytek.shield.common;

import com.iflytek.shield.utils.AppConfig;

/**
 * Created by xfgeng on 2018/6/22.
 */
public interface AppConstant {

    String API_GW_SCHEME = "WS";

    String API_GW_APPID = AppConfig.getValue("api.gw.appId");
    String API_GW_APPSECRET = AppConfig.getValue("api.gw.appSecret");
    String API_GW_PUBLICKEY = AppConfig.getValue("api.gw.publicKey");
    String API_GW_HOST = AppConfig.getValue("api.gw.host");
    Integer API_GW_HTTPPORT = AppConfig.getValue("api.gw.httpPort",4989);
    Integer API_GW_HTTPSPORT = AppConfig.getValue("api.gw.httpsPort",443);
    Integer API_GW_WEBSOCKETPORT = AppConfig.getValue("api.gw.websocketPort",4999);
    String API_GW_STAGE = AppConfig.getValue("api.gw.stage");

}
