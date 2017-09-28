package com.example.yug08.BM_SWDC_yslee.Item;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yug08.BM_SWDC_yslee.R;

import java.util.ArrayList;


/**
 * Created by yug08 on  2017-09-11
 * LEE YS
 * macOS 10.12.6
 *
 * @17-09-21_YSLEE 최초 GIT 추가
 *
 *
 */
public class IotAdapter extends RecyclerView.Adapter<IotAdapter.IoTViewHolder> {
    private ArrayList<IoTItem> items;

    public IotAdapter(ArrayList<IoTItem> items) {
        this.items = items;
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
                clickEvent(item);
                notifyItemChanged(posi);
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent(item);
                notifyItemChanged(posi);
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

    public void clickEvent(IoTItem item){
        if (item.getOnOff())
            item.setOnOff(false);
        else
            item.setOnOff(true);
    }
    /*
        @ 아마 여기다가 갱신 루틴을 만들어야 할거같음..
        추석안에 프로토 타입 만들어두자 ...
     */

    /**
     * 아이템의 View(보여지는 컴포넌트?들) 을 정의 하는 클래스
     * 새로운것을 보여주고 싶다면 해당 뷰를 XML에 추가후
     * 해당 클래스에 정의 해야한다.
     */
    public class IoTViewHolder extends ViewHolder{
        public ImageView imageView;
        public TextView textView;

        public IoTViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.itemName);
        }
    }
}



