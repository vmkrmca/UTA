package com.deepak.utacommunityservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "StudentDB";
    public static final String TBL_NAME = "StudentTBL";
    public static final String COL_FIRST_NAME = "firstName";
    public static final String COL_LAST_NAME = "lastName";
    public static final String COL_EMAIL_ADDRESS = "emailAddress";
    public static final String COL_MOBILE_NUMBER = "mobileNUmber";
    public static final String COL_PASSWORD = "password";

    /* Event Table */

    public static final String EVENT_TBL_NAME = "EventTBL";
    public static final String EVENT_TITLE = "EventTitle";
    public static final String EVENT_DESCRIPTION = "EventDescription";
    public static final String EVENT_DATE = "EventDate";
    public static final String EVENT_TIME = "EventTime";
    public static final String EVENT_IMAGE = "EventImage";

    public static final String REQUEST_EVENT_TBL = "RequestEventTBL";


    SQLiteDatabase db;
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table StudentTBL(mobileNumber TEXT PRIMARY KEY,firstName TEXT,lastName TEXT,emailAddress TEXT,password TEXT)");
        db.execSQL("create table EventTBL(EventTitle TEXT,EventDescription TEXT,EventDate TEXT,EventTime TEXT,EventImage BLOB)");
        db.execSQL("create table RequestEventTBL(emailAddress TEXT,mobileNumber TEXT,eventTitle TEXT,eventDate TEXT,eventTime TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public long insertEventDetails(Event mEvent) {

        db = this.getWritableDatabase();
        ContentValues mContentValues = new ContentValues();
        mContentValues.put(EVENT_TITLE,mEvent.eventTitle);
        mContentValues.put(EVENT_DESCRIPTION,mEvent.eventDescription);
        mContentValues.put(EVENT_DATE,mEvent.eventDate);
        mContentValues.put(EVENT_TIME,mEvent.eventTime);
        mContentValues.put(EVENT_IMAGE,mEvent.eventImage);

        return db.insert(EVENT_TBL_NAME,null,mContentValues);

    }

    public ArrayList<Event> readEventDetails(){

        ArrayList<Event> mEventArrayList = new ArrayList<>();

        db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("select * from EventTBL",null);
        if (mCursor !=null && mCursor.getCount() >0) {

            if (mCursor.moveToFirst()){
                do {

                    Event mEvent = new Event();
                    mEvent.setEventTitle(mCursor.getString(mCursor.getColumnIndexOrThrow(EVENT_TITLE)));
                    mEvent.setEventDescription(mCursor.getString(mCursor.getColumnIndexOrThrow(EVENT_DESCRIPTION)));
                    mEvent.setEventDate(mCursor.getString(mCursor.getColumnIndexOrThrow(EVENT_DATE)));
                    mEvent.setEventTime(mCursor.getString(mCursor.getColumnIndexOrThrow(EVENT_TIME)));
                    mEvent.setEventImage(mCursor.getBlob(mCursor.getColumnIndexOrThrow(EVENT_IMAGE)));
                    mEventArrayList.add(mEvent);

                }while (mCursor.moveToNext());
            }
        }
        return mEventArrayList;

    }
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

    public long insertRequestEventDetails(String emailAddress,String mobileNumber,String eventTitle,String eventDate,String eventTime) {

        db = this.getWritableDatabase();
        ContentValues mContentValues = new ContentValues();
        mContentValues.put("emailAddress",emailAddress);
        mContentValues.put("mobileNumber",mobileNumber);
        mContentValues.put("eventTitle",eventTitle);
        mContentValues.put("eventDate",eventDate);
        mContentValues.put("eventTime",eventTime);

        return db.insert(REQUEST_EVENT_TBL,null,mContentValues);
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

    public Student readDataFromMobileNumber(String mobileNumber) {

        Student mStudent = new Student();
        db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("select * from StudentTBL where mobileNUmber=?",new String[]{mobileNumber});

        if (mCursor !=null && mCursor.getCount() >0 && mCursor.moveToFirst()) {

                    mStudent.setFirstName(mCursor.getString(mCursor.getColumnIndexOrThrow(COL_FIRST_NAME)));
                    mStudent.setLastName(mCursor.getString(mCursor.getColumnIndexOrThrow(COL_LAST_NAME)));
                    mStudent.setEmailAddress(mCursor.getString(mCursor.getColumnIndexOrThrow(COL_EMAIL_ADDRESS)));
            }

        return mStudent;
    }


    public ArrayList<EventRequest> readEventRequestDetails() {

        ArrayList<EventRequest> mEventArrayList = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("select * from RequestEventTBL",null);
        if (mCursor !=null && mCursor.getCount() >0) {
            if (mCursor.moveToFirst()){
                do {
                    EventRequest mEvent = new EventRequest();
                    mEvent.setEmailAddress(mCursor.getString(mCursor.getColumnIndexOrThrow("emailAddress")));
                    mEvent.setEventTitle(mCursor.getString(mCursor.getColumnIndexOrThrow("eventTitle")));
                    mEvent.setEventDate(mCursor.getString(mCursor.getColumnIndexOrThrow("eventDate")));
                    mEvent.setEventTime(mCursor.getString(mCursor.getColumnIndexOrThrow("eventTime")));
                    mEvent.setMobileNumber(mCursor.getString(mCursor.getColumnIndexOrThrow("mobileNumber")));
                    mEventArrayList.add(mEvent);

                }while (mCursor.moveToNext());
            }
        }
        return mEventArrayList;


    }
}
