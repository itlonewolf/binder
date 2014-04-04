package com.example.BinderDemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author yee
 * 主界面
 */
public class MyActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MyActivity" ;

    ServiceConnection mServiceConnection;
    LocalService mLocalService;
    //利用bufferknife省去findviewbyid的麻烦
    @InjectView(R.id.start)
    Button mStart;
    @InjectView(R.id.stop)
    Button mStop;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.inject(this);
        bindListener() ;
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mLocalService = ((LocalService.LocalBinder) iBinder).getService();
                mLocalService.initMember();
                Log.d(TAG, "成功转型");
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d(TAG,"关闭service");
            }
        };
    }


    /**
     * 注册监听器
     */
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
            //开启通知
            case R.id.start:
                mLocalService.openNoti();
                break;
            //关闭通知
            case R.id.stop:
                mLocalService.closeNoti();
                break;
            default:
                break;
        }
    }
}
