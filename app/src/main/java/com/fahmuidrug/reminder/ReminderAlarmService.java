package com.fahmuidrug.reminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import com.fahmuidrug.R;
import com.fahmuidrug.ReminderActivity;
import com.fahmuidrug.SkipActivity;
import com.fahmuidrug.data.AlarmReminderContract;

public class ReminderAlarmService extends IntentService {
    private static final String TAG = ReminderAlarmService.class.getSimpleName();

    public static final int NOTIFICATION_ID = 42;
    //This is a deep link intent, and needs the task stack
    public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {
        Intent action = new Intent(context, ReminderAlarmService.class);
        action.setData(uri);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public ReminderAlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Uri uri = intent.getData();

        //Display a notification to view the task details
        Intent action = new Intent(this, ReminderActivity.class);
        action.setData(uri);
        PendingIntent operation = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //Grab the task description
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String title = "";
        String description = "";
        try {
            if (cursor != null && cursor.moveToFirst()) {

                title = AlarmReminderContract.getColumnString(cursor, AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
                description = AlarmReminderContract.getColumnString(cursor, AlarmReminderContract.AlarmReminderEntry.KEY_TIME);


            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Intent take_intent = new Intent(this,ReminderActivity.class);
        take_intent.setData(uri);
        PendingIntent take_intentPen = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(take_intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        /*Intent take_intent = new Intent(this,TakeMedicineActivity.class);
        take_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent take_intentPen = PendingIntent.getActivity(this,0,take_intent,PendingIntent.FLAG_ONE_SHOT);*/

        Intent skip_intent = new Intent(this, SkipActivity.class);
        skip_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent skip_intentPen = PendingIntent.getActivity(this,0,skip_intent, PendingIntent.FLAG_ONE_SHOT);

        Notification note = new NotificationCompat.Builder(this)
                .setContentTitle("แจ้งเตือน  "+title)
                .setContentText("ทานยาเวลา "+description+ " น.")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setSmallIcon(R.drawable.ic_takemed)
                .setColor(Color.RED)
                .addAction(R.drawable.ic_done_all," ทานยา ",take_intentPen)
                .addAction(R.drawable.ic_skip," ยังไม่ทานยา ",skip_intentPen)
                .setContentIntent(operation)
                .setVibrate(new long[] { 500, 2000, 500, 2000, 500, 2000, 500 })
                .setSound(Settings.System.DEFAULT_RINGTONE_URI)
                .setAutoCancel(false)
                .build();

        manager.notify(NOTIFICATION_ID, note);
    }

}