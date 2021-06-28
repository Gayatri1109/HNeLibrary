package com.example.elibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="HN.db";


    public DBHelper(Context context) {
        super(context,DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tbl_login(email TEXT primary key,username TEXT,password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tbl_login");
    }

    public Boolean insertData(String username,String email,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Username",username);
        contentValues.put("Email",email);
        contentValues.put("Password",password);
        long result = db.insert("tbl_login",null,contentValues);
        if(result==-1)return false;
        else
            return true;

    }

    public Boolean checkuseremail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from tbl_login where email = ? ",new String[]{email});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkuseremailpassword(String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from tbl_login where email = ?  and password = ?",new String[]{email,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;

    }
}
