package com.example.myapplication;

import android.os.Handler;
import android.os.Looper;

/**
 * @author houzhanlei
 * @date 2020-02-25.
 * GitHub：
 * email：
 * description： 原文地址 https://www.jianshu.com/p/83b96506a449
 */
public class CrashCatchManager {

    private CrashHandler mCrashHandler;

    private static CrashCatchManager mInstance;

    private CrashCatchManager(){

    }

    static CrashCatchManager getInstance(){
        if(mInstance == null){
            synchronized (CrashCatchManager.class){
                if(mInstance == null){
                    mInstance = new CrashCatchManager();
                }
            }
        }

        return mInstance;
    }

    public static void init(CrashHandler crashHandler){
        getInstance().setCrashHandler(crashHandler);
    }

    protected void setCrashHandler(CrashHandler crashHandler){

        mCrashHandler = crashHandler;
        //主线程异常拦截
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        if (mCrashHandler != null) {
                            //处理异常
                            mCrashHandler.handlerException(Looper.getMainLooper().getThread(), e);
                        }
                    }
                }
            }
        });

        //所有线程异常拦截，由于主线程的异常都被我们catch住了，所以下面的代码拦截到的都是子线程的异常
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if(mCrashHandler!=null){
                    //处理异常
                    mCrashHandler.handlerException(t,e);
                }
            }
        });


    }

    public interface CrashHandler{
        void handlerException(Thread t,Throwable e);
    }
}
