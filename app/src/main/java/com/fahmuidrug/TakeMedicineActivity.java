package com.fahmuidrug;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.fahmuidrug.adapter.DrugRemindAdapter;
import com.fahmuidrug.data.AlarmReminderContract;
import com.fahmuidrug.data.AlarmReminderDbHelper;
import com.fahmuidrug.model.DrugRemindVo;
import com.fahmuidrug.reminder.ReminderAlarmService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

import static com.fahmuidrug.ReminderActivity.drugcheck;

@SuppressLint("Registered")
public class TakeMedicineActivity extends AddReminderActivity  {

    private Uri mCurrentUri,DrugUri = AlarmReminderContract.AlarmReminderEntry.CONTENT_DRUG_URI;
    private int drugNo;
    String ID_REMIND;
    AlarmReminderDbHelper myDB;
    private FirebaseAuth mAuth ;
    ArrayList<DrugRemindVo> OO = new ArrayList<>();
    DrugRemindAdapter PP;
    int loop = 0 ;
    int NewDrug = 0;
    private static final int[] DRUG_KEY = new int[50];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_medicine);

        ImageButton backmain = (ImageButton) findViewById(R.id.imageButton);
        Button addData = (Button) findViewById(R.id.adddrug);
        ListView drugListView = (ListView) findViewById(R.id.ListViewRRR);
        //take_med = (ImageView) findViewById(R.id.take_med);


        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        mCurrentUri = intent.getData();
        final Cursor cursor = getContentResolver().query(DrugUri, null, AlarmReminderContract.AlarmReminderEntry.KEY_REMIND_ID + " = " + String.valueOf(drugcheck), null, null);
        NotificationManager delnoti = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        delnoti.cancel(ReminderAlarmService.NOTIFICATION_ID);


        PP = new DrugRemindAdapter(this,R.layout.fragment_drug_remind,OO);
        drugListView.setAdapter(PP);

        backmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        addData = (Button) findViewById(R.id.adddrug);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = (LayoutInflater.from(TakeMedicineActivity.this)).inflate(R.layout.fragment_drug_remind_add,null);

                AlertDialog.Builder alertBuild = new AlertDialog.Builder(TakeMedicineActivity.this);
                alertBuild.setView(view);
                final EditText DI1 = (EditText) view.findViewById(R.id.DataIn_name);
                final EditText DI2 = (EditText) view.findViewById(R.id.DataIn_use);
                final EditText DI3 = (EditText) view.findViewById(R.id.DataIn_no);

                alertBuild.setCancelable(true)
                        .setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ContentValues values = new ContentValues();


                                if(!DI1.getText().toString().isEmpty()&&!DI2.getText().toString().isEmpty()&&!DI3.getText().toString().isEmpty())
                                {
                                    values.put(AlarmReminderContract.AlarmReminderEntry.KEY_REMIND_ID, String.valueOf(drugcheck));
                                    values.put(AlarmReminderContract.AlarmReminderEntry.KEY_DRUG_USE_STATIC, String.valueOf(NewDrug));
                                    values.put(AlarmReminderContract.AlarmReminderEntry.KEY_DRUG_NAME, DI1.getText().toString());
                                    values.put(AlarmReminderContract.AlarmReminderEntry.KEY_DRUG_USE, DI2.getText().toString());
                                    values.put(AlarmReminderContract.AlarmReminderEntry.KEY_DRUG_NO, DI3.getText().toString());
                                }
                                getContentResolver().insert(DrugUri, values);


                                //บันทึกข้อมูลตรงนี้

                                //restartLoader();

                                Toast.makeText(getApplicationContext(),"เสร็จสิ้น" , Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(TakeMedicineActivity.this, TakeMedicineActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                Dialog dialog = alertBuild.create();
                dialog.show();
            }
        });


            if (cursor != null ) {

                cursor.moveToFirst();

                while (!cursor.isAfterLast())
                {

                    String A = cursor.getString(cursor.getColumnIndex("drug_name"));
                    String B = cursor.getString(cursor.getColumnIndex("drug_use"));
                    String TT = cursor.getString(cursor.getColumnIndex("drug_use_static"));
                    int ID = cursor.getInt(cursor.getColumnIndex("id"));
                    DRUG_KEY[loop] = ID;
                    DrugRemindVo C = new DrugRemindVo(A,B);
                    OO.add(C);
                    PP.notifyDataSetChanged();
                    loop+=1;
                    Toast.makeText(this, "++++"+TT, Toast.LENGTH_SHORT).show();
                    cursor.moveToNext();

                }


                cursor.close();
            }

            drugListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DrugRemindVo clicked = PP.getItem(position);
                    showTakeDialogConfirmation(clicked,position);
                }
            });

            drugListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    DrugRemindVo clicked = PP.getItem(position);
                    showDeleteDialogConfirmation(clicked,position);
                    return true;
                }
            });
    }

    public void delete_med(int i) {
        // Only perform the delete if this is an existing reminder.

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myReff = database.getReference("member_data").child(mAuth.getUid()).child("drug_list");

        if (DrugUri != null) {
            // Call the ContentResolver to delete the reminder at the given content URI.
            Cursor cursorDel = getContentResolver().query(DrugUri, null, AlarmReminderContract.AlarmReminderEntry.KEY_ID + " = " +DRUG_KEY[i], null, null);
            if(cursorDel!=null)
            {
                cursorDel.moveToFirst();
                String A = cursorDel.getString(cursorDel.getColumnIndex("drug_name"));
                myReff.child(A).removeValue();

                cursorDel.close();
            }

            int rowsDeleted = getContentResolver().delete(DrugUri, AlarmReminderContract.AlarmReminderEntry.KEY_ID + " = "+DRUG_KEY[i], null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_reminder_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "ท่านได้ทำการลบรายการยานี้ออกแล้ว",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void showDeleteDialogConfirmation(final DrugRemindVo data,final int Position) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("ลบรายการยา");
        alertDialogBuilder
                .setMessage("คุณต้องการลบรายการยา ชื่อ \""
                        + data.getDrugname() + "\"");

        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(DrugUri != null) {
                    delete_med(Position);
                    OO.remove(data);
                    PP.setItems(OO);
                    PP.notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showTakeDialogConfirmation(final DrugRemindVo data,final int i) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("ยืนยันการทานยา");
        alertDialogBuilder
                .setMessage("คุณต้องทานยาชื่อ \""
                        + data.getDrugname()+
                        "\"จำนวน "
                        + data.getDruguse() + " เม็ด\"");

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Cursor NewCur = getContentResolver().query(DrugUri, null, AlarmReminderContract.AlarmReminderEntry.KEY_ID + " = "+DRUG_KEY[i], null, null);
                ContentValues Update = new ContentValues();

                if (NewCur != null ) {

                    NewCur.moveToFirst();

                    String TG = NewCur.getString(NewCur.getColumnIndex("drug_use_static"));
                    String GG = NewCur.getString(NewCur.getColumnIndex("drug_use"));
                    int OT = Integer.parseInt(TG)+ Integer.parseInt(GG);

                    Update.put(AlarmReminderContract.AlarmReminderEntry.KEY_DRUG_USE_STATIC, String.valueOf(OT));

                    Toast.makeText(getApplicationContext(),"ทานยาเสร็จสิ้น  "+ String.valueOf(OT), Toast.LENGTH_SHORT).show();

                }
                getContentResolver().update(DrugUri,Update,"id"+"="+DRUG_KEY[i],null);
                dialog.dismiss();
                //Toast.makeText(getApplicationContext(),"ทานยาเสร็จสิ้น" , Toast.LENGTH_SHORT).show();

            }
        });
        alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
