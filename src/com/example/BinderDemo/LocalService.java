package com.example.BinderDemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by yee on 4/4/14.
 */
public class LocalService extends Service {

    private static final String TAG = "LocalService";
    //通知管理器
    static NotificationManager mNotificationManager;
    //通知
    static Notification mNotification;
    //pendingintent
    static PendingIntent mIntentContent;
    //id
    int mId = 10001;
    //RequestCode
    int mRequestCode = 10002;

    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }


    /**
     * 初始化成员变量
     */
    void initMember() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mIntentContent = PendingIntent.getActivity(
                getApplicationContext(),
                mRequestCode,
                new Intent(getApplicationContext(), MyActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        mNotification = new NotificationCompat.Builder(getApplicationContext()).setContentTitle("呵呵")
                .setContentText("你好").setContentInfo("我是通知栏")
                .setContentIntent(mIntentContent)
                .setSubText("这是子主题")
                //一定要设置一个smallicon，否则。。。无显示
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("开启通知栏喽^_^")
                .build();
        //设置通知栏为无法清除
        mNotification.flags |= Notification.FLAG_NO_CLEAR ;
    }

    /**
     * 打开通知栏
     */
    public void openNoti() {
        mNotificationManager.notify(mId,mNotification);
    }

    /**
     * 关闭通知栏
     */
    public void closeNoti() {
        if (mNotificationManager != null) {
            mNotificationManager.cancel(mId);
            Toast.makeText(getApplicationContext(), "关闭通知栏", Toast.LENGTH_LONG).show();
        }
    }

    public class LocalBinder extends Binder{
        LocalService getService(){
            //返回LocalService的引用
            return LocalService.this ;
        }
    }
}