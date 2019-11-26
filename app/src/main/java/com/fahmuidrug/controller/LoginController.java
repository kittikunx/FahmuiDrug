package com.fahmuidrug.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.fahmuidrug.R;

public class LoginController extends AppCompatActivity {
    private EditText logUser , logPass;
    private Button btnlog,btnreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
