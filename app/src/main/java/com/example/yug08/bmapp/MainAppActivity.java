/**
 * Created by yug08 on 2017-09-04.
 * 메인엡 엑비티비 클레스  실재 앱 컨트롤 클레스
 */

package com.example.yug08.bmapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.yug08.bmapp.Item.*;

import java.util.ArrayList;


public class MainAppActivity extends Activity {
    private static final String TAG = "MainAppActivity"; // 디버깅용 Class name

    private FloatingActionButton Fbtn;
    IotAdapter iotAdapter;
    private RecyclerView recyclerView;
    private ArrayList<IoTItem> items = new ArrayList<>();
    private AlertDialog.Builder alertDialog;
    private EditText et_country;
    private int edit_position;
    private View view;
    private boolean add = false;
    private Paint paint = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //엑티비티 레이아웃 설정
        setContentView(R.layout.activity_main);

        /*
            액티비티에서 보여주는 뷰 초기화
            플로팅 버튼 , 리싸이클러뷰 , 리싸이클러뷰 스와이프 기능 까지 전부 정의
        */
        initViews();

        /*
            새로운 아이템 생성 , 수정시 이동되는 액비비티 , 팝업창을 정의하고 초기화
         */
        initDialog(); // 팝업창 초기화

    }

    private void initViews() {
        Fbtn = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        //플로팅 액션 버튼 이벤트정의
        Fbtn.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "더해저야 하무니다!"); // 디버깅 용이라서 빌드때 사라질듯...
                removeView();
                add = true;
                alertDialog.setTitle("Add item");
                et_country.setText("");
                alertDialog.show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        iotAdapter = new IotAdapter(items);
        recyclerView.setAdapter(iotAdapter);
        initSwipe();
    }

    //스와이프 관련 기능 초기화 이벤트 에니메이션 정의
    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback
                = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            /*
                리싸이클러뷰 스와이프 했을때 기능 왼쪽: 삭제 , 오른쪽 에디트
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    iotAdapter.removeItem(position);
                } else {
                    removeView();
                    edit_position = position;
                    alertDialog.setTitle("Edit Country");
                    et_country.setText(items.get(position).getNmae());
                    alertDialog.show();
                }
            }

            /*
                리싸이클러뷰 오른쪽, 왼쪽 스와이프 했을때 나타나는 애니메이션 관련 메서드
             */
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        paint.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, paint);
                    } else {
                        paint.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, paint);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    // 스와이프 기능으로 해당 아이템 지우는 기능 정의
    private void removeView() {
        if (view.getParent() != null)
            ((ViewGroup) view.getParent()).removeView(view);
    }

    private void initDialog() {
        alertDialog = new AlertDialog.Builder(this);
        view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        alertDialog.setView(view);
        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //추가하면 각 아이템 요소가 더해지는 부분
                if (add) {
                    add = false;
                    iotAdapter.addItem(new IoTItem(et_country.getText().toString(), R.mipmap.lightingball_on));
                    // Intent intent = new Intent(MainAppActivity.this, IoTSettingActivity1st.class); // 엑티비티간 오브젝트 넘기는 법 찾아야된다 .
                    /*
                        https://medium.com/@henen/%EB%B9%A0%EB%A5%B4%EA%B2%8C-%EB%B0%B0%EC%9A%B0%EB%8A%94-%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-intent-4-%EB%82%B4%EA%B0%80-%EB%A7%8C%EB%93%A0-class%EB%A5%BC-%EC%A0%84%EC%86%A1-serializable-%EC%9D%B4%EC%9A%A9-5fddf7e3c730
                        일단 해당 소스 파일 한번 뜯어보고 고쳐보자
                     */

                }
                //수정해지는 부분 로직
                else {
                    items.set(edit_position, new IoTItem(et_country.getText().toString(), R.mipmap.lightingball_on));
                    iotAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        et_country = (EditText) view.findViewById(R.id.et_country);
    }

}



/*
  private void initDialog() {
        alertDialog = new AlertDialog.Builder(this);
        view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        alertDialog.setView(view);
        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (add)
                {
                    add = false;
                    iotAdapter.addItem(new IoTItem(et_country.getText().toString(), R.mipmap.lightingball_on));
                    dialog.dismiss();
                }
                else
                {
                    items.set(edit_position, new IoTItem(et_country.getText().toString(), R.mipmap.lightingball_on));
                    iotAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        et_country = (EditText) view.findViewById(R.id.et_country);
    }
}
 */