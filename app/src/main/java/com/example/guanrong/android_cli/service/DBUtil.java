package com.example.guanrong.android_cli.service;

import android.annotation.SuppressLint;

import com.example.guanrong.android_cli.common.utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by GuanRong on 2019/9/14.
 */

public class DBUtil {

    public static String api= "http://192.168.0.101:8088";

    @SuppressLint("HardwareIds")
    public static final String deviceId = android.os.Build.SERIAL;

    public static String login(String userName, String password,String deviceId){

        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("password", password);
        map.put("deviceId", deviceId);
        String url = api+"/login";
        System.out.println("url = " + url);
        String result = HttpUtil.PostMethod(url,map,"UTF-8");
        System.out.println("result = " + result);
        return result;
    }

    public static String queryDeviceFromRedis(String deviceId){
        String url = api +"/queryDeviceFromRedis?deviceId=" + deviceId;
        System.out.println("url = " + url);
        String result = HttpUtil.GetMethod(url);
        System.out.println("result = " + result);
        return result;
    }
}
