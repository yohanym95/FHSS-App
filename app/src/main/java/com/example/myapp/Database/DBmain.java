package com.example.myapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapp.Models.CallItem;

import java.util.ArrayList;
import java.util.List;

public class DBmain {
    public static final String KEY_ID = "ID";
    public static final String KEY_StaffName = "Name";
    public static final String KEY_StaffNumber = "Number";


    private final String DATABASE_NAME = "FHSSDB";
    private final String DATABASE_TABLE = "CALL_TABLE";
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
            db.execSQL(SQLCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
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
       // ourDatabase.close();
      //  ourDatabase.de
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
}
