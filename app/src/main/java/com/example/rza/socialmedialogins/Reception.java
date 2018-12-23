package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Reception extends AppCompatActivity {
    private CardView call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);
        call = findViewById(R.id.Callbtn);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callintent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "05388275522"));
                startActivity(callintent);
            }
        });
    }
}
