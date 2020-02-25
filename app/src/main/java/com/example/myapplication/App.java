package com.example.myapplication;

import android.app.Application;
import android.widget.Toast;

/**
 * @author houzhanlei
 * @date 2020-02-25.
 * GitHub：
 * email：
 * description：
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashCatchManager.getInstance().setCrashHandler(new CrashCatchManager.CrashHandler() {
            @Override
            public void handlerException(Thread t, Throwable throwable) {
                //try catch 以防handlerException内部再次抛出异常，导致循环调用handlerException
                try {
                    //TODO 实现自己的异常处理逻辑

                    Toast.makeText(getApplicationContext(), "我捕获到了异常", Toast.LENGTH_LONG).show();

                } catch (Exception e) {

                }
            }
        });
    }
}
