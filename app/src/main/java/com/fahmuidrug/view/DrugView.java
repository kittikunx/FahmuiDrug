package com.fahmuidrug.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fahmuidrug.R;

public class DrugView extends Activity {

    TextView txtName;
    TextView txtLevel;
    TextView txtProper;
    TextView txtHowTo;
    TextView txtAdr;
    TextView txtContra;
    ImageView vImage;
    String name;
    String level;
    String properties;
    String howTo;
    String adr;
    String contra;
    String URL;
    String image;
    ImageButton back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_drug);
        // Retrieve data from MainActivity on item click event
        Intent i = getIntent();
        // Get the results of name
        name = i.getStringExtra("name");
        level = i.getStringExtra("level");
        properties = i.getStringExtra("properties");
        howTo = i.getStringExtra("howTo");
        adr = i.getStringExtra("adr");
        contra = i.getStringExtra("contra");
        URL = i.getStringExtra("url");
        image = i.getStringExtra("image");

        // Locate the TextViews in singleitemview.xml
        txtName = (TextView) findViewById(R.id.nameDrug);
        txtLevel = (TextView) findViewById(R.id.levelDrug);
        txtHowTo = (TextView) findViewById(R.id.howToDrug);
        txtAdr= (TextView)  findViewById(R.id.adrDrug);
        txtProper = (TextView) findViewById(R.id.properDrug);
        txtContra = (TextView) findViewById(R.id.contraDrug);
        vImage = (ImageView) findViewById(R.id.imageDrug);

        // Load the results into the TextViews
        txtName.setText(name);
        txtLevel.setText(level);
        txtHowTo.setText(howTo);
        txtAdr.setText(adr);
        txtProper.setText(properties);
        txtContra.setText(contra);
        Glide.with(getApplicationContext()).load(image).into(vImage);

        back = (ImageButton) findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void detailed(View view) {
        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        startActivity(browserIntent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
