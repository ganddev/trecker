package com.trecker.treckeruebung.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by bjornahlfeld on 18.11.14.
 */
public class TreckerDatabase extends SQLiteOpenHelper {

    private static final String TAG = TreckerDatabase.class.getName();

    private static final String DATABASE_NAME = "trecker.db";

    private static final int DATABASE_VERSION = 1;


    public interface Tables {
        String CUSTOMERS = "customers";
        String FIELDS = "fields";
    }

    public TreckerDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "Create database for trecker app");
        sqLiteDatabase.execSQL("CREATE TABLE " + Tables.CUSTOMERS + " ("
                + TreckerContract.Customers.CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TreckerContract.Customers.CUSTOMER_NAME + " TEXT,"
                + TreckerContract.Customers.CUSTOMER_ADDRESS + " TEXT,"
                + TreckerContract.Customers.CUSTOMER_CITY + " TEXT,"
                + TreckerContract.Customers.CUSTOMER_FON + " TEXT,"
                + "UNIQUE (" + TreckerContract.Customers.CUSTOMER_ID + ") ON CONFLICT REPLACE);");

        sqLiteDatabase.execSQL("CREATE TABLE " + Tables.FIELDS + " ("
                + TreckerContract.Fields.FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TreckerContract.Fields.FIELD_NAME + " TEXT, "
                + TreckerContract.Fields.FIELD_BUSINESS_PARTNER + " TEXT, "
                + TreckerContract.Fields.FIELD_LATITUDE + " REAL,"
                + TreckerContract.Fields.FIELD_LONGITUDE + " REAL, "
                + "UNIQUE (" + TreckerContract.Fields.FIELD_ID + ") ON CONFLICT REPLACE);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
