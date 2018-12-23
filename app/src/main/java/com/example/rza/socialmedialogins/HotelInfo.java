package com.example.rza.socialmedialogins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class HotelInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_info);

        EditText editText1 = findViewById(R.id.txttel);
        editText1.setEnabled(false);
        EditText editText2 = findViewById(R.id.txtmail);
        editText2.setEnabled(false);
        EditText editText3 = findViewById(R.id.txtlocal);
        editText3.setEnabled(false);

    }
}
