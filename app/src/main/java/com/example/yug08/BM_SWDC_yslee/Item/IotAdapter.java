package com.example.yug08.BM_SWDC_yslee.Item;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yug08.BM_SWDC_yslee.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by yug08 on  2017-09-11
 * LEE YS
 * macOS 10.12.6
 * <p>
 * $17-09-21_YSLEE 최초 GIT 추가
 */

public class IotAdapter extends RecyclerView.Adapter<IotAdapter.IoTViewHolder> {
    private Context context;
    private ArrayList<IoTItem> items;
    private String ServerURL;
    private static final String updateURL = "/webapp/update_control.php";
    private static final String getstatesURL = "/webapp/get_States.php/";
    private String ID;
    private RequestQueue requestQueue;
    private StringRequest request;

    public IotAdapter(ArrayList<IoTItem> items, Context context) {
        this.items = items;
        requestQueue = Volley.newRequestQueue(context);
        this.context = context;
    }

    @Override
    public IoTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iotitem, parent, false);
        IoTViewHolder holder = new IoTViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(IoTViewHolder holder, int position) {
        final int posi = position;
        final IoTItem item = items.get(posi);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSenser(posi);
                refresh();
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSenser(posi);
                refresh();
            }
        });

        item.chengeImag();
        holder.imageView.setImageResource(item.getImag_resId());
        holder.textView.setText(item.getNmae());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public boolean itemMove(int fromPosition, int toPosition) {
        if (fromPosition < 0 || fromPosition >= items.size() || toPosition < 0 || toPosition >= items.size()) {
            return false;
        }

        IoTItem fromItem = items.get(fromPosition);
        items.remove(fromPosition);
        items.add(toPosition, fromItem);

        // 오류가 있음 원인 분석 필요함 ...
        notifyItemMoved(fromPosition, toPosition);
        notifyDataSetChanged();
        return true;
    }

    public void addItem(IoTItem item) {
        items.add(item);
        notifyItemInserted(items.size());
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }

    public void clickEvent(IoTItem item) {
        if (item.getCurrentStatus())
            item.setCurrentStatus(false);
        else
            item.setCurrentStatus(true);
    }

    /**
     * @ 아마 여기다가 갱신 루틴을 만들어야 할거같음..
     * 추석안에 프로토 타입 만들어두자 ...
     */

    private void updateSenser(int position) {

        final IoTItem item = items.get(position);
//                final int port = item.getPort();
        final int port = position + 1;
        String URL = "http://" + ServerURL + updateURL;

        final String PORT = String.valueOf(port);
        final String STATE = String.valueOf(steteToInt(!item.getCurrentStatus()));

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "서버 상태 혹은 서버 주소를 확인하세요.",
                        Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // POST 형식으로 넘겨줄 인자 맵
                HashMap<String, String> parameta = new HashMap<String, String>();
                parameta.put("ID", ID);
                parameta.put("PORT", PORT);
                parameta.put("STATE", STATE);

                Log.d("값변경:", "ID:" + ID +
                        "\nPORT:" + PORT +
                        "\nSTATE:" + STATE);
                return parameta;
            }
        };
        requestQueue.add(request);
    }

    public void refresh() {
        String URL = "http://" + ServerURL + getstatesURL;
        IoTItem item;
        int port;
        /**
         * 와 이거 진짜 너무 야매로 했다 ..
         * 나중에 2차 제작에서 전체 테이블을 한번에 끌어온뒤
         * 내부에서 값을 아이템에 나눠주는 방식으로 해봅시다 ....
         */
        for (int i = 0; i < items.size(); i++) {
            item = items.get(i);
            port = i + 1;
            final String[] state = new String[1];
            final String PORT = String.valueOf(port);
            final IoTItem finalItem = item;
            /**
             와 이거 진짜 ... 너무 야매로 한거 같은데
             내 머리로는 이런 방식 밖에 생각이 안납니다 .. 흐흑 ㅠㅠ
             */
            final IotAdapter iotAdapter = this;
            final int finalI = i;
            request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        state[0] = jsonObject.getString("state");
                        int s = Integer.parseInt(state[0]);
                        finalItem.setCurrentStatus(intToState(s));
                        finalItem.chengeImag();
                        Log.e("state:", state[0]);
                        iotAdapter.notifyItemChanged(finalI);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,
                            "서버 상태 혹은 서버 주소를 확인하세요.",
                            Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // POST 형식으로 넘겨줄 인자 맵
                    HashMap<String, String> parameta = new HashMap<String, String>();
                    parameta.put("ID", ID);
                    parameta.put("PORT", PORT);

                    return parameta;
                }
            };
            requestQueue.add(request);

        }

        Log.e("END", "END");

        for (int i = 0; i < items.size(); i++) {
            notifyItemChanged(i);
        }


    }

    /*불리언 값을 0 과 1 로 */
    private int steteToInt(boolean state) {
        if (state)
            return 1;
        else
            return 0;
    }

    private boolean intToState(int intstate) {
        if (intstate == 1)
            return true;
        else
            return false;
    }

    public void setServer(String ServerURL) {
        this.ServerURL = ServerURL;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * 아이템의 View(보여지는 컴포넌트?들) 을 정의 하는 클래스
     * 새로운것을 보여주고 싶다면 해당 뷰를 XML에 추가후
     * 해당 클래스에 정의 해야한다.
     */
    public class IoTViewHolder extends ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public IoTViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.itemName);
        }
    }
}
