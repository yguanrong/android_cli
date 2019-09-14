package com.example.guanrong.android_cli.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by GuanRong on 2019/5/28.
 */

public class AppManager {

    private static Stack<Activity> m_stack_activity;
    private static AppManager m_instance;
    private int flag ;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (m_instance == null) {
            m_instance = new AppManager();
        }
        return m_instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (m_stack_activity == null) {
            m_stack_activity = new Stack<Activity>();
        }
        for (Activity activity1: m_stack_activity) {
            if (activity.getClass().equals(activity1.getClass())) {
                finishActivity(activity);
            }
        }
        m_stack_activity.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = m_stack_activity.lastElement();
        return activity;
    }

    /**
     * 获取指定的Activity
     */
    public Activity getActivity(Activity activity) {
        flag = 0;
        for (Activity activity1: m_stack_activity){
            if (activity.getClass().equals(activity1.getClass())){
                flag = 1;
                return activity1;
            }

        }
        if (flag == 0){
            m_stack_activity.add(activity);
        }
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = m_stack_activity.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            m_stack_activity.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : m_stack_activity) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = m_stack_activity.size(); i < size; i++) {
            if (null != m_stack_activity.get(i)) {
                m_stack_activity.get(i).finish();
            }
        }
        MyWebSocket.webSocket.close();
        m_stack_activity.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        MyWebSocket.webSocket.close();
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            assert activityMgr != null;
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
