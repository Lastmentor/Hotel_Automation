package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInfo extends AppCompatActivity {

    TextView nm,rm,cki,cko,bnum;
    TextView txt1,txt2,txt3,txt4,txt5;
    private String Name,OdaNo,checkin,checkout,blok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Underlining Text
        txt1 = findViewById(R.id.txt1);
        txt1.setPaintFlags(txt1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txt2 = findViewById(R.id.txt2);
        txt2.setPaintFlags(txt2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txt3 = findViewById(R.id.txt3);
        txt3.setPaintFlags(txt3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txt4 = findViewById(R.id.txt4);
        txt4.setPaintFlags(txt4.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txt5 = findViewById(R.id.txt5);
        txt5.setPaintFlags(txt5.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Intent i = getIntent();
        Name = i.getExtras().getString("UserName");
        OdaNo = i.getExtras().getString("UserOdaNo");
        checkin = i.getExtras().getString("Checkin");
        checkout = i.getExtras().getString("Checkout");
        blok = i.getExtras().getString("UserBlokNo");

        nm = findViewById(R.id.username);
        rm = findViewById(R.id.userroom);
        cki = findViewById(R.id.usercheckin);
        cko = findViewById(R.id.usercheckout);
        bnum = findViewById(R.id.blocknumber);

        nm.setText(Name);
        rm.setText(OdaNo);
        cki.setText(checkin);
        cko.setText(checkout);
        bnum.setText(blok);
    }
}