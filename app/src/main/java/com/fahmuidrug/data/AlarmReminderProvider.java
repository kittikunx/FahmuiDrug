package com.fahmuidrug.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AlarmReminderProvider extends ContentProvider {

    public static final String LOG_TAG = AlarmReminderProvider.class.getSimpleName();

    private static final int REMINDER = 100;

    private static final int REMINDER_ID = 101;

    private static final int DRUG = 102;

    private static final int DRUG_ID = 103;


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AlarmReminderContract.CONTENT_AUTHORITY, AlarmReminderContract.PATH_VEHICLE, REMINDER);

        sUriMatcher.addURI(AlarmReminderContract.CONTENT_AUTHORITY, AlarmReminderContract.PATH_VEHICLE + "/#", REMINDER_ID);

        sUriMatcher.addURI(AlarmReminderContract.CONTENT_AUTHORITY, AlarmReminderContract.PATH_DRUG, DRUG);

        sUriMatcher.addURI(AlarmReminderContract.CONTENT_AUTHORITY, AlarmReminderContract.PATH_DRUG + "/#", DRUG_ID);
    }

    private AlarmReminderDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new AlarmReminderDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor = null;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case REMINDER:
                cursor = database.query(AlarmReminderContract.AlarmReminderEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case REMINDER_ID:
                selection = AlarmReminderContract.AlarmReminderEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(AlarmReminderContract.AlarmReminderEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case DRUG:
                cursor = database.query(AlarmReminderContract.AlarmReminderEntry.TABLE_DRUG, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case DRUG_ID:
                selection = AlarmReminderContract.AlarmReminderEntry.KEY_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(AlarmReminderContract.AlarmReminderEntry.TABLE_DRUG, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }


        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case REMINDER:
                return AlarmReminderContract.AlarmReminderEntry.CONTENT_LIST_TYPE;
            case REMINDER_ID:
                return AlarmReminderContract.AlarmReminderEntry.CONTENT_ITEM_TYPE;
            case DRUG:
                return AlarmReminderContract.AlarmReminderEntry.CONTENT_LIST_DRUG;
            case DRUG_ID:
                return AlarmReminderContract.AlarmReminderEntry.CONTENT_ITEM_DRUG;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case REMINDER:
                return insertReminder(uri, contentValues);
            case DRUG:
                return insertDrug(uri, contentValues);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertReminder(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(AlarmReminderContract.AlarmReminderEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertDrug(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(AlarmReminderContract.AlarmReminderEntry.TABLE_DRUG, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case REMINDER:
                rowsDeleted = database.delete(AlarmReminderContract.AlarmReminderEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case REMINDER_ID:
                selection = AlarmReminderContract.AlarmReminderEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(AlarmReminderContract.AlarmReminderEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DRUG:
                rowsDeleted = database.delete(AlarmReminderContract.AlarmReminderEntry.TABLE_DRUG, selection, selectionArgs);
                break;
            case DRUG_ID:
                selection = AlarmReminderContract.AlarmReminderEntry.KEY_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(AlarmReminderContract.AlarmReminderEntry.TABLE_DRUG, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case REMINDER:
                return updateReminder(uri, contentValues, selection, selectionArgs);
            case REMINDER_ID:
                selection = AlarmReminderContract.AlarmReminderEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateReminder(uri, contentValues, selection, selectionArgs);
            case DRUG:
                return updateDrug(uri, contentValues, selection, selectionArgs);
            case DRUG_ID:
                selection = AlarmReminderContract.AlarmReminderEntry.KEY_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateDrug(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateReminder(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(AlarmReminderContract.AlarmReminderEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    private int updateDrug(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(AlarmReminderContract.AlarmReminderEntry.TABLE_DRUG, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

}
