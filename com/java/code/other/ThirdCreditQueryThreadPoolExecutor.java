package com.pingan.rcpm.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 用于异步调用外联平台前海、同盾征信查询接口
 * Created by XUXIANGRUI736 on 2017/11/6.
 */
public class ThirdCreditQueryThreadPoolExecutor {
    /**
     * 第三方征信查询专用线程池。
     */
    private static ThreadPoolExecutor threadPool;

    static{
        threadPool = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(30),new ThreadPoolExecutor.DiscardPolicy());
    }


    private static void init(){
        threadPool = new ThreadPoolExecutor(3, 10, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(30),new ThreadPoolExecutor.DiscardPolicy());
    }


    public static void execute(Runnable job){
        if(threadPool == null){
            init();
        }
        threadPool.execute(job);
    }

}
