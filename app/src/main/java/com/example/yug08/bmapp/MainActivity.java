package com.example.yug08.bmapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button BtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        BtnLogin = (Button) findViewById(R.id.LoginButton);

        /*
            로그인 버튼 로그인 되면 메인앱 화면으로 전환시켜줌
            # [ID, 서버 , 비번 , DB 값] 인텐트로 넘겨야됨 구현 필요 우선도 낮음
         */
        BtnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,MainAppActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
        로그인 데이터를 서버에 매칭해서 서버에 등록된 사용자일 경우에만 로그인 허용
        # DB의 로그인 테이블 비교하는 소스를 짜야됨 힘들다 ... DB 1도 모른다 ㅠㅠ
     */
    public  void userLogin(View view)
    {
        startActivity(new Intent(this, Register.class));
    }
}
