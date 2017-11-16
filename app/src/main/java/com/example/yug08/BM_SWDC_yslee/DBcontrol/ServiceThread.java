package com.example.yug08.BM_SWDC_yslee.DBcontrol;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.yug08.BM_SWDC_yslee.Item.IoTItem;
import com.example.yug08.BM_SWDC_yslee.Item.SingletonList;

import java.util.ArrayList;

/**
 * Created by yug08 on 2017-11-16.
 * @author YSLEE
 * BackGround Service 를 돌리기 위한 Thread 입니다.
 */
public class ServiceThread extends Thread {

    private Handler handler;
    private ArrayList<IoTItem> items;
    private boolean outgoingMode;
    private Refresh_item refresh_item;
    private boolean[] preBuffer;

    boolean isrun = true;

    /**
     * 생성자
     * @param handler 핸들러 입력
     * @param context 서비스 Context 입력
     */

    public ServiceThread(Handler handler , Context context){
        this.handler = handler;

        items = SingletonList.getItemsinstance();
        preBuffer = new boolean[4];

        outgoingMode = false;

        refresh_item = new Refresh_item(context);

        for (int i = 0; i < items.size(); i++) {
            boolean temp = items.get(i).getCurrentStatus();
            preBuffer[i] = temp;
        }
    }

    /**
     * 외출모드 변수를 설정
     * @param outgoingMode 외부에서 외출모드 지정시 불리언 값으로 지정
     */
    public void setOutGoingMode(boolean outgoingMode){
        this.outgoingMode = outgoingMode;
    }

    public void stopThread(){
        synchronized (this){
            this.isrun = false;
        }
    }

    @Override
    public void run() {
        while(isrun){
            try{
                Thread.sleep(300); //10초씩 쉰다.
                Log.d("test", "서비스의 onStartCommand");
                refresh_item.refresh(items);
                chvallu();

            }catch (Exception e) {}
        }
    }

    /**
     * 이전 버퍼를 확인하여 값이 변경 되었는지를 채크 하여
     * 알람을 보낼지 확인 합니다.
     */
    private void chvallu() {
        for (int i = 0; i < items.size(); i++) {
            boolean temp = items.get(i).getCurrentStatus();

            sendNoTiy((preBuffer[i] == temp));
            preBuffer[i] = temp;
        }
    }

    /**
     * 값이 변경됬을경우 외출모드가 켜저 있으면
     * Noty(알람)을 발생시킵니다.
     * @param flage 이전값과 현제 값이 같은값인지 검사
     */
    private void sendNoTiy(boolean flage){
        if(!flage&&outgoingMode){
            handler.sendEmptyMessage(0);
        }

    }
}
