package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Room extends AppCompatActivity {

    private String Odano,katNo,BlokNo,currentDateTime;
    private CardView btnTemizlik;
    private TextView temizlikDate,txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // Underlining Text
        txt2 = findViewById(R.id.Rtxt2);
        txt2.setPaintFlags(txt2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Intent i = getIntent();
        Odano = i.getExtras().getString("OdaNo");
        katNo = i.getExtras().getString("KatNo");
        BlokNo = i.getExtras().getString("BlokNo");

        btnTemizlik = findViewById(R.id.sendRequest);
        temizlikDate = findViewById(R.id.temizlikDate);

        GetTemizlikDateForRoomNumber();

        btnTemizlik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
                OdaTemizlikRequestWebApi temizlikRequestWebApi = new OdaTemizlikRequestWebApi(
                        currentDateTime,
                        Odano,
                        BlokNo,
                        katNo
                );
                CreateRequest(temizlikRequestWebApi);
                Toast.makeText(getApplicationContext(),"Room Clean Request Taken",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetTemizlikDateForRoomNumber(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<OdaTemizlikWebApi>> call = api.getOdaTemizlik();

        call.enqueue(new Callback<List<OdaTemizlikWebApi>>() {
            @Override
            public void onResponse(Call<List<OdaTemizlikWebApi>> call, Response<List<OdaTemizlikWebApi>> response) {
                List<OdaTemizlikWebApi> tempTemizlikDate = response.body();
                for(int i=0;i<tempTemizlikDate.size();i++){
                    if(Odano.equals(tempTemizlikDate.get(i).getOdaNo()) && katNo.equals(tempTemizlikDate.get(i).getKatAdi()) && BlokNo.equals(tempTemizlikDate.get(i).getBlok())){
                        temizlikDate.setText(tempTemizlikDate.get(i).getTemizlikTarihi().substring(0,10));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OdaTemizlikWebApi>> call, Throwable t) {

            }
        });
    }

    private void CreateRequest(OdaTemizlikRequestWebApi temizlikRequestWebApi){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<OdaTemizlikRequestWebApi> call = api.createOdaTemizlikRequest(temizlikRequestWebApi);

        call.enqueue(new Callback<OdaTemizlikRequestWebApi>() {
            @Override
            public void onResponse(Call<OdaTemizlikRequestWebApi> call, Response<OdaTemizlikRequestWebApi> response) {
                //Toast.makeText(getApplicationContext(),"Başarılı",Toast.LENGTH_SHORT).show();
                DuyuruWebApi duyuruDeneme = new DuyuruWebApi(
                       "Temizlik - " + currentDateTime,
                       "Temizlik",
                        BlokNo + " " + katNo + ". Kat " + Odano + " Nolu Odadan Temizlik Talebi Geldi",
                       "Personel",
                        "Belirtilmedi",
                        "Belirtilmedi",
                        "Temizlikçi",
                        currentDateTime,
                        true
                );
                SendPersonelRequest(duyuruDeneme);
            }

            @Override
            public void onFailure(Call<OdaTemizlikRequestWebApi> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SendPersonelRequest(DuyuruWebApi denemeDuyuru){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<DuyuruWebApi> call = api.createDuyuru(denemeDuyuru);

        call.enqueue(new Callback<DuyuruWebApi>() {
            @Override
            public void onResponse(Call<DuyuruWebApi> call, Response<DuyuruWebApi> response) {

            }

            @Override
            public void onFailure(Call<DuyuruWebApi> call, Throwable t) {

            }
        });
    }
}
