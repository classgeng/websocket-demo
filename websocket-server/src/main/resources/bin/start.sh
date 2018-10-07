#! /bin/sh

echo "begin to start com.iflytek.websocket.WebSocketStartUp ..."

dir=`pwd`
pid=`ps ax | grep -i 'com.iflytek.websocket.WebSocketStartUp' |grep java| grep $dir | grep -v grep | awk '{print $1}'`
if [ -n "$pid" ] ; then
        echo "WebSocket is already running ..."
        exit -1;
fi

BIN_DIR=$(cd "$(dirname "$0")"; pwd)
APP_DIR=$(cd "$BIN_DIR/.."; pwd)

LogPath=$APP_DIR/logs

if [ ! -d "$LogPath" ]; then
        mkdir "$LogPath"
fi

PRGDIR=`dirname "$PRG"`
BASEDIR=`cd "$PRGDIR/.." >/dev/null; pwd`

JAVA=$JAVA_HOME/bin/java

for i in $APP_DIR/lib/*.jar
do
    CLASSPATH=$CLASSPATH:"$i"
done

export CLASSPATH=.:"$BASEDIR":$CLASSPATH

$JAVA com.iflytek.websocket.WebSocketStartUp -ddir=$dir -cp $CLASSPATH  >> $LogPath/run.log 2>&1 &