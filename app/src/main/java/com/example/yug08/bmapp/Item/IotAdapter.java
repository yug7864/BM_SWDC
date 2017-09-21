package com.example.yug08.bmapp.Item;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yug08.bmapp.R;


public class IotAdapter extends RecyclerView.Adapter<IotAdapter.IoTViewHolder> {
    private  ArrayList<IoTItem> items;

    public IotAdapter(ArrayList<IoTItem> items)
    {
        this.items = items;
    }


    @Override
    public IoTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iotitem,parent,false);
        IoTViewHolder holder = new IoTViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(IoTViewHolder holder,final int position) {
        final IoTItem item = items.get(position);
        holder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(item.getOnOff())
                    item.setOnOff(false);
                else
                    item.setOnOff(true);

                notifyItemChanged(position);
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

    public void addItem(IoTItem item){
        items.add(item);
        notifyItemInserted(items.size());
    }

    public  void removeItem(int position){
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,items.size());
    }

    /*
        IoT 센서 정보를 화면에 보여주는 view홀더 새로운 정보를 보여주고 싶다면 이 클레스를 수정해야된다.
     */
    public class IoTViewHolder extends ViewHolder {
        public ImageView imageView ;
        public TextView textView ;

        public IoTViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView  = (TextView) itemView.findViewById(R.id.itemName);
        }
    }
}



