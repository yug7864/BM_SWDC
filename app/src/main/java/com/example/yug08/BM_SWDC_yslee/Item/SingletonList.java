package com.example.yug08.BM_SWDC_yslee.Item;

import java.util.ArrayList;

/**
 * 리스트 하나 밖에 필요 하지 않은거 같아 싱글톤으로 편하게 서비스에서 받기 위해 만듬
 * Created by yug08 on 2017-11-15.
 * @author YSLEE
 */

public class SingletonList {
    static ArrayList<IoTItem> itemsinstance;

    public SingletonList(ArrayList<IoTItem> items) {
        itemsinstance = items;
    }

    public static ArrayList<IoTItem> getItemsinstance() {
        if (itemsinstance == null) {
            itemsinstance = new ArrayList<>();
        }
        return itemsinstance;
    }
}
