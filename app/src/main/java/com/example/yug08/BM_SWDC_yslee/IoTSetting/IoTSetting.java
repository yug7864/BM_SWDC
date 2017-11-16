package com.example.yug08.BM_SWDC_yslee.IoTSetting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.yug08.BM_SWDC_yslee.Item.BulbItem;
import com.example.yug08.BM_SWDC_yslee.Item.IoTItem;
import com.example.yug08.BM_SWDC_yslee.Item.WinItem;
import com.example.yug08.BM_SWDC_yslee.MainAppActivity;
import com.example.yug08.BM_SWDC_yslee.R;

/**
 * Created by yug08 on 2017-10-04.
 */

public class IoTSetting extends Activity {


    private EditText namSpace;
    private ImageButton bulb, win;
    private boolean B = false, W = false;
    private CheckBox port[] = new CheckBox[4];

    private int portnumber;

    private IoTItem ioTItem;
    private Button okbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.setting);

        init();
        initevent();
    }

    void init() {
        namSpace = (EditText) findViewById(R.id.namespace);
        bulb = (ImageButton) findViewById(R.id.checkableImageButton_blub);
        win = (ImageButton) findViewById(R.id.checkableImageButton_win);
        okbtn = (Button) findViewById(R.id.okbtn);
        port[0] = (CheckBox) findViewById(R.id.port01);
        port[1] = (CheckBox) findViewById(R.id.port02);
        port[2] = (CheckBox) findViewById(R.id.port03);
        port[3] = (CheckBox) findViewById(R.id.port04);
    }

    void initevent() {
        bulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ioTItem = new BulbItem();
                if (B) {
                    bulb.setImageResource(R.mipmap.setiot_bulb);
                    B = false;
                } else {
                    bulb.setImageResource(R.mipmap.setiot_bulb_ch);
                    B = true;
                    win.setImageResource(R.mipmap.setiot_win);
                    W = false;
                }
            }
        });

        win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ioTItem = new WinItem();
                if (W) {
                    win.setImageResource(R.mipmap.setiot_win);
                    W = false;
                } else {
                    win.setImageResource(R.mipmap.setiot_win_ch);
                    W = true;
                    bulb.setImageResource(R.mipmap.setiot_bulb);
                    B = false;
                }
            }
        });

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ok();
            }
        });
    }

    void ok() {
        String name = namSpace.getText().toString();
        chToint();

        ioTItem.setPort(portnumber);
        ioTItem.setNmae(name);

        Intent intent = new Intent(getApplicationContext(), MainAppActivity.class);
        intent.putExtra("a",ioTItem);
        setResult(0,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        ioTItem =null;
        Intent intent = new Intent(getApplicationContext(), MainAppActivity.class);
        intent.putExtra("a",ioTItem);
        setResult(0,intent);
        finish();
    }

    void chToint() {
        for (int i = 0; i < 4; i++) {
            if (port[i].isChecked()) {
                portnumber = i + 1;
            }
        }
    }
}

