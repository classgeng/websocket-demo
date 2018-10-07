#!/bin/sh

dir=`pwd`
pid=`ps ax | grep -i 'com.iflytek.websocket.WebSocketStartUp' |grep java | grep $dir | grep -v grep | awk '{print $1}'`
if [ -z "$pid" ] ; then
        echo "No WebSocket running."
        exit -1;
fi

echo "The WebSocket(${pid}) is running..."

kill ${pid}

echo "Send shutdown request to WebSocket(${pid}) OK"