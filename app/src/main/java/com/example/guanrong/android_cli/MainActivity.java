package com.example.guanrong.android_cli;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.example.guanrong.android_cli.common.AppManager;
import com.example.guanrong.android_cli.common.MyWebSocket;
import com.example.guanrong.android_cli.model.Message;
import com.example.guanrong.android_cli.service.DBUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button send_btn;
    private EditText sendData,receiverId;
    private long backTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backTime=1000;
        init();
    }

    public void init(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                1001, intent, 0);
        MyWebSocket.oncreate(getApplicationContext(),pendingIntent, DBUtil.deviceId);

        send_btn = findViewById(R.id.send_btn);
        receiverId = findViewById(R.id.receiverId);
        sendData = findViewById(R.id.sendData);
        send_btn.setOnClickListener(this);
    }

    //监听Back键按下事件,方法1
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            if (System.currentTimeMillis() - backTime>1000)
            {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                backTime = System.currentTimeMillis();
            }else {
                AppManager.getAppManager().finishAllActivity();
            }
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_btn:
                if (MyWebSocket.webSocket != null){
                    Message message = new Message();
                    message.setData(sendData.getText()+"");
                    message.setType(0);
                    message.setReceiverId(receiverId.getText()+"");
                    message.setSendId(DBUtil.deviceId);
                    MyWebSocket.sendMessage(JSONArray.toJSONString(message));
                }
                break;
        }
    }
}
