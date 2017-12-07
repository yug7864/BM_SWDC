package com.example.yug08.BM_SWDC_yslee.IoTUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.yug08.BM_SWDC_yslee.Item.BulbItem;
import com.example.yug08.BM_SWDC_yslee.Item.IoTItem;
import com.example.yug08.BM_SWDC_yslee.Item.WinItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yug08 on 2017-09-28.
 * @author YSLEE
 * 메인이 너무 길어져서 나눴음
 */

public class SharedPreference {

    public static  String FAVORITES = "IOTitem";
    private String ID;

    public SharedPreference(String ID) {
        super();
        this.ID = ID;
    }

    public void saveItems(Context context,ArrayList<IoTItem> items) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        sharedPreferences = context.getSharedPreferences(ID,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json  = gson.toJson(items);
        editor.putString(FAVORITES, json );

        editor.commit();
    }

    /**
     * 어플종료시 저장된 리스트를 복원함
     *
     * @param context MainAppActivity 의 Context 를 넣어줌 getApplicationContext()
     * @param items 복원할 리스트
     * @return 복원된 리스트
     */
    public ArrayList<IoTItem> getItems(Context context, ArrayList<IoTItem> items) {
        SharedPreferences sharedPreferences;
        List<IoTItem> ioTItems;

        sharedPreferences = context.getSharedPreferences(ID,Context.MODE_PRIVATE);

        if (sharedPreferences.contains(FAVORITES)) {
            String jsonFavorites = sharedPreferences.getString(FAVORITES, null);

            Gson gson = new Gson();

            IoTItem[] favoriteItems = gson.fromJson(jsonFavorites, IoTItem[].class);

            ioTItems = Arrays.asList(favoriteItems);
            ioTItems = new ArrayList<IoTItem>(ioTItems);
            checkType(ioTItems);
        } else{
            return null;
        }

        return (ArrayList<IoTItem>) ioTItems;
    }

    /** 시리얼화 시킨 다형성 Item 들을 다시 원래 형태로 마춰주는 기능 수행
     *  #이거 안하면 아이템의 기능 전부 날아가는 문제 발생 ...#
     *  Item 추가하면 Gson 시리얼 번호 부여하고 예외처리 해줘야됨 .. ㅠ
     * @param items : 체크를 원하는 리스트
     */
    private void checkType(List<IoTItem> items){
        for(int i=0; i<items.size(); i++){
            IoTItem item = items.get(i);

            switch (item.Tpye())
            {
                case 1:
                    BulbItem bulbItem = new BulbItem();
                    bulbItem.setPort(item.getPort());
                    bulbItem.setNmae(item.getNmae());

                    items.remove(i);
                    items.add(i,bulbItem);
                    break;
                case 2:
                    WinItem winItem = new WinItem();
                    winItem.setPort(item.getPort());
                    winItem.setNmae(item.getNmae());

                    items.remove(i);
                    items.add(i,winItem);
                    break;

                default:
                    break;

            }


        }
    }
}
