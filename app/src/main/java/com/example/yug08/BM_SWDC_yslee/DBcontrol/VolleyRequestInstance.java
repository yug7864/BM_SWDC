package com.example.yug08.BM_SWDC_yslee.DBcontrol;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by yug08 on 2017-11-10.
 */

public class VolleyRequestInstance {
    private static VolleyRequestInstance instance; /* singleton 써보자 */
    private RequestQueue requestQueue;
    private Context context;

    /**
     * singleton 패턴으로 인스턴스를 받으면 하나만 유지 하니깐 괜찮지 않을까???!
     */
    public static VolleyRequestInstance getInstance(){
        if(instance == null){
            instance = new VolleyRequestInstance();
        }
        return instance;
    }

    public static VolleyRequestInstance getInstance(Context context){
        if(instance == null){
            instance = new VolleyRequestInstance();
        }
        instance.setContext(context);
        instance.setRequestSetting(Volley.newRequestQueue(context));
        return instance;
    }

    public  void setContext(Context context){
        this.context = context;
    }

    public Context getContext(){
        return context;
    }

    public void setRequestSetting(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
    }

    public  RequestQueue getRequestQueue(){
        return requestQueue;
    }

}
