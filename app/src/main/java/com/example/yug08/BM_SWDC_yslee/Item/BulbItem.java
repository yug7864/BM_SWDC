package com.example.yug08.BM_SWDC_yslee.Item;

import android.util.Log;

import com.example.yug08.BM_SWDC_yslee.DBcontrol.SendSignal;
import com.example.yug08.BM_SWDC_yslee.R;

import java.io.Serializable;

/**
 * Created by yug08 on 2017-11-09.
 * Serial Type : 1
 */
public class BulbItem extends IoTItem implements Serializable {

    public BulbItem(String Name, int resid) {
        super(Name,resid);
        initImgres();
    }

    public BulbItem(){
        Type = 1;
        initImgres();
    }

    private void initImgres() {
        ONImagResid = R.mipmap.lightingball_on;
        OffImagResid = R.mipmap.lightingball_off;

        /*UnitTest 3 다형성 확인 해야 한다.... */
        Log.d("UnitTest #UT3", "Bulb ONImagResid : "+String.valueOf(ONImagResid));
        Log.d("UnitTest #UT3", "Bulb OffImagResid : "+String.valueOf(OffImagResid));
    }

    @Override
    public void senserfnc() {
        /**
         *  어뎁터의 값 전송 메서드 모듈화후 넣어야됨
         */
        synchronized(this) {
            currentStatus = !getCurrentStatus();
            SendSignal sendSignal = new SendSignal();
            sendSignal.sendRequest(this);
            chengeImag();
        }
    }
}
