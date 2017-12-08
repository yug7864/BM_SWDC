package com.example.yug08.BM_SWDC_yslee.DBcontrol;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yug08.BM_SWDC_yslee.Item.IoTItem;
import com.example.yug08.BM_SWDC_yslee.Item.IotAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.yug08.BM_SWDC_yslee.IoTUtil.IoTUtil.getID;
import static com.example.yug08.BM_SWDC_yslee.IoTUtil.IoTUtil.getIP;
import static com.example.yug08.BM_SWDC_yslee.IoTUtil.IoTUtil.intToState;

/**
 * Created by yug08 on 2017-11-10.
 */

public class Refresh_item {
    private static final String getstatesURL = "/BM/get_States.php/";
    private String URL;
    private  HashMap<String, String> parameta;
    private VolleyRequestInstance requestInstance;
    private RequestQueue requestQueue;

    public Refresh_item(){

        URL = "http://" + getIP() + getstatesURL;
        requestInstance = VolleyRequestInstance.getInstance();
        requestQueue = requestInstance.getRequestQueue();
    }

    public Refresh_item(Context context){

        URL = "http://" + getIP() + getstatesURL;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void refresh(ArrayList<IoTItem> items, IotAdapter iotAdapter) {

        IoTItem item;
        /**
         * 와 이거 진짜 너무 야매로 했다 ..
         * 나중에 2차 제작에서 전체 테이블을 한번에 끌어온뒤
         * 내부에서 값을 아이템에 나눠주는 방식으로 해봅시다 ....
         */
        for (int i = 0; i < items.size(); i++) {

            item = items.get(i);
            final String PORT = String.valueOf(item.getPort());
            final int posi = i;

            /**
             와 이거 진짜 ... 너무 야매로 한거 같은데
             내 머리로는 이런 방식 밖에 생각이 안납니다 .. 미안하다 칭구들 ㅠㅠㅠ
             */

            StringRequest request = new StringRequest(Request.Method.POST,
                    URL,
                    networkSuccessListener(item,iotAdapter,posi),
                    networkErrorListener()){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return Refresh_item.this.getParams(PORT);
                }
            };
            requestQueue.add(request);
        }
    }

    public void refresh(ArrayList<IoTItem> items) {

        IoTItem item;

        for (int i = 0; i < items.size(); i++) {

            item = items.get(i);
            final String PORT = String.valueOf(item.getPort());
            final int posi = i;

            StringRequest request = new StringRequest(Request.Method.POST,
                    URL,
                    networkSuccessListener(item),
                    networkErrorListener()){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return Refresh_item.this.getParams(PORT);
                }
            };
            requestQueue.add(request);
        }
    }

    private Response.Listener<String> networkSuccessListener(final  IoTItem item) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    int s = Integer.parseInt(jsonObject.getString("state"));
                    item.setCurrentStatus(intToState(s));
                    item.chengeImag();

                    /* UnitTest 1 port 번호로 확인하자! */
                    Log.d("UnitTest #UT1","port :"+String.valueOf(item.getPort())+
                            "state :"+String.valueOf(item.getCurrentStatus()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }




    private Response.Listener<String> networkSuccessListener(final  IoTItem item, final IotAdapter iotAdapter, final int posi) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    int s = Integer.parseInt(jsonObject.getString("state"));
                    item.setCurrentStatus(intToState(s));
                    item.chengeImag();
                    iotAdapter.notifyItemChanged(posi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private Response.ErrorListener networkErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        };
    }
    private Map<String, String> getParams(String PORT) throws AuthFailureError {
        // POST 형식으로 넘겨줄 인자 맵
        setParameta(PORT);
        return parameta;
    }

    private void setParameta(String PORT) {
        parameta = new HashMap<String, String>();
        parameta.put("ID", getID());
        parameta.put("PORT", PORT);
    }
}
