package com.fahmuidrug;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fahmuidrug.adapter.LabAdapter;
import com.fahmuidrug.model.LabVo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Locale;

public class LabTestActivity extends AppCompatActivity {

    ImageButton back;
    ListView mListView;
    Button addData;
    String tempType;
    FirebaseAuth mAuth ;
    LabAdapter mArrayAdapterData;
    ArrayList<LabVo> listLab = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labtest);
        mAuth = FirebaseAuth.getInstance();
        back = (ImageButton) findViewById(R.id.imageButton);
        mListView = (ListView) findViewById(R.id.listLab);

        addData = (Button) findViewById(R.id.addLab);
        setSpinner();
        setAddButton();
        onDeleteItem();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void onDeleteItem(){
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialogConfirmation(position);
                return true;
            }
        });
    }

    private void setAddButton(){
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = (LayoutInflater.from(LabTestActivity.this)).inflate(R.layout.fragment_labtest_add,null);

                AlertDialog.Builder alertBuild = new AlertDialog.Builder(LabTestActivity.this);
                alertBuild.setView(view);
                final EditText DI1 = (EditText) view.findViewById(R.id.cd4_d);
                final EditText DI2 = (EditText) view.findViewById(R.id.cd4_l);
                final EditText DI3 = (EditText) view.findViewById(R.id.viral_d);
                final EditText DI4 = (EditText) view.findViewById(R.id.viral_l);

                alertBuild.setCancelable(true)
                        .setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myReff = database.getReference("lab_data").child(mAuth.getUid());

                                if(!DI1.getText().toString().isEmpty()||!DI2.getText().toString().isEmpty())
                                {
                                    if(!DI1.getText().toString().isEmpty()&&!DI2.getText().toString().isEmpty())
                                    {
                                        LabVo data = new LabVo();
                                        data.setDate(DI1.getText().toString());
                                        data.setLab(DI2.getText().toString());
                                        myReff.child("cd4").push().setValue(data);
                                        Toast.makeText(getApplicationContext(),"เสร็จสิ้น" , Toast.LENGTH_SHORT).show();
                                        setSpinner();
//                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"กรุณาระบุผลตรวจ CD4 ให้ครบ" , Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if(!DI3.getText().toString().isEmpty()||!DI4.getText().toString().isEmpty())
                                {
                                    if(!DI3.getText().toString().isEmpty()&&!DI4.getText().toString().isEmpty())
                                    {
                                        LabVo data = new LabVo();
                                        data.setDate(DI3.getText().toString());
                                        data.setLab(DI4.getText().toString());
                                        myReff.child("viral").push().setValue(data);
                                        Toast.makeText(getApplicationContext(),"เสร็จสิ้น" , Toast.LENGTH_SHORT).show();
                                        setSpinner();
//                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"กรุณาระบุผลตรวจ Viral Load ให้ครบ" , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                Dialog dialog = alertBuild.create();
                dialog.show();
            }
        });
    }

    private List<LabVo> getLabList(String lab){
        listLab.clear();
        DatabaseReference  rootReff = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reff = rootReff.child("lab_data").child(mAuth.getUid()).child(lab);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String keySnap = ds.getKey();
                    LabVo data = new LabVo();
                    data.setDate(ds.child("date").getValue(String.class));
                    data.setLab(ds.child("lab").getValue(String.class));
                    data.setKeySnap(keySnap);
//                    Log.d("Data", keySnap);
                    listLab.add(data);
                }
                mArrayAdapterData = new LabAdapter(LabTestActivity.this,R.layout.fragment_labtest,listLab);
                mListView.setAdapter(mArrayAdapterData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        reff.addListenerForSingleValueEvent(eventListener);

        return listLab;
    }

    private void setSpinner(){
        ArrayAdapter<CharSequence> selected = ArrayAdapter.createFromResource(this,R.array.lab_selected,android.R.layout.simple_spinner_item);
        selected.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(selected);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        getLabList("cd4");
                        tempType ="cd4";
                        break;
                    case 2:
                        getLabList("viral");
                        tempType ="viral";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showDeleteDialogConfirmation(final int Position) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("ลบรายการยา");
        alertDialogBuilder
                .setMessage("คุณต้องการลบรายการนี้ใช่หรือไม่ ? ");

        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                DatabaseReference removeData = FirebaseDatabase.getInstance().getReference("lab_data").child(mAuth.getUid()).child(tempType).child(listLab.get(Position).getKeySnap());
                removeData.removeValue();

                dialog.dismiss();
                Toast.makeText(LabTestActivity.this, "ลบรายการสำเร็จ"+tempType, Toast.LENGTH_SHORT).show();
                setSpinner();
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
