package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonnelKapiUnlock extends AppCompatActivity {

    private String Value;
    private String KatNo,BlokNo,Adsoyad,mail;
    private String[] arrayKapiNo;
    private String[] arrayKapiKod;
    private List<String> kapilarNo = new ArrayList<String>();
    private List<String> kapilarKod = new ArrayList<String>();

    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    private CardView CrdOpen,CrdReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_kapi_unlock);

        spinner = findViewById(R.id.spnNumbers);
        CrdOpen = findViewById(R.id.open);
        CrdReset = findViewById(R.id.reset);

        Intent i = getIntent();
        KatNo = i.getExtras().getString("PersonelKatNo");
        BlokNo = i.getExtras().getString("PersonelBlok");
        Adsoyad = i.getExtras().getString("PersonelAdSoyAd");
        mail = i.getExtras().getString("PersoneEmail");


        Toast.makeText(getApplicationContext(),"KatNo : " + KatNo + " BlokNo : " + BlokNo,Toast.LENGTH_SHORT).show();

        getKapiInfo();

        CrdOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateStringValue();
            }
        });

        CrdReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateCode();
            }
        });
    }

    private void getKapiInfo(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<KapiWebApi>> call = api.getKapiInfo();

        call.enqueue(new Callback<List<KapiWebApi>>() {
            @Override
            public void onResponse(Call<List<KapiWebApi>> call, Response<List<KapiWebApi>> response) {
                List<KapiWebApi> kapiinfo = response.body();
                for(int i=0;i<kapiinfo.size();i++){
                    if(KatNo.equals(kapiinfo.get(i).getKat()) && BlokNo.equals(kapiinfo.get(i).getBlok())){
                        kapilarNo.add(kapiinfo.get(i).getKapiNo());
                        kapilarKod.add(kapiinfo.get(i).getKapiKod());
                    }
                }
                arrayKapiNo = kapilarNo.toArray(new String[kapilarNo.size()]);
                arrayKapiKod = kapilarKod.toArray(new String[kapilarKod.size()]);
                CreateSpinner();
            }


            @Override
            public void onFailure(Call<List<KapiWebApi>> call, Throwable t) {

            }
        });
    }

    private void CreateSpinner(){
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,arrayKapiNo);
        spinner.setAdapter(adapter);
    }

    private void GenerateStringValue(){
        OdaHareketWebApi logOlustur = new OdaHareketWebApi(
                spinner.getSelectedItem().toString(),
                DateFormat.getDateTimeInstance().format(new Date()),
                DateFormat.getDateTimeInstance().format(new Date()),
                Adsoyad,
                "Temizlik",
                true,
                mail,
                "Standart",
                BlokNo,
                KatNo
        );
        CreateLog(logOlustur);
        Value = "KAPI " + spinner.getSelectedItem().toString() + " PERSONEL " + arrayKapiKod[spinner.getSelectedItemPosition()] + " " + Adsoyad;
        Intent intent = new Intent(PersonnelKapiUnlock.this,PersonnelDoor2.class);
        intent.putExtra("PersonnelValue",Value);
        startActivity(intent);
    }

    private void GenerateCode(){
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        char tempChar;
        for (int i = 0; i < 5; i++){
            tempChar = (char) (generator.nextInt(96) + 31);
            randomStringBuilder.append(tempChar);
        }
        String YeniKod = randomStringBuilder.toString();
        KapiWebApi denemeKapi = new KapiWebApi(
                spinner.getSelectedItem().toString(),
                YeniKod,
                BlokNo,
                KatNo
        );
        Value = "SIFIRLA " + spinner.getSelectedItem().toString() + " PERSONEL " + YeniKod + " " + Adsoyad;
        CreateCode(denemeKapi);
        Intent intent = new Intent(PersonnelKapiUnlock.this,PersonelKapiReset.class);
        intent.putExtra("PersonnelNewValue",Value);
        startActivity(intent);
    }

    private void CreateCode(KapiWebApi denemeKapi){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<KapiWebApi> call = api.createKapiKod(denemeKapi);

        call.enqueue(new Callback<KapiWebApi>() {
            @Override
            public void onResponse(Call<KapiWebApi> call, Response<KapiWebApi> response) {
                kapilarNo.clear();kapilarKod.clear();
                getKapiInfo();
            }

            @Override
            public void onFailure(Call<KapiWebApi> call, Throwable t) {

            }
        });
    }

    private void CreateLog (OdaHareketWebApi denemeOdaHareket){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<OdaHareketWebApi> call = api.createOdaHareket(denemeOdaHareket);

        call.enqueue(new Callback<OdaHareketWebApi>() {
            @Override
            public void onResponse(Call<OdaHareketWebApi> call, Response<OdaHareketWebApi> response) {

            }

            @Override
            public void onFailure(Call<OdaHareketWebApi> call, Throwable t) {

            }
        });
    }
}
