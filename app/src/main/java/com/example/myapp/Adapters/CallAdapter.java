package com.example.myapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Database.DBmain;
import com.example.myapp.Models.CallItem;
import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.ViewHolder> {
        private List<CallItem> listData;
        private Context context;

    public CallAdapter(List<CallItem> list, Context context){
        this.listData = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final CallItem callItem = listData.get(i);

        viewHolder.textView2.setText(listData.get(i).getStaffNumber());
        viewHolder.textView1.setText(listData.get(i).getStaffName());
        final String num = listData.get(i).getStaffNumber().toString();
        final int id = listData.get(i).getId();
        viewHolder.callImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+num));
                context.startActivity(intent);
            }
        });

        viewHolder.deleteCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomDialog(id);
            }
        });
    }

    private void myCustomDialog(final int id){
        final AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
        myDialog.setTitle(" Delete Staff Contact");
        myDialog.setMessage("Are You Sure?");

        LayoutInflater inflater = LayoutInflater.from(context);


        myDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DBmain dBmain = new DBmain(context);
                dBmain.open();
                dBmain.deleteContact(id);
                dBmain.close();
                dialog.dismiss();
            }
        });

        myDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        myDialog.show();

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1,textView2;
        public ImageView callImage,deleteCall;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView1 = itemView.findViewById(R.id.staffName);
            this.textView2 = itemView.findViewById(R.id.staffNumber);
            this.callImage = itemView.findViewById(R.id.callImage);
            this.deleteCall= itemView.findViewById(R.id.deleteIcon);
        }
    }
}
