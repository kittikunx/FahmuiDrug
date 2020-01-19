package com.fahmuidrug;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionActivity extends AppCompatActivity {

    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        back = (ImageButton) findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void question(View view) {
        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSfyMT4-yYzsGSYYxJQ9XMFhdDKu2EPDB0IUlldJGxMSQyI-UQ/viewform?fbclid=IwAR02jMf5V02t3SSfBdc0poZD3wlQAh2wdzzstMtdktczVnQDFNiMpIO50OI"));
        startActivity(browserIntent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
