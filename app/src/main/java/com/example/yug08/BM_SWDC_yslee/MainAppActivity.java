
package com.example.yug08.BM_SWDC_yslee;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.yug08.BM_SWDC_yslee.Item.*;

import java.util.ArrayList;

/**
 * Created by yug08 on 2017-09-04.
 * 메인엡 엑비티비 클레스  실재 앱 컨트롤 클레스
 *
 * @17-09-21_YSLEE 최초 GIT 추가
 * @17-09-27_YSLEE ID를 키값으로 상태저장 추가
 * @17-09-29_YSLEE Item 드래그 이동 구현 -> 꼭 필요 있을지 없을지 모르지만일단 있으면 좋지 않을까?
 */
public class MainAppActivity extends Activity {
    private static final String TAG = "MainAppActivity"; // 디버깅용 Class name
    private String ID;
    private IotAdapter iotAdapter;
    private FloatingActionButton Fbtn;
    private RecyclerView recyclerView;
    private ArrayList<IoTItem> items;
    private AlertDialog.Builder alertDialog;
    private EditText et_country;
    private int edit_position;
    private View view;
    private boolean add = false;
    private Paint paint = new Paint();
    private SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 엑티비티 레이아웃 설정 */
        setContentView(R.layout.activity_main);

        initViews();
        initDialog();

    }

    /* View , 변수 초기화 함수 */
    private void initViews() {
        /*ID 넘겨 받기*/
        Intent intent = getIntent();
        ID = intent.getStringExtra("id");

        Fbtn = findViewById(R.id.floatingActionButton);
        sharedPreference = new SharedPreference(ID);

        if ((items = sharedPreference.getItems(this, items)) == null) {
            items = new ArrayList<>();
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        iotAdapter = new IotAdapter(items);
        recyclerView.setAdapter(iotAdapter);
        eventinit();
    }

    private void eventinit() {
        /* 플로팅 액션 버튼 이벤트정의 */
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

        /* 리사이클러뷰 스와이프 관련 이벤트, 기능 정의*/
        initSwipe();

        /*당겨서 새로고침 이벤트 정의*/
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /*
                onRefresh 안에 기능을 정의 하면 됩니다.
                @ 함수로 만들어서 호출 시킬 꺼임
             */
            @Override
            public void onRefresh() {
                // false 안해주면 뻉글 뺑글이 계속 도니깐 꼮 false
                swipeRefreshLayout.setRefreshing(false);
            }
        });

         /*
            새로 고침시 나오는 뻉글뻉글이 색상을 바꿀수 있음
            한바퀴 돌때마다 위에서 부터 순차적으로 색이 바뀜
         */
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
    }

    /*리사이클러뷰 스와이프 관련 기능 초기화 이벤트 에니메이션 정의*/
    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback
                = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return iotAdapter.itemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
            }


            @Override             /* 리싸이클러뷰 스와이프 했을때 기능 왼쪽: 삭제 , 오른쪽 에디트 */
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

            /* 리싸이클러뷰 오른쪽, 왼쪽 스와이프 했을때 나타나는 애니메이션 관련 메서드 */
            @Override
            public void onChildDraw(Canvas c,
                                    RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState,
                                    boolean isCurrentlyActive) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    if (dX > 0) {
                        paint.setColor(Color.parseColor("#a8a8a8a8"));
                        RectF background = new RectF((float) itemView.getLeft(),
                                (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, paint);
                    } else {
                        paint.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX,
                                (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, paint);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /* 스와이프 기능으로 해당 아이템 지우는 기능 정의 */
    private void removeView() {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    /* 리사이클러뷰 동작에 대한 기능 호출 정의*/
    private void initDialog() {
        alertDialog = new AlertDialog.Builder(this);
        view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        alertDialog.setView(view);
        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //추가하면 각 아이템 요소가 더해지는 부분
                IoTItem newItem;
                if (add) {
                    add = false;
                    newItem = new IoTItem(et_country.getText().toString(), R.mipmap.lightingball_on);
                    /* @ 구현이 필요합니다.
                        Intent intent = new Intent(MainAppActivity.this, IoTSettingActivity1st.class);
                         // 엑티비티간 오브젝트 넘기는 법 찾아야된다 .
                        https://medium.com/@henen/%EB%B9%A0%EB%A5%B4%EA%B2%8C-%EB%B0%B0%EC%9A%B0%EB%8A%94-%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-intent-4-%EB%82%B4%EA%B0%80-%EB%A7%8C%EB%93%A0-class%EB%A5%BC-%EC%A0%84%EC%86%A1-serializable-%EC%9D%B4%EC%9A%A9-5fddf7e3c730
                        일단 해당 소스 파일 한번 뜯어보고 고쳐보자
                     */
                    iotAdapter.addItem(newItem);
                }
                //수정해지는 부분 로직
                else {
                    newItem = items.get(edit_position);
                    newItem.setNmae(et_country.getText().toString());
                    items.set(edit_position, newItem);
                    iotAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        et_country = view.findViewById(R.id.et_country);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreference.saveItems(this, items);
    }
}

/*
    @구현 완료
    로그인 할떄 입력한 ID 값을 가지고옴
    유저 ID로 만든 테이블을 가지고 올떄 사용할예정



 */