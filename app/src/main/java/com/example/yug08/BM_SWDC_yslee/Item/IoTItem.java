package com.example.yug08.BM_SWDC_yslee.Item;

import com.example.yug08.BM_SWDC_yslee.R;

// 리스트에 추가할 아이템 여기서 아이템은 센서당!

public class IoTItem {

    private int ONImagResid;        // on off 값은 내부에서만 접근 가능
    private int OffImagResid;
    private boolean on_off = true;
    private String Nmae;
    private Type type = Type.Bulb;
    private int imag_resId;

    public IoTItem(String Name, int resid) {
        this.Nmae = Name;
        this.imag_resId = resid;
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
        센서 상태 ON,OF 에 따른 이미지상태 (즉 DB 값에 따라 보여지는 이미지를 바꿔준다)
    */
    public void chengeImag() {
        if (on_off)
            imag_resId = ONImagResid;
        else
            imag_resId = OffImagResid;
    }

    /*
        이후 메서드는 값을 읽고 쓰고 위함 , 정보은닉
     */
    public boolean getOnOff() {
        return on_off;
    }

    public void setOnOff(boolean on_off) {this.on_off = on_off;}

    public int getImag_resId() {
        return imag_resId;
    }

    public void setImag_resId(int resid) {
        this.imag_resId = resid;
    }

    public String getNmae() {
        return Nmae;
    }

    public void setNmae(String Name) {this.Nmae = Name;}

    public void setState(boolean on_off) {
        this.on_off = on_off;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
