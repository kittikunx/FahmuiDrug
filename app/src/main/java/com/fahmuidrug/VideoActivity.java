package com.fahmuidrug;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    private android.webkit.WebView WebView;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        back = (ImageButton) findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        WebView  = (android.webkit.WebView) findViewById(R.id.webview);
        WebView.getSettings().setJavaScriptEnabled(true);
        String myvideokey="<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/5Vt3UlIR-b8\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
        WebView.loadData(myvideokey,"text/html","utf-8");
        WebView.setWebChromeClient(new WebChromeClient(){

        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }



}

