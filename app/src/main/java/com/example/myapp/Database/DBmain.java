package com.example.myapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapp.Models.CallItem;
import com.example.myapp.Models.Events;

import java.util.ArrayList;
import java.util.List;

public class DBmain {
    public static final String KEY_ID = "ID";
    public static final String KEY_StaffName = "Name";
    public static final String KEY_StaffNumber = "Number";
    public static final String KEY_EVENTName = "EVENT";
    public static final String KEY_EVENTDate = "DATE";
    public static final String KEY_EVENTDetails = "DETAILS";


    private final String DATABASE_NAME = "FHSSDB";
    private final String DATABASE_TABLE = "CALL_TABLE";
    private final String DATABASE_EVENT_TABLE = "EVENT_CALENDAR";
    private final int DATABASE_VERSION = 3;

    private DBHelper OurHelper;
    private final Context context;
    private SQLiteDatabase ourDatabase;

    public DBmain(Context context){
        this.context = context;
    }

    public class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String SQLCode = "CREATE TABLE "+DATABASE_TABLE+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_StaffName+" TEXT NOT NULL, "+KEY_StaffNumber+" TEXT NOT NULL);";
            String SQLCode1 = "CREATE TABLE "+DATABASE_EVENT_TABLE+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_EVENTName+" TEXT NOT NULL, "+KEY_EVENTDate+" TEXT NOT NULL,"+KEY_EVENTDetails+" TEXT NOT NULL);";
            db.execSQL(SQLCode);
            db.execSQL(SQLCode1);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_EVENT_TABLE);
            onCreate(db);
        }
    }

    public DBmain open(){
        OurHelper = new DBHelper(context);
        ourDatabase = OurHelper.getWritableDatabase();
        return  this;

    }
    public void close(){
        OurHelper.close();
    }

    public long addData(String name, String number){

        ContentValues cv  = new ContentValues();
        cv.put(KEY_StaffName,name);
        cv.put(KEY_StaffNumber,number);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public List<CallItem> getAllContact(){
        List<CallItem> callList = new ArrayList<CallItem>();
        String selctQuery ="SELECT * FROM "+DATABASE_TABLE;
        Cursor cursor = ourDatabase.rawQuery(selctQuery,null);
        callList.clear();
        if(cursor.moveToFirst()){
            do{
                CallItem callItem = new CallItem(cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(0)));
                callList.add(callItem);
            }while (cursor.moveToNext());
        }
       // ourDatabase.close();
        return callList;
    }

    public Integer deleteContact (Integer id) {
        return ourDatabase.delete(DATABASE_TABLE,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public void insertEvent(String date, String event,String details){
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_EVENTDate,date);
            contentValues.put(KEY_EVENTName,event);
            contentValues.put(KEY_EVENTDetails,details);
            ourDatabase.insert(DATABASE_EVENT_TABLE,null,contentValues);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Events> readEvent(String date){
        List<Events> events = new ArrayList<>();
        try{
            String query = "Select * from "+DATABASE_EVENT_TABLE+" where "+ KEY_EVENTDate +" ='"+date+"';";
            Cursor cursor = ourDatabase.rawQuery(query,null);
           // events.clear();
            if(cursor.moveToFirst()){
                do{
                    Events event = new Events(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                    events.add(event);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return events;
    }

    public void deleteEvent (Integer id) {
        try{
            ourDatabase.delete(DATABASE_EVENT_TABLE,
                    "id = ? ",
                    new String[] { Integer.toString(id) });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
