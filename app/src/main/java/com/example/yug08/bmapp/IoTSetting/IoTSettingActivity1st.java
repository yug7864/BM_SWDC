//
//package com.example.yug08.bmapp.IoTSetting;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.View;
//import android.widget.ImageButton;
//
//import com.example.yug08.bmapp.Item.IoTItem;
//import com.example.yug08.bmapp.Item.Type;
//import com.example.yug08.bmapp.R;
//
//
///**
// * Created by yug08 on 2017-09-19.
// */
//
//public class IoTSettingActivity1st extends Activity {
//    private IoTItem ioTItem;
//    private ImageButton win,bulb;
//
//    IoTSettingActivity1st(IoTItem ioTItem){
//        this.ioTItem =ioTItem;
//    }
//
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.regiseractivity);
//    }
//
//    /*
//        사용할 뷰 초기화 , 이벤트 생성
//     */
//    void initview(){
//        win = (ImageButton) findViewById(R.id.SettingWin);
//        bulb = (ImageButton) findViewById(R.id.Settingbulb);
//        /*
//            창문이미지 버튼 클릭 이벤트 생성
//            클릭하면 활성화 되도록 하는 코드
//         */
//        win.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(ioTItem.getType() == Type.Win)
//                {
//                    ioTItem.setType(null);
//                    win.setImageResource(R.mipmap.);
//                }
//                else if (ioTItem.getType() == null)
//                {
//                    win.setImageResource(R.mipmap.IoTSetting_win_ch);
//                }
//                else
//                {
//                    ioTItem.setType(Type.Win);
//                    bulb.setImageResource(R.mipmap.IoTSetting_bulb);
//                    win.setImageResource(R.mipmap.IoTSetting_win_ch);
//                }
//            }
//        });
//
//        /*
//            전구 버튼이미지 클릭 이벤트 생성
//            클릭하면 활성화 되도록 하는 코드
//         */
//        bulb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(ioTItem.getType() == Type.Bulb)
//                {
//                    ioTItem.setType(null);
//                    win.setImageResource(R.mipmap.IoTSetting_bulb);
//                }
//                else
//                {
//                    ioTItem.setType(Type.Bulb);
//                    bulb.setImageResource(R.mipmap.IoTSetting_bulb);
//                    win.setImageResource(R.mipmap.IoTSetting_bulb_ch);;
//                }
//            }
//        });
//    }
//
//    public void setIoTItem(IoTItem ioTItem) {
//        this.ioTItem = ioTItem;
//    }
//
//
//}
//
