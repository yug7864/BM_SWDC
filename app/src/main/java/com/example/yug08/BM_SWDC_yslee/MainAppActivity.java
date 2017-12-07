
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
import android.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.yug08.BM_SWDC_yslee.DBcontrol.BackgroundService;
import com.example.yug08.BM_SWDC_yslee.DBcontrol.VolleyRequestInstance;
import com.example.yug08.BM_SWDC_yslee.IoTSetting.IoTSetting;
import com.example.yug08.BM_SWDC_yslee.IoTUtil.IoTUtil;
import com.example.yug08.BM_SWDC_yslee.IoTUtil.SharedPreference;
import com.example.yug08.BM_SWDC_yslee.Item.IoTItem;
import com.example.yug08.BM_SWDC_yslee.Item.IotAdapter;
import com.example.yug08.BM_SWDC_yslee.Item.SingletonList;

import java.util.ArrayList;


/**
 * Created by yug08 on 2017-09-04.
 * 메인엡 엑비티비 클레스  실재 앱 컨트롤 클레스
 * <p>
 * $17-09-21_YSLEE 최초 GIT 추가
 * $17-09-27_YSLEE ID를 키값으로 상태저장 추가
 * $17-09-29_YSLEE Item 드래그 이동 구현 -> 꼭 필요 있을지 없을지 모르지만일단 있으면 좋지 않을까?
 */
public class MainAppActivity extends Activity {

    private IotAdapter iotAdapter;
    private ArrayList<IoTItem> items;

    private AlertDialog.Builder alertDialog;
    private boolean add = false;
    private BackgroundService backgroundService;


    private FloatingActionButton Fbtn;
    private RecyclerView recyclerView;
    private EditText et_country;
    private int edit_position;
    private View DialogView;
    private Switch Switch;

    private Paint paint = new Paint();
    private SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* 엑티비티 레이아웃 설정 */
        setContentView(R.layout.activity_main);

        init();
        initViews();
        eventinit();
        initDialog();

        iotAdapter.refresh();

        Intent intent = new Intent(
                getApplicationContext(),
                BackgroundService.class);
        startService(intent);

        onRefreshThread((float) 1.5);
    }

    /**
     * 화면갱신 주기 성정
     *
     * @param time 몇 초로 갱신할 것인지 성정
     */
    private void onRefreshThread(final float time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep((long) (time * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iotAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 필드 초기화
     */
    private void init() {
        VolleyRequestInstance requestInstance =
                VolleyRequestInstance.getInstance(getApplicationContext());


        sharedPreference = new SharedPreference(IoTUtil.getID());

        if ((items = sharedPreference.getItems(this, items)) == null) {
            items = new ArrayList<IoTItem>();
        }

        SingletonList singletonList = new SingletonList(items);
        items = singletonList.getItemsinstance();

        backgroundService = new BackgroundService();
    }

    /**
     * 필드에 있는 뷰  초기화
     */
    private void initViews() {
        Fbtn = findViewById(R.id.floatingActionButton);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        iotAdapter = new IotAdapter(items);
        recyclerView.setAdapter(iotAdapter);
        Switch = (Switch) findViewById(R.id.swicher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IoTItem item = (IoTItem) data.getSerializableExtra("a");

        if (item != null) {
            iotAdapter.addItem(item);
        }

    }

    /**
     * 이벤트 정의
     */
    private void eventinit() {
        /* 플로팅 액션 버튼 이벤트정의 */
        Fbtn.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (iotAdapter.getItemCount() < 4) {
                    Intent intent = new Intent(getApplicationContext(), IoTSetting.class);
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "매우 아쉽게도.....\n" +
                                    "아이템을 추가하실수 없습니다.\n" +
                                    "아이템은 4개 까지 만들수 있습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                IoTUtil.setOutGoingMode(isChecked);
                String mesage;
                if(isChecked){
                    mesage = "외출모드 설정!";
                }
                else {
                    mesage = "외출모드 해제!";
                }

                Fbtn.setBackgroundColor(Color.GREEN);
                Toast.makeText(getApplicationContext(),
                        mesage,
                        Toast.LENGTH_SHORT).show();
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
                iotAdapter.refresh();
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

    /**
     * 리사이클러뷰 스와이프 관련 기능 초기화 이벤트 에니메이션 정의
     */
    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback
                = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return iotAdapter.itemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
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

    /**
     * 스와이프 기능으로 해당 아이템 지우는 기능 정의
     */
    private void removeView() {
        if (DialogView.getParent() != null) {
            ((ViewGroup) DialogView.getParent()).removeView(DialogView);
        }
    }

    /**
     * 리사이클러뷰 동작에 대한 기능 호출 정의
     */
    private void initDialog() {
        alertDialog = new AlertDialog.Builder(this);
        DialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        alertDialog.setView(DialogView);
        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //추가하면 각 아이템 요소가 더해지는 부분 /* 현제는 안쓰이는 부분 입니다. */
                IoTItem newItem;
                if (!add) {
                    newItem = items.get(edit_position);
                    newItem.setNmae(et_country.getText().toString());
                    items.set(edit_position, newItem);
                    iotAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        et_country = DialogView.findViewById(R.id.et_country);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(
                getApplicationContext(),
                BackgroundService.class);
        stopService(intent);

        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        sharedPreference.saveItems(this, items);

        super.onStop();

    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
    }
}