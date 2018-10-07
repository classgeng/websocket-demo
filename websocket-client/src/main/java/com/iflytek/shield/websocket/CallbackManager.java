package com.iflytek.shield.websocket;

import com.iflytek.websocket.model.WebSocketResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xfgeng on 2018/6/26.
 */
public class CallbackManager implements Runnable{
    private static Logger logger = LoggerFactory.getLogger(CallbackManager.class);

    private static ConcurrentHashMap<String, WebSocketContext> callbacks = new ConcurrentHashMap();
    private static ExecutorService fixThreadPool;
    CountDownLatch countDownLatch = new CountDownLatch(1);
    private static final long CHECK_EXPIRE_INTERVAL = 500L;
    private static int requestExpiredTime = 10000;

    public CallbackManager(int callbackThreadPoolCount,int requestExpiredTimep) {
        this.requestExpiredTime = requestExpiredTimep;
        fixThreadPool = Executors.newFixedThreadPool(callbackThreadPoolCount);
    }

    public void add(String seq, WebSocketContext context) {
        this.callbacks.put(seq, context);
        if(this.countDownLatch != null && this.countDownLatch.getCount() == 1L) {
            this.countDownLatch.countDown();
        }
    }

    public void callback(String seq, final WebSocketResponse response) {
        final WebSocketContext apiContext = this.callbacks.remove(seq);
        if(null != apiContext) {
            fixThreadPool.execute(new Runnable() {
                public void run() {
                    try {
                        apiContext.getCallback().onResponse(apiContext.getRequest(), response);
                    } catch (Exception var2) {
                        logger.error("SDK", "Callback failed", var2);
                    }

                }
            });
        }
    }

    @Override
    public void run() {
        while(true) {
            Long current = Long.valueOf((new Date()).getTime());
            ArrayList<Integer> toBeRemove = new ArrayList();

            Iterator var3 = this.callbacks.entrySet().iterator();
            while(var3.hasNext()) {
                Map.Entry<Integer, WebSocketContext> callback = (Map.Entry)var3.next();
                WebSocketContext context = callback.getValue();
                if(current.longValue() - context.getStartTime() > (long)this.requestExpiredTime) {
                    context.getCallback().onFailure(context.getRequest(), new Exception("Get Response Timeout"));
                    toBeRemove.add(callback.getKey());
                }
            }

            var3 = toBeRemove.iterator();
            while(var3.hasNext()) {
                Integer key = (Integer)var3.next();
                this.callbacks.remove(key);
            }

            try {
                if(this.callbacks.size() == 0) {
                    this.countDownLatch = new CountDownLatch(1);
                    this.countDownLatch.await();
                }
                Thread.sleep(CHECK_EXPIRE_INTERVAL);
            } catch (Exception var6) {
                logger.error("SDK", "Check callback expired", var6);
            }
        }
    }


}
