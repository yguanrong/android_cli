package com.example.guanrong.android_cli.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.guanrong.android_cli.MainActivity;
import com.example.guanrong.android_cli.R;
import com.example.guanrong.android_cli.service.DBUtil;
import com.example.guanrong.android_cli.common.utils.ThreadPool;

/**
 * Created by GuanRong on 2019/9/14.
 */


public class PreOpenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_open);
        init();
    }
    private void init(){

        ThreadPool.getInstance().execute(new Thread(
                ()->{
                    String string = DBUtil.queryDeviceFromRedis(DBUtil.deviceId);
                    handlerSendMsg(1001,string);
                }));
    }


    private void handlerSendMsg(int what, String result){
        Message msg = new Message();
        msg.what = what;
        Bundle data = new Bundle();
        data.putString("result",result);
        msg.setData(data);
        mHandler.sendMessage(msg);
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){

        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 1001:
                    String string= msg.getData().getString("result");
                    if ("1".equals(string)){
                        Toast.makeText(getApplicationContext(), "已经登录了", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(getApplication(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
