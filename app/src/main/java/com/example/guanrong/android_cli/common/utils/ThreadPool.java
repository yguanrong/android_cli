package com.example.guanrong.android_cli.common.utils;

import java.util.concurrent.*;

/**
 * @Author: ygr
 * @Date: 2019/09/02
 * @Description: 注：线程池
 */

public class ThreadPool {

    private ThreadPool(){}

    /**
     * 初始化线程
     */
    private static final ThreadPool threadPool = new ThreadPool();

    /**
     * 获取线程池对象
     * @return threadPool
     */
    public static ThreadPool getInstance(){
        return threadPool;
    }

    /**
     * 创建线程池
     */
    private ExecutorService executorService =Executors.newFixedThreadPool(5);
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    /**
     * 执行线程
     * @param runnable
     */
    public void execute(Runnable runnable) {
        this.executorService.execute(runnable);
    }

    public void executeScheduled(int millSenconds, Runnable runnable){
        this.scheduledExecutorService.schedule(runnable,millSenconds, TimeUnit.MILLISECONDS);
    }
}
