package com.example.yug08.BM_SWDC_yslee.Item;

import java.io.Serializable;

// 리스트에 추가할 아이템 여기서 아이템은 센서당!


public class IoTItem implements Serializable {
    protected int Type = 0; /** 센서를 확인 하기 위한 값 상속 해서 만들면 유일한 센서 품번을 부여해야함*/

    protected int ONImagResid;        // on off 값은 내부에서만 접근 가능
    protected int OffImagResid;
    protected boolean currentStatus = true;
    protected String Nmae;
    protected int imag_resId;
    protected int port;

    public IoTItem(String Name, int resid) {
        this.Nmae = Name;
        this.imag_resId = resid;
    }

    public IoTItem() {}

    public void senserfnc(){}

    /*
        센서 상태 ON,OF 에 따른 이미지상태 (즉 DB 값에 따라 보여지는 이미지를 바꿔준다)
    */
    public void chengeImag() {
        if (currentStatus)
            imag_resId = ONImagResid;
        else
            imag_resId = OffImagResid;
    }
    /*
        이후 메서드는 값을 읽고 쓰고 위함 , 정보은닉
     */

    public int getType(){
        return Type;
    }
    public synchronized boolean getCurrentStatus() {
        return currentStatus;
    }

    public synchronized void setCurrentStatus(boolean on_off) {this.currentStatus = on_off;}

    public int getImag_resId() {
        return imag_resId;
    }

    public String getNmae() {
        return Nmae;
    }

    public void setNmae(String Name) {this.Nmae = Name;}

    public synchronized void setPort(int portnum){this.port = portnum;}

    public synchronized int getPort(){return port;}

    public int Tpye(){
        return Type;
    }
}
