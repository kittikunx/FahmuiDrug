package com.fahmuidrug;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fahmuidrug.model.ProfileVo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText email,password,confirmPassword,name,lastName;
    private ProgressBar loadingProgress;
    private Button regButton;
    private FirebaseAuth mAuth;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.regEmail);
        password = (EditText) findViewById(R.id.regPassword);
        confirmPassword = (EditText) findViewById(R.id.regConfirmPass);
        name = (EditText) findViewById(R.id.regName);
        lastName = (EditText) findViewById(R.id.regLastName);
        loadingProgress = (ProgressBar) findViewById(R.id.progressBar);
        regButton = (Button) findViewById(R.id.regButton);
        mAuth = FirebaseAuth.getInstance();
        back = (ImageButton) findViewById(R.id.imageButton);


        loadingProgress.setVisibility(View.INVISIBLE);
        setRegButton();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setRegButton(){
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mEmail = email.getText().toString();
                final String mPassword = password.getText().toString();
                final String mConfirm = confirmPassword.getText().toString();
                final String mName = name.getText().toString();
                final String mLastName = lastName.getText().toString();

                if(mEmail.isEmpty() || mPassword.isEmpty() || mConfirm.isEmpty() || mName.isEmpty() || mLastName.isEmpty()){
                    showMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
                }else{
                    if(mPassword.equals(mConfirm)){
                        if(mPassword.length()>5){
                            Log.d("","Data Pass :"+mPassword);
                            Log.d("","Data Confirm :"+mConfirm);
                            CreateUserAccount(mName,mLastName,mEmail,mPassword);
                        }else{
                            showMessage("รหัสผ่านน้อยกว่า 6 ตัวอักษร");
                        }
                    }else {
                        showMessage("รหัสผ่านไม่ตรงกัน");
                    }

                }
            }
        });
    }

    private void CreateUserAccount(final String name, final String lastName, String email, String password) {
        loadingProgress.setVisibility(View.VISIBLE);
        regButton.setVisibility(View.INVISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            loadingProgress.setVisibility(View.INVISIBLE);
                            regButton.setVisibility(View.VISIBLE);
                            showMessage("สมัครสมาชิกสำเร็จ");
                            ProfileVo user = new ProfileVo(name,lastName,"","","","", "","","","","","");
                            FirebaseDatabase.getInstance().getReference("member_data")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user);
                            finish();
                        }else{
                            showMessage("สมัครสมาชิกล้มเหลว " + task.getException().getMessage());
                            loadingProgress.setVisibility(View.INVISIBLE);
                            regButton.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
