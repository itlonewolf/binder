package com.example.BinderDemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyActivity extends Activity implements View.OnClickListener {


    ServiceConnection mServiceConnection;
    LocalService mLocalService;
    @InjectView(R.id.start)
    Button mStart;
    @InjectView(R.id.stop)
    Button mStop;

    //:
    static NotificationManager mNotificationManager;
    static Notification mNotification;
    static PendingIntent mIntentContent;
    int mId = 10001;
    //~


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.inject(this);
        bindListener() ;
        initMember();
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mLocalService = ((LocalService.LocalBinder) iBinder).getService();
                mLocalService.initMember();
                Log.d("myactivity", "成功转型");
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
    }

    //:
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
                .setTicker("开启通知栏喽^_^")
                .setSmallIcon(R.drawable.ic_launcher)
                .build();
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
    //~


    void bindListener(){
        mStart.setOnClickListener(this);
        mStop.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(getApplicationContext(), LocalService.class),
                mServiceConnection,
                Context.BIND_AUTO_CREATE
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                mLocalService.startNoti();
//                startNoti();
                break;
            case R.id.stop:
                mLocalService.cancelNoti();
//                cancelNoti();
                break;
            default:
                break;
        }
    }
}
