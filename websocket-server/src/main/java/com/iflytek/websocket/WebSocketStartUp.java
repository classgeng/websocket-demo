package com.iflytek.websocket;

import com.iflytek.websocket.netty.WebsocketServer;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by xfgeng on 2018/6/5.
 */
public class WebSocketStartUp {
    private static Logger logger = LoggerFactory.getLogger(WebSocketStartUp.class);

    static {
        initLog();
    }

    public static void main(String[] args) {
        /*try {
            int listenPort = 4979;
            logger.info(String.format("WEBSOCKET服务启动成功，监听端口:%s", listenPort));
            new WebsocketServer().start(listenPort);
        }catch (Exception e) {
            logger.error("start error ", e);
        }*/

        System.out.println(System.currentTimeMillis());

    }

    /**
     * 初始化log4j设置，指定配置文件
     *
     * @throws Exception
     */
    private static void initLog() {
        String configPath = new File(WebSocketStartUp.class.getClassLoader().getResource("").getPath()).getPath();//开发环境
        if (configPath.endsWith("bin")) {//linux发布环境在bin目录运行
            configPath = configPath.substring(0, configPath.length() - 4);
        }
        String customizedPath = configPath  + File.separator + "config/log4j.properties";
        PropertyConfigurator.configure(customizedPath);
    }

}
