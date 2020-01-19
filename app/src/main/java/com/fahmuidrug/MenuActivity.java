package com.fahmuidrug;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    GridLayout grid;
    ImageButton signOut;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        grid = (GridLayout) findViewById(R.id.grid);
        signOut = (ImageButton) findViewById(R.id.signOut);
        SingleEvent(grid);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                loginPrefsEditor = loginPreferences.edit();
                loginPrefsEditor.putBoolean("saveLogin", false);
                loginPrefsEditor.putString("email", "");
                loginPrefsEditor.putString("password", "");
                loginPrefsEditor.apply();
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "กดปุ่มอีกครั้งเพื่อออกจากระบบ", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                FirebaseAuth.getInstance().signOut();
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void SingleEvent(GridLayout grid) {

        for (int i = 0; i < grid.getChildCount(); i++) {
            CardView cardView = (CardView) grid.getChildAt(i);

            if(i == 0)
            {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

                    }
                });
            }

            if(i == 1)
            {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),DrugActivity.class));
                    }
                });
            }

            if(i == 2)
            {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("อยู่ในช่วงพัฒนา");
                        startActivity(new Intent(getApplicationContext(),ReminderActivity.class));
                    }
                });
            }

            if(i == 3)
            {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("อยู่ในช่วงพัฒนา");
//                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });
            }

            if(i == 4)
            {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),LabTestActivity.class));
                    }
                });
            }

            if(i == 5)
            {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), HospitalActivity.class));
                    }
                });
            }

            if(i == 6)
            {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), KnowledgeActivity.class));
                    }
                });
            }

            if(i == 7)
            {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
                    }
                });
            }

            if(i == 8)
            {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), DeveloperActivity.class));
                    }
                });
            }

            if(i == 9)
            {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), VideoActivity.class));
                    }
                });
            }

        }
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
}
