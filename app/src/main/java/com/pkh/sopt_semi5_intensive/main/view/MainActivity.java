package com.pkh.sopt_semi5_intensive.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.pkh.sopt_semi5_intensive.R;
import com.pkh.sopt_semi5_intensive.main.model.Token;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView{

    @BindView(R.id.facebook_login_button)
    LoginButton facebookBtn;



    //페이스북 콜백메소드
    CallbackManager mFacebookCallbackManager;


    //Back 키 두번 클릭 여부 확인
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        /**
         * 심화 세미나에서는
         * 0. mvp패턴을 사용해보자
         * 1. 페이스북 로그인을 구현해보자.
         * 2. 카카오톡 로그인 및 링크 공유하기를 구현해보자.
         */

        /**
         * 로그인 연동을 하기위해서는 디바이스에 해당 어플리케이션이 설치되어있어야 합니다. ex)카카오톡, 페이스북
         */

        /**
         * 먼저, 페이스북 로그인부터 진행하겠습니다.
         */

        /**
         * 1. https://developers.facebook.com/quickstarts/?platform=android
         * 빠른 시작으로 진행하겠습니다
         * 1-1. 새 앱ID를 생성합니다.
         * 1-2. 페이스북 가이드에 맞게 gradle 설정을 진행합니다.
         * 1-3. string.xml 및 Manifest.xml에 설정 값 추가
         * 1-4. 패키지 등록
         * 1-5. 해시키 등록
         */

        /**
         * 2. layout.xml에 로그인 버튼 추가
         * 3. application을 상속받은 클래스파일을 생성하여 어플리케이션 초기 실행 시, 페이스북설을 초기화하도록 설정
         * 4. 로그인 버튼의 콜백 메소드 설정
         * 5. onActivityResult 에 다시 액티비티 돌아온 결과에 따른 메소드를 설정해주어야 한다.
         */

        facebookBtn.setReadPermissions("public_profile","email");
        // If using in a fragment
//        facebookBtn.setFragment(this);
        // Other app specific specialization


        mFacebookCallbackManager = CallbackManager.Factory.create();

        // Callback registration
        facebookBtn.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Token token = new Token();
                token.access_token =loginResult.getAccessToken().getToken();

                Log.i("myTag", String.valueOf(token.access_token));

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }

        });
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        /**
         * Back키 두번 연속 클릭 시 앱 종료
         */
        if ( 0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime ) {
            super.onBackPressed();
        }
        else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(),"뒤로 가기 키을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 필수!!!
         */
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }
}
