package com.Gavin.common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: Gavin
 * @description:
 * @className: AsycThreadPoolManager
 * @date: 2022/3/4 9:31
 * @version:0.1
 * @since: jdk14.0
 */

//单例模式（懒汉）
public class AsycThreadPoolManager {
    private volatile static ThreadPoolExecutor threadPoolExecutor;          //线程池
    private volatile static AsycThreadPoolManager threadPoolManager;

    static {
        threadPoolExecutor=new ThreadPoolExecutor(10,
                50,10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(30),new ThreadPoolExecutor.CallerRunsPolicy());
    }

    //单例模式
    public static AsycThreadPoolManager getInstance(){
        if (threadPoolManager==null){
            synchronized (AsycThreadPoolManager.class){                     //同步锁 防止多线程出现多个实例
                if(threadPoolManager==null){
                    threadPoolManager=new AsycThreadPoolManager();
                }
            }
        }
        return threadPoolManager;
    }

    public void executeTask(Runnable task){
        //从池子里面获得一个闲置的线程对象执行任务
            threadPoolExecutor.execute(task);
    }

}
