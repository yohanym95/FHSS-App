package com.example.myapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class StaffFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    CallAdapter callAdapter;
    ArrayList<CallItem> callItems;
    TextInputLayout staffName,staffNumber;

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
        callItems.add(new CallItem("Staff 1","0112345678"));
        callItems.add(new CallItem("Staff 1","0112345678"));
        recyclerView = view.findViewById(R.id.recycle_view);
        callAdapter = new CallAdapter(callItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(callAdapter);

    }

    private void myCustomDialog(){
        final AlertDialog.Builder myDialog = new AlertDialog.Builder(getContext());
        myDialog.setTitle("Staff Service");
        myDialog.setTitle("Add Staff Contact Details");

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
