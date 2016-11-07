package com.pkh.sopt_semi5_intensive.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.Session;
import com.kakao.auth.ISessionCallback;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.pkh.sopt_semi5_intensive.R;
import com.pkh.sopt_semi5_intensive.main.model.Token;
import com.pkh.sopt_semi5_intensive.mypage.view.MyPageActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView{

    @BindView(R.id.facebook_login_button)
    LoginButton facebookBtn;

    //페이스북 콜백메소드
    CallbackManager mFacebookCallbackManager;


    //카카오톡 세션
    private SessionCallback callback;

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
         * 3. application을 상속받은 클래스파일을 생성하여 어플리케이션 초기 실행 시, 페이스북설정을 초기화하도록 설정
         * 4. 로그인 버튼의 콜백 메소드 설정
         * 5. onActivityResult 에 다시 액티비티 돌아온 결과에 따른 메소드를 설정해주어야 한다.
         * 6. 다음으로 로그인이 완료되었을 때, 마이페이지로 이동할 수 있도록 코딩
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

                moveMyPageActivity("facebook");

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


        /**
         * 이번엔 카카오톡을 시작하겠습니다.
         */

        /**
         * 1. https://developers.kakao.com/docs/android#시작하기
         * 빠른 시작으로 진행하겠습니다
         * 1-1. 내 어플리케이션 추가
         * 1-2. 카카오톡 가이드에 맞게 gradle 설정을 진행합니다.
         * 1-3. kako_strings.xml 및 Manifest.xml에 설정 값 추가
         * 1-4. 패키지 등록
         * 1-5. 해시키 등록
         */

        /**
         * 2. layout.xml에 로그인 버튼 추가
         * 3. application을 상속받은 클래스파일을 생성하여 어플리케이션 초기 실행 시, 카카오톡 초기화하도록 설정
         * 4. 로그인 버튼의 콜백 메소드 설정
         * 5. onActivityResult 에 다시 액티비티 돌아온 결과에 따른 메소드를 설정해주어야 한다.
         * 6. 다음으로 로그인이 완료되었을 때, 마이페이지로 이동할 수 있도록 코딩
         */

        //kakao login
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 필수!!!
         * 아래의 코드는 페이스북에 필요한 코드
         */
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);

        /**
         * 아래의 코드는 카카오톡에 필요한 코
         */
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void moveMyPageActivity(String loginMethod) {
        /**
         * 회원가입 성공 시,
         * 마이페이지로 이동한다.
         */
        Intent intent = new Intent(this, MyPageActivity.class);
        intent.putExtra("login",loginMethod);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            String token = Session.getCurrentSession().getAccessToken();
            Log.i("myTag",token);

            moveMyPageActivity("kakao");  // 세션 연결성공 시 redirectSignupActivity() 호출         }
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
            Log.i("myTag", String.valueOf(exception));

            setContentView(R.layout.activity_main); // 세션 연결이 실패했을때
        }                                            // 로그인화면을 다시 불러옴
    }
}
