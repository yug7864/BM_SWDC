package com.example.yug08.bmapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*
    로그인 엑티비티 서버 , id , ps 필요
 */
public class MainActivity extends AppCompatActivity {

    private Button BtnLogin;
    private EditText inputID, inputServer, inputPS;
    private AlertDialog dialog;
    private RequestQueue requestQueue;
    public String URL = "http://118.39.231.49:8080/webapp/user_control.php";
    public static String Login = "/webapp/user_control.php/";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        /*
            로그인 버튼 로그인 되면 메인앱 화면으로 전환시켜줌
            # [ID, 서버 , 비번 , DB 값] 인텐트로 넘겨야됨 구현 필요 우선도 낮음
         */
        initview();
    }

    void initview() {
        inputServer = (EditText) findViewById(R.id.serverInput);
        inputID = (EditText) findViewById(R.id.emailInput);
        inputPS = (EditText) findViewById(R.id.passwordInput);
        BtnLogin = (Button) findViewById(R.id.LoginButton);
        requestQueue = Volley.newRequestQueue(this);
        /*
            로그인 버튼 이벤트 정의
         */
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL = "http://" + inputServer.getText().toString() + Login;
                final String userID = inputID.getText().toString();
                final String userPW = inputPS.getText().toString();
                Log.e("asd", "로그인!!!!!");

                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    // 로그인에 성공한다면
                    public void onResponse(String response) {
                        try {
                            // json 받아서 파싱
                            JSONObject jsonObject = new JSONObject(response);

                            // success json 존제 하면 참이겠지?
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), "주인님 방갑습니다." + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();

                                // 매인 앱 액티비티로 이동
                                startActivity(new Intent(getApplicationContext(), MainAppActivity.class));
                            }
                            // 실패시 json 에러 값 출력;
                            else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        // POST 형식으로 넘겨줄 인자 맵
                        HashMap<String, String> parameta = new HashMap<String, String>();
                        parameta.put("userID", userID);
                        parameta.put("userPW", userPW);
                        return parameta;
                    }
                };
                requestQueue.add(request);
            }
        });
    }

    /*
        @ 17-09-19
        로그인 씨스템 구현 해야됨
        @ 17-09-22
        @ 구현 1차 완료 결과 부분적 성공
        @ ip 번호 입력시 로그인 가능함 , 도메인 입력시 안됨..
        @ 도메인 서버에서 넘어갈떄 나오는 값이 문제인건지 도메인 넣어다 하면 안됨 ... ㅠㅠㅠ
        @
     */
}
