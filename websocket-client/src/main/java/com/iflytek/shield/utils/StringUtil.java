package com.iflytek.shield.utils;


import com.iflytek.shield.common.AppConstant;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * 字符串工具
 */
public class StringUtil {

    /*
     * 拼接URL
     * HTTP + HOST + PATH(With pathparameter) + Query Parameter
     */
    public static String buildUri(String path,Map<String, String> querys) {
        try {
            URIBuilder uriBuilder = new URIBuilder();
            uriBuilder.setScheme(AppConstant.API_GW_SCHEME);
            uriBuilder.setHost(AppConstant.API_GW_HOST);
            uriBuilder.setPort(AppConstant.API_GW_WEBSOCKETPORT);
            uriBuilder.setPath(path);

            if (null != querys && querys.size() > 0) {
                for (Map.Entry<String, String> entry : querys.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            URI uri = uriBuilder.build();
            String url = uri.getRawPath();
            if (null != uri.getRawQuery() && uri.getRawQuery().length() > 0) {
                url = url + "?" + uri.getRawQuery();
            }
            return url;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }


}
