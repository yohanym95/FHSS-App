package com.example.myapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapp.Adapters.CallAdapter;
import com.example.myapp.Database.DBmain;
import com.example.myapp.Models.CallItem;

import java.util.ArrayList;
import java.util.List;

public class StaffFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    CallAdapter callAdapter;
    List<CallItem> callItems;
    TextInputLayout staffName,staffNumber;
    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_staff, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        callItems = new ArrayList<>();
        DBmain dBmain = new DBmain(getContext());
        dBmain.open();
        callItems = dBmain.getAllContact();
        dBmain.close();
        recyclerView = view.findViewById(R.id.recycle_view);
        callAdapter = new CallAdapter(callItems,getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(callAdapter);

        floatingActionButton = view.findViewById(R.id.floatingButton1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomDialog();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        DBmain dBmain = new DBmain(getContext());
        dBmain.open();
        callItems = dBmain.getAllContact();
        dBmain.close();
        recyclerView = view.findViewById(R.id.recycle_view);
        callAdapter = new CallAdapter(callItems,getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(callAdapter);


    }

    @Override
    public void onPause() {
        super.onPause();
        DBmain dBmain = new DBmain(getContext());
        dBmain.open();
        callItems = dBmain.getAllContact();
        dBmain.close();
        recyclerView = view.findViewById(R.id.recycle_view);
        callAdapter = new CallAdapter(callItems,getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(callAdapter);
    }

    private void myCustomDialog(){
        final AlertDialog.Builder myDialog = new AlertDialog.Builder(getContext());
        myDialog.setTitle("Staff Service");
        myDialog.setMessage("Add Staff Contact Details");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View myCustomLayout = inflater.inflate(R.layout.contactcustom,null);

        staffName = myCustomLayout.findViewById(R.id.staffName1);
        staffNumber = myCustomLayout.findViewById(R.id.staffNumber1);

        myDialog.setView(myCustomLayout);

        myDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = staffName.getEditText().getText().toString();
                final  String number = staffNumber.getEditText().getText().toString();

                if(name.length() == 0 ){
                    Toast.makeText(getContext(),"Please add the staff name",Toast.LENGTH_LONG).show();
                }else if(number.length() != 10){
                    Toast.makeText(getContext(),"Please Add the correct number EX - 0111234567",Toast.LENGTH_LONG).show();
                }else{
                    DBmain dBmain = new DBmain(getContext());
                    dBmain.open();
                    dBmain.addData(name, number);
                    dBmain.close();
                    Toast.makeText(getContext(),"Successfully Added the details",Toast.LENGTH_LONG).show();

                    DBmain dBmain1 = new DBmain(getContext());
                    dBmain1.open();
                    callItems = dBmain1.getAllContact();
                    recyclerView = view.findViewById(R.id.recycle_view);
                    CallAdapter callAdapter1 = new CallAdapter(callItems,getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(callAdapter1);
                    dBmain1.close();
                }
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
