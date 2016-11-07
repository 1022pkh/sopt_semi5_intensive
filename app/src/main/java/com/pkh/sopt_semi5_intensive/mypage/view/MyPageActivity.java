package com.pkh.sopt_semi5_intensive.mypage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.pkh.sopt_semi5_intensive.R;
import com.pkh.sopt_semi5_intensive.main.view.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPageActivity extends AppCompatActivity {

    @BindView(R.id.profileImage)
    ImageView profileImage;
    @BindView(R.id.loginMethod)
    TextView methodText;
    @BindView(R.id.profileNickname)
    TextView profileNickname;
    @BindView(R.id.logoutBtn)
    Button logoutBtn;

    //Back 키 두번 클릭 여부 확인
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    String loginMethod = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        loginMethod =  intent.getExtras().getString("login");

        /**
         * 각 로그인 방법에 따라 정보를 받아오는 메소드가 다르기 때문에
         */
        if(loginMethod.equals("facebook")){
            facebookRequestProfile();
        }
        else if(loginMethod.equals("kakao")){

        }

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

    @OnClick(R.id.logoutBtn)
    public void logoutEvent(){
        /**
         * 각 로그인 방법에 따라 로그아웃 방법이 다르기 때문에
         */
        if(loginMethod.equals("facebook")){
            LoginManager.getInstance().logOut();
            Toast.makeText(getApplicationContext(),"로그아웃 완료",Toast.LENGTH_SHORT).show();
            moveMainPageActivity();
        }
        else if(loginMethod.equals("kakao")){

        }
    }

    private void moveMainPageActivity() {
        /**
         * 회원가입 성공 시,
         * 마이페이지로 이동한다.
         */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * facebook 사용자의 정보를 가져오기 위해 API 호출을 한다.
     */
    protected void facebookRequestProfile(){

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {

                            String id = (String) response.getJSONObject().get("id");//페이스북 아이디값
                            String name = (String) response.getJSONObject().get("name");//페이스북 이름
                            String email = (String) response.getJSONObject().get("email");//이메일

                            Log.i("myTag",id);
                            Log.i("myTag",name);
                            Log.i("myTag",String.valueOf(response));


                            /**
                             *  페이스북 프로필이미지의 url주소
                             */
                            String thumnailImg = "http://graph.facebook.com/"+ id +"/picture?type=large";

                            Glide.with(MyPageActivity.this)
                                    .load(thumnailImg)
                                    .into(profileImage);

                            methodText.setText("facebook 연동 중");
                            profileNickname.setText(name);


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

}
