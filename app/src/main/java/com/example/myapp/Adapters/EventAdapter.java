package com.example.myapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.Database.DBmain;
import com.example.myapp.Models.Events;
import com.example.myapp.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    private List<Events> listEvents;
    private Context context;

    public EventAdapter(List<Events> events, Context context){
        this.listEvents = events;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.customevent, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView1.setText(listEvents.get(i).getEventName());
        viewHolder.textView2.setText(listEvents.get(i).getEventDetails());
        final int id = listEvents.get(i).getId();

        viewHolder.deletImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomDialog(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1,textView2;
        public ImageView deletImage;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView1 = itemView.findViewById(R.id.eventName);
            this.textView2 = itemView.findViewById(R.id.eventDetails);
            this.deletImage = itemView.findViewById(R.id.deleteIcon);
        }
    }

    private void myCustomDialog(final int id){
        final AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
        myDialog.setTitle(" Delete Event");
        myDialog.setMessage("Are You Sure?");

        LayoutInflater inflater = LayoutInflater.from(context);


        myDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DBmain dBmain = new DBmain(context);
                dBmain.open();
                dBmain.deleteEvent(id);
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
}
