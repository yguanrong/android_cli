package com.example.guanrong.android_cli.common;

import android.app.PendingIntent;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.guanrong.android_cli.model.Message;
import com.example.guanrong.android_cli.service.DBUtil;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;


import java.net.URI;

/**
 * Created by GuanRong on 2019/5/28.
 */

public class MyWebSocket {

    public static WebSocketClient webSocket;

    public static void oncreate(final Context context,final PendingIntent pendingIntent, String userId){

        String url = DBUtil.api + "/websocket/" + userId;
        System.out.println("url = " + url);
        try{
            webSocket = new WebSocketClient(new URI(url)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("连接 " );
                }

                @Override
                public void onMessage(String s) {
                    System.out.println("有新消息 ="+s );
                    Message message = JSONObject.toJavaObject(JSON.parseObject(s),Message.class);
                    NotificationHelper.show(context, message.getSendId(),message.getData(),pendingIntent);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("关闭 " );
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("错误 " );
                }
            };
        }catch (Exception e){
            e.printStackTrace();
        }
        webSocket.connect();

    }

    /**
     * 发送消息
     * @param message
     */
    public static void sendMessage(String message){
        if (webSocket != null){
            webSocket.send(message);
        }
    }

}
