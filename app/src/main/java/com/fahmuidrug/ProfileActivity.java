package com.fahmuidrug;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fahmuidrug.model.ProfileVo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    ImageButton back;
    Button editData;
    TextView txtName;
    TextView txtLastName;
    TextView txtHN;
    TextView txtBD;
    TextView txtWeight;
    TextView txtHigh;
    TextView txtBloodGroup;
    TextView txtCongential;
    TextView txtAllergic;
    TextView txtPhone;
    TextView txtGeneResist;
    TextView txtGeneAllergy;
    DatabaseReference myReff;
    FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = (TextView) findViewById(R.id.textB0);
        txtLastName = (TextView) findViewById(R.id.textB1);
        txtHN = (TextView) findViewById(R.id.textB2);
        txtBD = (TextView) findViewById(R.id.textB3);
        txtWeight = (TextView) findViewById(R.id.textB4);
        txtHigh = (TextView) findViewById(R.id.textB5);
        txtBloodGroup = (TextView) findViewById(R.id.textB6);
        txtCongential = (TextView) findViewById(R.id.textB7);
        txtAllergic = (TextView) findViewById(R.id.textB8);
        txtPhone = (TextView) findViewById(R.id.textB9);
        txtGeneResist = (TextView) findViewById(R.id.textB10);
        txtGeneAllergy = (TextView) findViewById(R.id.textB11);
        editData = (Button) findViewById(R.id.editdata);
        back = (ImageButton) findViewById(R.id.imageButton);
        mAuth = FirebaseAuth.getInstance();
        myReff = FirebaseDatabase.getInstance().getReference("member_data").child(mAuth.getUid());

        getData();
        setEditData();
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

    private void getData(){
        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileVo data = new ProfileVo();
                String A0 = dataSnapshot.child("name").getValue().toString();
                String A1 = dataSnapshot.child("lastName").getValue().toString();
                String A2 = dataSnapshot.child("hn").getValue().toString();
                String A3 = dataSnapshot.child("bd").getValue().toString();
                String A4 = dataSnapshot.child("weight").getValue().toString();
                String A5 = dataSnapshot.child("high").getValue().toString();
                String A6 = dataSnapshot.child("bloodGroup").getValue().toString();
                String A7 = dataSnapshot.child("congential").getValue().toString();
                String A8 = dataSnapshot.child("allergic").getValue().toString();
                String A9 = dataSnapshot.child("phone").getValue().toString();
                String A10 = dataSnapshot.child("geneResist").getValue().toString();
                String A11 = dataSnapshot.child("geneAllergy").getValue().toString();

                if(!A0.isEmpty())
                {
                    txtName.setText(A0);
                }
                if(!A1.isEmpty())
                {
                    txtLastName.setText(A1);
                }
                if(!A2.isEmpty())
                {
                    txtHN. setText(A2);
                }
                if(!A3.isEmpty())
                {
                    txtBD.setText(A3);
                }
                if(!A4.isEmpty())
                {
                    txtWeight.setText(A4);
                }
                if(!A5.isEmpty())
                {
                    txtHigh.setText(A5);
                }
                if(!A6.isEmpty())
                {
                    txtBloodGroup.setText(A6);
                }
                if(!A7.isEmpty())
                {
                    txtCongential.setText(A7);
                }
                if(!A8.isEmpty())
                {
                    txtAllergic.setText(A8);
                }
                if(!A9.isEmpty())
                {
                    txtPhone.setText(A9);
                }
                if(!A10.isEmpty())
                {
                    txtGeneResist.setText(A10);
                }
                if(!A11.isEmpty())
                {
                    txtGeneAllergy.setText(A11);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setEditData(){

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = (LayoutInflater.from(ProfileActivity.this)).inflate(R.layout.fragment_profile,null);

                AlertDialog.Builder alertBuild = new AlertDialog.Builder(ProfileActivity.this);
                alertBuild.setView(view);

                final EditText DI0 = (EditText) view.findViewById(R.id.input_name);
                final EditText DI1 = (EditText) view.findViewById(R.id.input_lastName);
                final EditText DI2 = (EditText) view.findViewById(R.id.input_hn);
                final EditText DI3 = (EditText) view.findViewById(R.id.input_bd);
                final EditText DI4 = (EditText) view.findViewById(R.id.input_weight);
                final EditText DI5 = (EditText) view.findViewById(R.id.input_high);
                final EditText DI6 = (EditText) view.findViewById(R.id.input_bloodGroup);
                final EditText DI7 = (EditText) view.findViewById(R.id.input_congential);
                final EditText DI8 = (EditText) view.findViewById(R.id.input_allergic);
                final EditText DI9 = (EditText) view.findViewById(R.id.input_phone);
                final EditText DI10 = (EditText) view.findViewById(R.id.input_geneResist);
                final EditText DI11 = (EditText) view.findViewById(R.id.input_geneAllergy);

                alertBuild.setCancelable(true)
                        .setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(!DI0.getText().toString().isEmpty())
                                {
                                    myReff.child("name").setValue(DI0.getText().toString());
                                }
                                if(!DI1.getText().toString().isEmpty())
                                {
                                    myReff.child("lastName").setValue(DI1.getText().toString());
                                }
                                if(!DI2.getText().toString().isEmpty())
                                {
                                    myReff.child("hn").setValue(DI2.getText().toString());
                                }
                                if(!DI3.getText().toString().isEmpty())
                                {
                                    myReff.child("bd").setValue(DI3.getText().toString());
                                }
                                if(!DI4.getText().toString().isEmpty())
                                {
                                    myReff.child("weight").setValue(DI4.getText().toString());
                                }
                                if(!DI5.getText().toString().isEmpty())
                                {
                                    myReff.child("high").setValue(DI5.getText().toString());
                                }
                                if(!DI6.getText().toString().isEmpty())
                                {
                                    myReff.child("bloodGroup").setValue(DI6.getText().toString());
                                }
                                if(!DI7.getText().toString().isEmpty())
                                {
                                    myReff.child("congential").setValue(DI7.getText().toString());
                                }
                                if(!DI8.getText().toString().isEmpty())
                                {
                                    myReff.child("allergic").setValue(DI8.getText().toString());
                                }
                                if(!DI9.getText().toString().isEmpty())
                                {
                                    myReff.child("phone").setValue(DI9.getText().toString());
                                }
                                if(!DI10.getText().toString().isEmpty())
                                {
                                    myReff.child("geneResist").setValue(DI10.getText().toString());
                                }
                                if(!DI11.getText().toString().isEmpty())
                                {
                                    myReff.child("geneAllergy").setValue(DI11.getText().toString());
                                }

                                getData();
                                Toast.makeText(getApplicationContext(),"เสร็จสิ้น" , Toast.LENGTH_SHORT).show();
                            }
                        });
                Dialog dialog = alertBuild.create();
                dialog.show();
            }
        });
    }
}
