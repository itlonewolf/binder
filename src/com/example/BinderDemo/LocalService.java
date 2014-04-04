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
    static NotificationManager mNotificationManager;
    static Notification mNotification;
    static PendingIntent mIntentContent;
    int mId = 10001;

    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }


    void initMember() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mIntentContent = PendingIntent.getActivity(
                getApplicationContext(),
                mId,
                new Intent(getApplicationContext(), MyActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        mNotification = new NotificationCompat.Builder(getApplicationContext()).setContentTitle("")
                .setContentText("你好").setContentInfo("我是通知栏")
                .setContentIntent(mIntentContent)
                .setSubText("这是子主体")
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("开启通知栏喽^_^")
                .build();
        mNotification.flags |= Notification.FLAG_NO_CLEAR ;
    }

    public void startNoti() {
        mNotificationManager.notify(100,mNotification);
    }

    public void cancelNoti() {
        if (mNotificationManager != null) {
            mNotificationManager.cancelAll();
            Toast.makeText(getApplicationContext(), "关闭通知栏", Toast.LENGTH_LONG).show();
        }
    }


    public class LocalBinder extends Binder{
        LocalService getService(){
            return LocalService.this ;
        }
    }
}
