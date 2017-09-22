package com.example.yug08.bmapp;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yug08 on 2017-09-22.
 */

public class LoginRequest extends StringRequest {

    static  private String URL = "http://bm.ys2lee.com/webapp/Login.php";
    private Map<String ,String> parameta;

    public LoginRequest(String server,String userID, String userPW, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        URL = server;
        parameta = new HashMap<>();
        parameta.put("userID", userID);
        parameta.put("userPW", userPW);
    }
    @Override
    protected Map<String, String> getParams() {return parameta;}
}
