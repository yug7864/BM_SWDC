package com.example.yug08.BM_SWDC_yslee.DBcontrol;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yug08.BM_SWDC_yslee.IoTUtil.IoTUtil;
import com.example.yug08.BM_SWDC_yslee.MainAppActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yug08 on 2017-11-10.
 */
public class Login {
    private static String path = "/BM/user_control.php/";
    private  HashMap<String, String> parameta;



    public void login(Context context){
        final String URL = "http://" + IoTUtil.getIP() + path;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                networkSuccessListener(context),
                networkErrorListener(context)
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return Login.this.getParams();
            }
        };
        requestQueue.add(request);
    }

    private Response.Listener<String> networkSuccessListener(final Context context) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {                               // json 받아서 파싱
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.names().get(0).equals("success")) {

                        Toast.makeText(context,
                                jsonObject.getString("success"),
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, MainAppActivity.class);
                        context.startActivity(intent);

                    } else {
                        Toast.makeText(context,
                                jsonObject.getString("error"),
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 실패시 행위 명세
     * @param context Toast 만드려면 필요함
     * @return
     */
    private Response.ErrorListener networkErrorListener(final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "서버상태및 서버 주소를 확인해 주세요!",
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    private Map<String, String> getParams() throws AuthFailureError {
        // POST 형식으로 넘겨줄 인자 맵
        return parameta;
    }

    public void setParameta(String ID, String PW) {
        parameta = new HashMap<String, String>();

        parameta.put("userID", ID);
        parameta.put("userPW", PW);
    }
}
