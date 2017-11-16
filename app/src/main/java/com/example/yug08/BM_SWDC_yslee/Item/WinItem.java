package com.example.yug08.BM_SWDC_yslee.Item;

import com.example.yug08.BM_SWDC_yslee.R;

import java.io.Serializable;

/**
 * Created by yug08 on 2017-11-09.
 * Serial Type : 2
 * @author YSLEEE
 */

public class WinItem extends IoTItem implements Serializable {

    public WinItem(String Name, int resid) {
        super(Name,resid);
        initImgres();
    }

    public WinItem(){
        Type = 2;
        initImgres();
    }
    private void initImgres() {
        ONImagResid = R.mipmap.win_on;
        OffImagResid = R.mipmap.win_off;
    }
}
