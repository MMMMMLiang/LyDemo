package com.sun.ly.baselib;

import android.app.Application;

import com.sun.lib.net.SunHttp;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initHttp();
    }

    private void initHttp() {
        SunHttp.Configure.get()
                .showLog(true)
                .init(this);
    }
}
