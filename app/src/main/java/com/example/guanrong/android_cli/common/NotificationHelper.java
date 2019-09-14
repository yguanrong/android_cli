package com.example.guanrong.android_cli.common;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.guanrong.android_cli.R;

/**
 * Created by GuanRong on 2019/5/28.
 */

public class NotificationHelper {
    private static final String CHANNEL_ID="channel_id";   //通道渠道id
    private static final String  CHANEL_NAME="chanel_name"; //通道渠道名称


    @TargetApi(Build.VERSION_CODES.O)
    public static void show(Context context, String title,
                              String contenText, PendingIntent pendingIntent){
        NotificationChannel channel = null;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            //创建 通知通道  channelid和channelname是必须的（自己命名就好）
            channel = new NotificationChannel(CHANNEL_ID, CHANEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);//是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.RED);//小红点颜色
            channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
        }
        Notification notification;
        //获取Notification实例   获取Notification实例有很多方法处理
        // 在此我只展示通用的方法（虽然这种方式是属于api16以上，但是已经可以了，
        // 毕竟16以下的Android机很少了，如果非要全面兼容可以用）

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //向上兼容 用Notification.Builder构造notification对象
            notification = new Notification.Builder(context,CHANNEL_ID)
                    .setContentTitle(title) //通知标题
                    .setContentText(contenText) //通知内容
                    .setShowWhen(true)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.tubiao)

                    .setContentIntent(pendingIntent)//点击通知事件
                    .setColor(Color.parseColor("#FEDA26"))
                    .setLargeIcon(BitmapFactory
                            .decodeResource(context.getResources(),R.drawable.tubiao))
                    .setTicker("")
                    .build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
        }else {
            //向下兼容 用NotificationCompat.Builder构造notification对象
            notification = new NotificationCompat.Builder(context)
                    .setContentTitle(title) //通知标题
                    .setContentText(contenText) //通知内容
                    .setShowWhen(true)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.tubiao)
                    .setColor(Color.parseColor("#FEDA26"))
                    .setLargeIcon(BitmapFactory
                            .decodeResource(context.getResources(),R.drawable.tubiao))
                    .setTicker("")
                    .build();
        }

        //发送通知
        int  notifiId=2;
        //创建一个通知管理器
        NotificationManager   notificationManager=
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            assert notificationManager != null;
            assert channel != null;
            notificationManager.createNotificationChannel(channel);
        }
        assert notificationManager != null;
        notificationManager.notify(notifiId,notification);

    }

}
