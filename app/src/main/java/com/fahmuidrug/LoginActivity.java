package com.fahmuidrug;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

@SuppressLint("Registered")
public class LoginActivity extends AppCompatActivity {

    private String email,password;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private EditText emailInput,passwordInput;
    private CheckBox rememberChk;
    private ProgressBar logProgess;
    private Button loginBtn,regBtn;
    private FirebaseAuth mAuth ;
    private Boolean saveLogin;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get value by ID
        loginBtn = (Button) findViewById(R.id.loginBtn);
        regBtn = (Button) findViewById(R.id.regBtn);
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        rememberChk = (CheckBox) findViewById(R.id.rememberChk);
        logProgess =  (ProgressBar) findViewById(R.id.logProgess);

        //set default
        mAuth = FirebaseAuth.getInstance();
        logProgess.setVisibility(View.INVISIBLE);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        if (saveLogin) {
            emailInput.setText(loginPreferences.getString("email", ""));
            passwordInput.setText(loginPreferences.getString("password", ""));
            rememberChk.setChecked(true);
            email = emailInput.getText().toString();
            password = passwordInput.getText().toString();
            signIn(email,password);
            return;
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(emailInput.getWindowToken(), 0);

                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();

                if (rememberChk.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("email", email);
                    loginPrefsEditor.putString("password", password);
                    loginPrefsEditor.apply();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

                if(!email.isEmpty()&&!password.isEmpty()){
                    signIn(email,password);
                }else{
                    Toast.makeText(getApplicationContext(),"กรุณากรอกข้อมูลให้ครบ",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void signIn(String email, String pass) {
        logProgess.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.INVISIBLE);
        regBtn.setVisibility(View.INVISIBLE);
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    logProgess.setVisibility(View.INVISIBLE);
                    loginBtn.setVisibility(View.VISIBLE);
                    regBtn.setVisibility(View.VISIBLE);
                    goActivity();
                }
                else{
                    showMessage(Objects.requireNonNull(task.getException()).getMessage());
                    logProgess.setVisibility(View.INVISIBLE);
                    loginBtn.setVisibility(View.VISIBLE);
                    regBtn.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void goActivity() {
        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
        LoginActivity.this.finish();
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

}
