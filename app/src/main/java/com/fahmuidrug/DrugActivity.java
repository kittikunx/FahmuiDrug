package com.fahmuidrug;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.fahmuidrug.adapter.DrugAdapter;
import com.fahmuidrug.model.DrugVo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DrugActivity extends AppCompatActivity {

    ListView listView;
    ImageButton back;
    EditText keyword;
    DrugAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);

        listView = (ListView) findViewById(R.id.drugList);
        keyword = (EditText) findViewById(R.id.searchInput);
        back = (ImageButton) findViewById(R.id.imageButton);

        getList();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setListView(List<DrugVo> drug){
        adapter = new DrugAdapter(this, drug);
        listView.setAdapter(adapter);
        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = keyword.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onTextChanged(CharSequence arg0, int arg1,
                                      int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }

    private List<DrugVo> getList(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference moviesRef = rootRef.child("drug_data");
        final List<DrugVo> listDrug = new ArrayList<DrugVo>();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    DrugVo data = new DrugVo();
                    data.setName(ds.child("name").getValue(String.class));
                    data.setUrl(ds.child("url").getValue(String.class));
                    data.setImage(ds.child("image").getValue(String.class));
//                    data.setImage(R.drawable.noimage);
                    data.setLevel(ds.child("level").getValue(String.class));
                    data.setHowTo(ds.child("howTo").getValue(String.class));
                    data.setProperties(ds.child("properties").getValue(String.class));
                    data.setAdr(ds.child("adr").getValue(String.class));
                    data.setContra(ds.child("contra").getValue(String.class));
                    listDrug.add(data);
                    Log.d("IMAGE URL", data.getImage());
                }
                setListView(listDrug);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        moviesRef.addListenerForSingleValueEvent(eventListener);

        return listDrug;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
