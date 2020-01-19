package com.fahmuidrug.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class AlarmReminderContract {

    private AlarmReminderContract() {}

    public static final String CONTENT_AUTHORITY = "com.fahmuidrug";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_VEHICLE = "reminder-path";
    public static final String PATH_DRUG = "drug-path";

    public static final class AlarmReminderEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_VEHICLE);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEHICLE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEHICLE;

        public static final Uri CONTENT_DRUG_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DRUG);

        public static final String CONTENT_LIST_DRUG =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DRUG;

        public static final String CONTENT_ITEM_DRUG=
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DRUG;

        public final static String TABLE_NAME = "reminder";

        public final static String _ID = BaseColumns._ID;

        public static final String KEY_TITLE = "title";
        public static final String KEY_TIME = "time";
        public static final String KEY_REPEAT = "repeat";
        public static final String KEY_REPEAT_NO = "repeat_no";
        public static final String KEY_REPEAT_TYPE = "repeat_type";
        public static final String KEY_ACTIVE = "active";

        public final static String TABLE_DRUG = "drug";
        public static final String KEY_ID = "id";
        public static final String KEY_REMIND_ID = "id_remind";
        public static final String KEY_DRUG_NAME = "drug_name";
        public static final String KEY_DRUG_NO = "drug_no";
        public static final String KEY_DRUG_USE = "drug_use";
        public static final String KEY_DRUG_USE_STATIC = "drug_use_static";

        public final static String TABLE_TIME = "time";
        public static final String KEY_TIME_ID = "id_time";
        public static final String KEY_TIME_STAMP = "time_stamp";
        public static final String KEY_TIME_DRUG_NAME = "time_drug_name";

        public final static String TABLE_EVENT = "event";
        public static final String KEY_EVENT_ID = "id_time";
        public static final String KEY_EVENT_NAME = "time_stamp";
        public static final String KEY_EVENT_DES = "time_drug_name";


    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

}
