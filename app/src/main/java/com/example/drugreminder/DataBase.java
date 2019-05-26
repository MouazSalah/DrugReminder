package com.example.drugreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**

 */

public class DataBase extends SQLiteOpenHelper
{

    public static final String DatabaseName = "3LAG";
    public static final String UserTable = "USER";
    public static final String DataTable ="DATA";

    public DataBase(Context context)
    {
        super(context, DatabaseName, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

      //  db.execSQL("create table " + TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SURNAME TEXT, MARKS TEXT )");

        db.execSQL("create table " + UserTable + " ( USERID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, AGE TEXT, DR TEXT, DIAGNOSIS TEXT, PROCEDURES TEXT )");
        db.execSQL("create table " + DataTable + " ( DOAID INTEGER PRIMARY KEY AUTOINCREMENT,   DOAUSERID  TEXT , DOANAME TEXT , DOSE TEXT, ROUTE TEXT, NOTE TEXT, TIME1 TEXT, TIME2 TEXT, TIME3 TEXT, TIME4 TEXT, TIME5 TEXT, TIME6 TEXT, TIME7 TEXT, TIME8 TEXT, TIME9 TEXT, TIME10 TEXT, TIME11 TEXT, TIME12 TEXT, FOREIGN KEY(DOAUSERID) REFERENCES USER(USERID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + UserTable);

        db.execSQL("DROP TABLE IF EXISTS " + DataTable);
        onCreate(db);
    }


    public boolean insertUser(String name, String age, String dr, String dio, String proc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("AGE", age);
        values.put("DR", dr);
        values.put("DIAGNOSIS", dio);
        values.put("PROCEDURES", proc);
        Long result = db.insert(UserTable, null, values);

        if(result==-1)
            return  false;
        else
            return true;
    }
    public boolean insertDOA(String userid , String DOAname, String dose, String route, String note, String [] time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("DOAUSERID", userid);
        values.put("DOANAME", DOAname);
        values.put("DOSE", dose);
        values.put("ROUTE", route);
        values.put("NOTE", note);
        values.put("TIME1", time[0]);
        values.put("TIME2", time[1]);
        values.put("TIME3", time[2]);
        values.put("TIME4", time[3]);
        values.put("TIME5", time[4]);
        values.put("TIME6", time[5]);
        values.put("TIME7", time[6]);
        values.put("TIME8", time[7]);
        values.put("TIME9", time[8]);
        values.put("TIME10", time[9]);
        values.put("TIME11", time[10]);
        values.put("TIME12", time[11]);
        Long result = db.insert(DataTable, null, values);

        if(result==-1)
            return  false;
        else
            return true;
    }

    public Cursor getUsers()
    {
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ UserTable, null);
        return res;
    }

    public Cursor getDoa()
    {
        SQLiteDatabase db= this.getWritableDatabase();
       // db.query()
        Cursor res = db.rawQuery("select * from "+ DataTable, null);
        return res;
    }

    public void removeSingleContact(String row)
    {

        SQLiteDatabase database = this.getWritableDatabase();

        database.execSQL("DELETE FROM " + DatabaseName + " WHERE " + DataTable + "= '" + row + "'");

        database.close();
    }

    public Integer deleteuser(String id)
    {
        SQLiteDatabase dp=this.getWritableDatabase();
        return dp.delete(UserTable, "USERID= ?", new String[]{id});
    }

    public Integer deleteDoa(String id)
    {
        SQLiteDatabase dp=this.getWritableDatabase();
        return dp.delete(DataTable, "DOAID= ?", new String[]{id});
    }

}

