package com.example.yug08.BM_SWDC_yslee;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    2017-09-10
    LEE YS

    macOS 10.12.6
    로그인 엑티비티 서버 , id , ps 필요
 */
public class MainActivity extends AppCompatActivity {

    /*
        만약 만드시는 서버의 로그인 PHP 스크립트의 경로가
        다르다면 밑에 있는 경로를 바꿔야함.
    */
    private static String Login = "/webapp/user_control.php/";
    private String URL = "";
    private StringRequest request;
    private Button BtnLogin;
    private CheckBox loginDataSave;
    private EditText inputID, inputServer, inputPS;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        initview();
        getdata();
    }

    /*
    17-09-15 첫추가
     yslee

     생성자와 비슷한 역활
     내스타일
     맴버변수 초기화 하고 이밴트들 정의 하는 초기화 메서드

     @ 17-09-18
     로그인 이벤트 기능을 메서드로 만들었음
     체크박스 체크시 로그인 정보를 가지고 바로 로그인 가능하게 하기위해서
     그리고
     코드가 지저분해서 좀더 깔끔하게 하려고...
     */
    void initview() {
        sharedPreferences = getSharedPreferences("Login", Activity.MODE_PRIVATE);
        inputServer = (EditText) findViewById(R.id.serverInput);
        inputID = (EditText) findViewById(R.id.emailInput);
        inputPS = (EditText) findViewById(R.id.passwordInput);
        BtnLogin = (Button) findViewById(R.id.LoginButton);
        loginDataSave = (CheckBox) findViewById(R.id.RememberCheck);

        requestQueue = Volley.newRequestQueue(this);

        /* 로그인 버튼 이벤트 정의 */
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    /*
        17-09-27 첫추가
        yslee

        SharedPreferences 기능을 사용해서 이전에 사용했던 로그인 기록을 가지고 옴
        이전에 체크박스를 체크 하지 않았다면 아무런 값을 가지고 오지 않아요~
    */
    void getdata() {
        inputServer.setText(sharedPreferences.getString("server", ""));
        inputID.setText(sharedPreferences.getString("id", ""));
        inputPS.setText(sharedPreferences.getString("pw", ""));
        loginDataSave.setChecked(sharedPreferences.getBoolean("ch", false));
        login();
    }

    /*
        17-09-27 첫추가
        yslee

        끝날때 체크박스 상태를 파악해서 로그인 정보를 보관
        SharedPreferences 기능을 사용
     */
    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences = getSharedPreferences("Login", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (loginDataSave.isChecked()) {
            editor.putString("server", inputServer.getText().toString());
            editor.putString("id", inputID.getText().toString());
            editor.putString("pw", inputPS.getText().toString());
            editor.putBoolean("ch", true);
        } else {
            editor.putString("server", "");
            editor.putString("id", "");
            editor.putString("pw", "");
            editor.putBoolean("ch", false);
        }
        editor.commit();
    }

    /*
        17-09-18 첫추가
        yslee

        http 리퀘스트를 사용해 입력한 서버로 로그인 리퀘스트를 날립니다.
        IOT 서버 안에 Login PHP 스크립터 경로가 다르다면
        클레스 맴버변수로 있는 경로를 수정해줘야 합니다.
         @ 17-09-19
        로그인 씨스템 구현 해야됨
        @ 17-09-22
        @ 구현 1차 완료 결과 부분적 성공
        @ ip 번호 입력시 로그인 가능함 , 도메인 입력시 안됨..
        @ 도메인 서버에서 넘어갈떄 나오는 값이 문제인건지 도메인 넣어다 하면 안됨 ... ㅠㅠㅠ
        @ 17-09-23
        @ 도메인 안되는 이유를 못찾겠음 ...
        @ 아마 도메인 세팅하는 파트에서 잘못해서 그런거 같음
        @ 왜안되지 ...
        @ 17-09-25
        @ 도메인으로 넘기는거 포기!
     */
    void login() {
        URL = "http://" + inputServer.getText().toString() + Login;
        final String userID = inputID.getText().toString();
        final String userPW = inputPS.getText().toString();
        Log.d("asd", "로그인!!!!!");
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {                               // json 받아서 파싱
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.names().get(0).equals("success")) {
                        Toast.makeText(getApplicationContext(), "주인님 방갑습니다." +
                                jsonObject.getString("success"), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),MainAppActivity.class);
                        intent.putExtra("id",inputID.getText().toString());     // 로그인시 아이디를 넘겨줍니다.
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                jsonObject.getString("error"),
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "서버 상태 혹은 서버 주소를 확인하세요.",
                            Toast.LENGTH_SHORT).show();
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
}
