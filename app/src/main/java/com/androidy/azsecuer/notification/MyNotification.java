package com.androidy.azsecuer.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.HomeActivity;

public class MyNotification {
    private static final int ID = 10;
    private static NotificationManager notificationManager;//获取系统管理器

    public static void openNotification(Context context){
         if(notificationManager==null){
             notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
         }
        Notification notification = null;
        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pintent = PendingIntent.getActivity(context ,10,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.activity_notification);
        remoteView.setOnClickPendingIntent(R.id.notification_icon1,pintent);//监听单个控件
        notification = new Notification.Builder(context)
                //禁止滑动删除
                .setOngoing(true)
                .setAutoCancel(true)//点击之后提示信息自动消失
                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(),R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)//设置消息声音和提示灯
                .setContent(remoteView)
                //.setWhen(System.currentTimeMillis())
                .setTicker("有新的消息")
                //.setContentIntent(pintent)
                .build();
       // notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(ID,notification);
    }
    public static void closeNotification(Context context){
        if(notificationManager==null){
            notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        }
        notificationManager.cancel(ID);

    }
}
