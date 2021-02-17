package com.example.myapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.Adapters.CallAdapter;
import com.example.myapp.Adapters.EventAdapter;
import com.example.myapp.Database.DBmain;
import com.example.myapp.Models.Events;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private Button btnSave;
    private EditText eventText,detailsText;
    private String selectedDate;

    RecyclerView recyclerView;
    EventAdapter eventAdapter;
    List<Events> eventsList;
    DBmain dBmain;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        calendarView = findViewById(R.id.calendarView);
        btnSave = findViewById(R.id.btnEventSave);
        eventText = findViewById(R.id.editText);
        recyclerView = findViewById(R.id.event_recyleView);
        detailsText = findViewById(R.id.editText1);

        mToolbar = findViewById(R.id.eventToolbar);
        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Event Details");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        eventsList = new ArrayList<>();
        dBmain = new DBmain(this);
        dBmain.open();
        if(selectedDate != null){
            eventsList.clear();
            eventsList = dBmain.readEvent(selectedDate);
        }

        eventAdapter = new EventAdapter(eventsList,EventActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(eventAdapter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = Integer.toString(year)+"-"+ Integer.toString(month)+"-"+ Integer.toString(dayOfMonth);
                eventsList.clear();
                eventsList = dBmain.readEvent(selectedDate);
                eventAdapter = new EventAdapter(eventsList,EventActivity.this);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(eventAdapter);


            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedDate == null){
                    Toast.makeText(getApplicationContext(),"Please Select the Date",Toast.LENGTH_LONG).show();
                }else if(eventText.getText().toString() == null){
                    Toast.makeText(getApplicationContext(),"Please Add the Event Name",Toast.LENGTH_LONG).show();
                }else if(detailsText.getText().toString() == null){
                    Toast.makeText(getApplicationContext(),"Please Add the Event Details",Toast.LENGTH_LONG).show();
                }else{
                   // Toast.makeText(getApplicationContext(),selectedDate+" "+eventText.getText().toString()+" "+detailsText.getText().toString(),Toast.LENGTH_LONG).show();
                    dBmain.insertEvent(selectedDate,eventText.getText().toString(),detailsText.getText().toString());
                    eventsList.clear();
                    eventsList = dBmain.readEvent(selectedDate);
                   // Toast.makeText(getApplicationContext(),eventsList.size()+"",Toast.LENGTH_LONG).show();
                    if(eventsList.size() > 0){
                        eventText.setText("");
                        detailsText.setText("");
                        Toast.makeText(getApplicationContext(),"Successfully Added the Event",Toast.LENGTH_LONG).show();
                        eventAdapter = new EventAdapter(eventsList,EventActivity.this);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(eventAdapter);

                    }
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dBmain.close();
    }
}