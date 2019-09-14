package com.example.guanrong.android_cli.activity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guanrong.android_cli.MainActivity;
import com.example.guanrong.android_cli.R;
import com.example.guanrong.android_cli.common.MyWebSocket;
import com.example.guanrong.android_cli.service.DBUtil;
import com.example.guanrong.android_cli.common.utils.ThreadPool;

/**
 * Created by GuanRong on 2019/9/14.
 */


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userName, password;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        userName = findViewById(R.id.user_name);
        password = findViewById(R.id.user_password);
        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                final String username = userName.getText()+"";
                final String pw = password.getText()+"";

                ThreadPool.getInstance().execute(new Thread(
                        ()->{
                            String string = DBUtil.login(username, pw, DBUtil.deviceId);
                            handlerSendMsg(1001,string);
                        }));
                break;
        }
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
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
