package com.graduation.alarmsync.dbadmin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static String DBFILE_CONTACT;

    public DatabaseHelper(Context context, String path) {
        super(context, DBFILE_CONTACT, null, DB_VERSION);
        DBFILE_CONTACT = path;
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.SQL_CREATE_TB) ;
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL(ContactDBCtrct.SQL_DROP_TBL) ;
        onCreate(db) ;
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade(db, oldVersion, newVersion);
    }
}