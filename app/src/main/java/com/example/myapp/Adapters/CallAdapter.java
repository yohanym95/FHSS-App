package com.example.myapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapp.Models.CallItem;
import com.example.myapp.R;

import java.util.ArrayList;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.ViewHolder> {
    private ArrayList<CallItem> listData;

    public CallAdapter(ArrayList<CallItem> list){
        this.listData = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final CallItem callItem = listData.get(i);
        viewHolder.textView2.setText(listData.get(i).getStaffNumber());
        viewHolder.textView1.setText(listData.get(i).getStaffName());
        viewHolder.callImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1,textView2;
        public ImageView callImage;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView1 = itemView.findViewById(R.id.staffName);
            this.textView2 = itemView.findViewById(R.id.staffNumber);
            this.callImage = itemView.findViewById(R.id.callImage);
        }
    }
}
