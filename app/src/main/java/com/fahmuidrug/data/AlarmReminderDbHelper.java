package com.fahmuidrug.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmReminderDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reminder.db";

    private static final int DATABASE_VERSION = 1;

    public AlarmReminderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a String that contains the SQL statement to create the reminder table
        String SQL_CREATE_ALARM_TABLE =  "CREATE TABLE " + AlarmReminderContract.AlarmReminderEntry.TABLE_NAME + " ("
                + AlarmReminderContract.AlarmReminderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_TITLE + " TEXT, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_TIME + " TEXT, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT + " TEXT, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO + " TEXT, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE + " TEXT, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE + " TEXT " + " );";

        String SQL_CREATE_DRUG_TABLE =  "CREATE TABLE " + AlarmReminderContract.AlarmReminderEntry.TABLE_DRUG + " ("
                + AlarmReminderContract.AlarmReminderEntry.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AlarmReminderContract.AlarmReminderEntry.KEY_REMIND_ID + " TEXT, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_DRUG_NAME + " TEXT, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_DRUG_NO + " TEXT, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_DRUG_USE + " TEXT, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_DRUG_USE_STATIC + " TEXT " + " );";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_ALARM_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_DRUG_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Cursor getListDrug1(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT KEY_DRUG_NAME FROM "+AlarmReminderContract.AlarmReminderEntry.TABLE_DRUG +
                " WHERE " + AlarmReminderContract.AlarmReminderEntry.KEY_REMIND_ID +" = "+ ID,null);
        return data;
    }


}
