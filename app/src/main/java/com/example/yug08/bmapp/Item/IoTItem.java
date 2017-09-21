package com.example.yug08.bmapp.Item;

import android.os.Parcelable;
import android.widget.ImageButton;

import com.example.yug08.bmapp.R;

// 리스트에 추가할 아이템 여기서 아이템은 센서당!

public class IoTItem {

    int debug;
    private boolean on_off = true;
    private String Nmae;
    private Type type = Type.Bulb;
    private int imag_resId;
    int ONImagResid;
    int OffImagResid;

    IoTItem() {
    }

    public IoTItem(String Name, int resid) {
        this.Nmae = Name;
        this.imag_resId = resid;
        initImgres();
    }

    public IoTItem(String Name, int resid, int debug) {
        this.Nmae = Name;
        this.imag_resId = resid;
        this.debug = debug;
        initImgres();
    }

    /*
        초기 설정하는 센서의 타입에 다라 이미지 리소스 변경시켜줌
     */
    private void initImgres() {
        switch (type) {
            case Bulb:
                ONImagResid = R.mipmap.lightingball_on;
                OffImagResid = R.mipmap.lightingball_off;
                break;
            case Win:
                ONImagResid = R.mipmap.lightingball_on;
                OffImagResid = R.mipmap.lightingball_off;
                break;
            default:
                break;
        }
    }

    /*
        클릭하면 이미지 상태 변경 된다.
     */
    void iotclick() {
        if (on_off)
            on_off = false;
        else
            on_off = true;

        chengeImag();
    }

    /*
        센서 상태 ON,OF 에 따른 이미지상태 (즉 DB 값에 따라 보여지는 이미지를 바꿔준다)
    */
    public void chengeImag() {
        if (on_off)
            imag_resId = R.mipmap.lightingball_on;
        else
            imag_resId = R.mipmap.lightingball_off;
    }

    /*
        이후 메서드는 값을 읽고 쓰고 위함 , 정보은닉
     */
    public boolean getOnOff() {
        return on_off;
    }

    public void setOnOff(boolean on_off) {
        this.on_off = on_off;
    }

    public int getImag_resId() {
        return imag_resId;
    }

    public void setImag_resId(int resid) {
        this.imag_resId = resid;
    }

    public String getNmae() {
        return Nmae;
    }

    public int getDebug() {
        return debug;
    }

    public void setNmae(String Name) {
        this.Nmae = Name;
    }

    public void setState(boolean on_off) {
        this.on_off = on_off;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }
}
