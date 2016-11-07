package com.pkh.sopt_semi5_intensive.application;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by kh on 2016. 11. 7..
 */
public class GlobalApplication extends Application {
    private static volatile GlobalApplication instance = null;

    public static GlobalApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        GlobalApplication.instance = this;

        /**
         * 로그인 api 설정
         */
        //facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
