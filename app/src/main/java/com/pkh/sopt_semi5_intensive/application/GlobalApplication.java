package com.pkh.sopt_semi5_intensive.application;

import android.app.Activity;
import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.kakao.auth.KakaoSDK;
import com.pkh.sopt_semi5_intensive.main.presenter.KakaoSDKAdapter;

/**
 * Created by kh on 2016. 11. 7..
 */
public class GlobalApplication extends Application {
    private static volatile GlobalApplication instance = null;
    private static volatile Activity currentActivity = null;

    public static GlobalApplication getInstance() {
        return instance;
    }
    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }
    /**
     * singleton 애플리케이션 객체를 얻는다.
     * @return singleton 애플리케이션 객체
     */
    public static GlobalApplication getGlobalApplicationContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
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


        //kakao
        KakaoSDK.init(new KakaoSDKAdapter());
    }
}
