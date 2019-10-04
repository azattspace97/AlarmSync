package com.graduation.alarmsync.dbadmin;

public class DatabaseContract {
    private DatabaseContract() {};

    public static final String TABLE_NAME = "Alarm";
    public static final String COL_TIME = "Time";
    public static final String COL_DAYSOFWEEK = "DaysOfWeek";
    public static final String COL_ENABLED = "Enabled";
    public static final String COL_VIBRATE = "Vibrate";
    public static final String COL_MESSAGE = "Message";
    public static final String COL_ALERT = "Alert";

    public static final String SQL_CREATE_TB = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " +
            "(" +
            COL_TIME +        " TEXT NOT NULL"   +   ", " +
            COL_DAYSOFWEEK +  " INTEGER"            +   ", " +
            COL_ENABLED +     " INTEGER NOT NULL"   +   ", " +
            COL_VIBRATE +     " INTEGER NOT NULL"   +   ", " +
            COL_MESSAGE +     " TEXT"               +   ", " +
            COL_ALERT +       " TEXT)";

    public static final String SQL_DROP_TB = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String SQL_SELECT = "SELECT * FROM " + TABLE_NAME;

    public static final String SQL_SELECT_SORT = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_TIME + " ASC";

    //public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TABLE_NAME + " " +
    public static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " " +
            "(" + COL_TIME + ", " + COL_DAYSOFWEEK + ", " + COL_ENABLED + ", " + COL_VIBRATE + ", " + COL_MESSAGE + ", " + COL_ALERT + ") VALUES ";

    public static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME;
}