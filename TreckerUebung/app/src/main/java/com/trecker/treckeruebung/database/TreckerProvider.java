package com.trecker.treckeruebung.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

/**
 * Created by bjornahlfeld on 18.11.14.
 */
public class TreckerProvider extends ContentProvider {

    private static final int CUSTOMERS = 100;
    private static final int CUSTOMER_ID = 101;
    private static final int FIELDS = 200;
    private static final int FIELDS_ID = 201;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final String TAG = TreckerProvider.class.getName();
    private TreckerDatabase mOpenHelpener;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TreckerContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, "customers", CUSTOMERS);
        matcher.addURI(authority, "customers/*", CUSTOMER_ID);
        matcher.addURI(authority, "fields", FIELDS);
        matcher.addURI(authority, "fields/*", FIELDS_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelpener = new TreckerDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mOpenHelpener.getReadableDatabase();
        final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        String having = null;
        String groupBy = null;
        switch (sUriMatcher.match(uri)) {
            case CUSTOMERS:
                builder.setTables(TreckerDatabase.Tables.CUSTOMERS);
                break;
            case FIELDS:
                builder.setTables(TreckerDatabase.Tables.FIELDS);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor c = builder.query(database, projection, selection,
                selectionArgs, groupBy, having, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CUSTOMERS:
                return TreckerContract.Customers.CONTENT_TYPE;
            case CUSTOMER_ID:
                return TreckerContract.Customers.CONTENT_ITEM_TYPE;
            case FIELDS:
                return TreckerContract.Fields.CONTENT_TYPE;
            case FIELDS_ID:
                return TreckerContract.Fields.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase database = mOpenHelpener.getWritableDatabase();
        long id = -1;
        final String nullColumnHack = null;
        Uri contentUri = null;
        switch (sUriMatcher.match(uri)) {
            case FIELDS:
                id = database.insert(TreckerDatabase.Tables.FIELDS, nullColumnHack, values);
                contentUri = TreckerContract.Fields.CONTENT_URI;
                break;
            case CUSTOMERS:
                id = database.insert(TreckerDatabase.Tables.CUSTOMERS, nullColumnHack, values);
                contentUri = TreckerContract.Customers.CONTENT_URI;
                break;
        }
        if (id > -1) {
            Uri insertedId = ContentUris.withAppendedId(contentUri, id);
            getContext().getContentResolver().notifyChange(insertedId, null);
            return insertedId;
        } else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase database = mOpenHelpener.getWritableDatabase();
        int rows = -1;
        switch (sUriMatcher.match(uri)) {
            case FIELDS:
                rows = database.delete(TreckerDatabase.Tables.FIELDS, selection, selectionArgs);
                break;
            case CUSTOMERS:
                rows = database.delete(TreckerDatabase.Tables.CUSTOMERS, selection, selectionArgs);
                break;
            default:
                break;
        }
        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase database = mOpenHelpener.getWritableDatabase();
        int rows = -1;
        switch (sUriMatcher.match(uri)) {
            case FIELDS:
                rows = database.update(TreckerDatabase.Tables.FIELDS, values, selection,
                        selectionArgs);
                break;
            case CUSTOMERS:
                rows = database.update(TreckerDatabase.Tables.CUSTOMERS, values, selection,
                        selectionArgs);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rows;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values){
        final SQLiteDatabase database = mOpenHelpener.getWritableDatabase();
        int numInserted = 0;
        String table = null;
        switch (sUriMatcher.match(uri)) {
            case CUSTOMERS:
                table = TreckerDatabase.Tables.CUSTOMERS;
                break;
            case FIELDS:
                table = TreckerDatabase.Tables.FIELDS;
                break;
        }

        database.beginTransaction();
        try {
            for (ContentValues cv : values) {
                Log.d(TAG, "Field id: " +cv.get(TreckerContract.Fields.FIELD_ID));
                long newID = database.insertWithOnConflict(table, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
                if (newID <= 0) {
                    throw new SQLException("Failed to insert row into " + uri);
                }
            }
            database.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            numInserted = values.length;
        } finally {
            database.endTransaction();
        }
        return numInserted;
    }
}
