package com.deepak.utacommunityservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "StudentDB";
    public static final String TBL_NAME = "StudentTBL";
    public static final String COL_FIRST_NAME = "firstName";
    public static final String COL_LAST_NAME = "lastName";
    public static final String COL_EMAIL_ADDRESS = "emailAddress";
    public static final String COL_MOBILE_NUMBER = "mobileNUmber";
    public static final String COL_PASSWORD = "password";

    SQLiteDatabase db;
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table StudentTBL(mobileNumber TEXT PRIMARY KEY,firstName TEXT,lastName TEXT,emailAddress TEXT,password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public Long insertStudentRecord(Student student) {

        db = this.getWritableDatabase();
        ContentValues mContentValues = new ContentValues();
        mContentValues.put(COL_FIRST_NAME,student.firstName);
        mContentValues.put(COL_LAST_NAME,student.lastName);
        mContentValues.put(COL_MOBILE_NUMBER,student.mobileNumber);
        mContentValues.put(COL_PASSWORD,student.password);
        mContentValues.put(COL_EMAIL_ADDRESS,student.emailAddress);
        return db.insert(TBL_NAME,null,mContentValues);
    }

    public boolean loginValidation(String mobileNumber,String password) {

        db = this.getReadableDatabase();
        boolean isLoggedIn = false;
        Cursor mCursor = db.rawQuery("select * from StudentTBL where mobileNumber=? AND password=?",new String[]{mobileNumber,password});

        if (mCursor.getCount()>0 && mCursor !=null && mCursor.moveToFirst()) {
            isLoggedIn = true;
        }
        return isLoggedIn;
    }
}
