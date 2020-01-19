package com.fahmuidrug;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class KnowledgeActivity<getIntent>  extends AppCompatActivity {
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);

        back = (ImageButton) findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void knowledge1 (View view) {
        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://oryor.com/oryor2015/print-detail.php?cat=62&id=1403&fbclid=IwAR2E" +
                "QQUTtAshPT5p-Ae_fgm4v4IqFOBaGn038CHx89X8JTVziuFwIh70JYE"));
        startActivity(browserIntent);
    }

    public void knowledge2 (View view) {
        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://oryor.com/media/k2/pdfs/7984.pdf?fbclid=IwAR3IPuE51L7TIGo2gLnfpD938z" +
                "J4yeX46qJ3_4MFmXdecUdBwYUmBD_vqrs"));
        startActivity(browserIntent);
    }
    public void knowledge3 (View view) {
        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://oryor.com/oryor2015/print-detail.php?cat=73&id=1721"));
        startActivity(browserIntent);
    }

    public void knowledge4 (View view) {
        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://oryor.com/oryor2015/news-detail.php?cat=50&id=1562&fbclid=IwAR2RpHs" +
                "yKRfCW06ltgE--OlUa_gagTXBZoQUp_f1X9mIpfcumceTVKIt40c"));
        startActivity(browserIntent);
    }

    public void knowledge5 (View view) {
        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://oryor.com/oryor2015/news-detail.php?cat=50&id=1562&fbclid=IwAR2RpHs" +
                "yKRfCW06ltgE--OlUa_gagTXBZoQUp_f1X9mIpfcumceTVKIt40c"));
        startActivity(browserIntent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
