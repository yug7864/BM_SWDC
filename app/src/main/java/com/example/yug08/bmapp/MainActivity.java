package com.example.yug08.bmapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/*
    로그인 엑티비티 서버 , id , ps 필요
 */
public class MainActivity extends AppCompatActivity {

    private Button BtnLogin;
    private EditText inputID, inputServer, inputPS;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        /*
            로그인 버튼 로그인 되면 메인앱 화면으로 전환시켜줌
            # [ID, 서버 , 비번 , DB 값] 인텐트로 넘겨야됨 구현 필요 우선도 낮음
         */
    }

    void initview() {
        inputID = (EditText) findViewById(R.id.emailInput);
        inputServer = (EditText) findViewById(R.id.passwordInput);
        inputPS = (EditText) findViewById(R.id.serverInput);
        BtnLogin = (Button) findViewById(R.id.LoginButton);

        /*
            로그인 버튼 이벤트 정의
         */
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Server = inputServer.getText().toString();
                String userID = inputID.getText().toString();
                String userPW = inputPS.getText().toString();

                Response.Listener<String> respons = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            /*
                                로그인 성공시 현 액티비티를 멈추고 메인 엑티비티로 전환
                                 아직 값을 넘기는걸 구현하지 않음...
                                 조만간 테이블 나오는거보고 어떻게  갱신을 구현할지
                                 고민해봅시다...
                             */
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                dialog = builder.setMessage("주인님 환영합니다!").setPositiveButton("확인", null).create();
                                dialog.show();
                                Intent intent = new Intent(MainActivity.this, MainAppActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                //실패하면 그냥 실패
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                dialog = builder.setMessage("계정이 없습니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(Server,userID,userPW,respons);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    protected void  onStop(){
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }

    }

    /*
        로그인 데이터를 서버에 매칭해서 서버에 등록된 사용자일 경우에만 로그인 허용
        # DB의 로그인 테이블 비교하는 소스를 짜야됨 힘들다 ... DB 1도 모른다 ㅠㅠ
        @ 17-09-22 구현 1차 완료
     */
}
