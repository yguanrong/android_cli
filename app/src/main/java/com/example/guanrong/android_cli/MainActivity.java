package com.example.guanrong.android_cli;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private long backTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backTime=1000;
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
                super.onPause();
                super.onStop();
                super.finish();
            }
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
