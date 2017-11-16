package com.example.yug08.BM_SWDC_yslee.DBcontrol;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yug08.BM_SWDC_yslee.Item.IoTItem;

import java.util.HashMap;
import java.util.Map;

import static com.example.yug08.BM_SWDC_yslee.IoTUtil.IoTUtil.getID;
import static com.example.yug08.BM_SWDC_yslee.IoTUtil.IoTUtil.getIP;
import static com.example.yug08.BM_SWDC_yslee.IoTUtil.IoTUtil.steteToInt;

/**
 * Created by yug08 on 2017-11-10.
 */

public class SendSignal {
    private static final String updateURL = "/BM/update_control.php";
    private  String URL =null;
    private  HashMap<String, String> parameta;
    private VolleyRequestInstance requestInstance;

    public SendSignal(){
        URL =  "http://" + getIP() + updateURL;
    }

    public void sendRequest(IoTItem iotitem) {
        final SendSignal sendSignal =this;
        requestInstance = VolleyRequestInstance.getInstance();

        RequestQueue requestQueue = requestInstance.getRequestQueue();

        String PORT = String.valueOf(iotitem.getPort());
        String STATE = String.valueOf(steteToInt(iotitem.getCurrentStatus()));

        sendSignal.setParameta(PORT,STATE);


        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                sendSignal.networkSuccessListener(),
                sendSignal.networkErrorListener(requestInstance.getContext())){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return sendSignal.getParams();
            }
        };

        requestQueue.add(request);
    }

    private Response.Listener<String> networkSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {}
        };
    }

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

    private void setParameta(String PORT,String STATE) {
        parameta = new HashMap<String, String>();
        parameta.put("ID", getID());
        parameta.put("PORT", PORT);
        parameta.put("STATE", STATE);
    }

}
