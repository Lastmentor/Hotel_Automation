package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DuyuruDetail extends AppCompatActivity {

    private TextView txt1;
    private TextView txt2;
    private TextView txt3;

    String[] title;
    String[] description;
    String[] date;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyuru_detail);

        Intent i = getIntent();
        position = i.getExtras().getInt("position");
        title = i.getStringArrayExtra("title");
        date = i.getStringArrayExtra("date");
        description = i.getStringArrayExtra("description");

        txt1 = findViewById(R.id.baslikdetail);
        txt2 = findViewById(R.id.aciklamadetail);
        txt3 = findViewById(R.id.tarihdetail);

        txt1.setText(title[position]);
        txt2.setText(description[position]);
        txt3.setText("Date: " + date[position].substring(0,10) + "  Time: " + date[position].substring(11,16));
    }
}
