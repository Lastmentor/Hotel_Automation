package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivitiesDetail extends AppCompatActivity {
    private String tempIsJoined;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView kalankisi;
    private CardView join,notjoin;
    int[] etkinlikNo;
    int[] limit;
    String[] title;
    String[] description;
    String RealKullanici;
    String RealActive;
    String[] date;
    int position;

    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private String[] dataSpinner = {"1","2","3","4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_detail);

        // Spinner
        spinner = findViewById(R.id.spinner1);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,dataSpinner);
        spinner.setAdapter(adapter);

        Intent i = getIntent();
        position = i.getExtras().getInt("position");
        title = i.getStringArrayExtra("title");
        description = i.getStringArrayExtra("description");
        etkinlikNo = i.getIntArrayExtra("etkinlikNo");
        date = i.getStringArrayExtra("etkinlikdate");
        limit = i.getIntArrayExtra("etkinliklimit");

        RealKullanici = i.getExtras().getString("realusermail");
        RealActive = i.getExtras().getString("activeuser");

        getMusteriEtkinlikInfo();

        txt1 = findViewById(R.id.Abaslikdetail);
        txt2 = findViewById(R.id.Aaciklamadetail);
        kalankisi = findViewById(R.id.kalankisi);
        txt3 = findViewById(R.id.Atarihdetail);

        txt1.setText(title[position]);
        txt2.setText(description[position]);

        if(limit[position]==-1){
            kalankisi.setText("∞");
        } else {
            kalankisi.setText(limit[position]+"");
        }

        //2018-02-17T00:00:00
        txt3.setText("Date: " + date[position].substring(0,10) + "  Time: " + date[position].substring(11,16));

        join = findViewById(R.id.joincard);
        notjoin = findViewById(R.id.notjoincard);

        notjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitiesDetail.this,DenemeMenu.class);
                intent.putExtra("emailAddress",RealKullanici);
                intent.putExtra("activevalue",RealActive);
                startActivity(intent);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempnumber = Integer.parseInt(spinner.getSelectedItem().toString());
                if(tempIsJoined == null){
                    if( limit[position] == 0){
                        Toast.makeText(getApplicationContext(),"No Limit For This Activity",Toast.LENGTH_SHORT).show();
                    }
                    else if( limit[position] != -1 && limit[position] - tempnumber < 0 ) {
                        Toast.makeText(getApplicationContext(),"Exceeded Limit For This Activity",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"You Have Successfully Participated",Toast.LENGTH_SHORT).show();
                        EtkinlikMusteriWebApi createEtknilikMusteri = new EtkinlikMusteriWebApi(
                                etkinlikNo[position],
                                RealKullanici,
                                "1",
                                tempnumber
                        );
                        CreateEtkinlikMusteri(createEtknilikMusteri);
                        Intent intent = new Intent(ActivitiesDetail.this,DenemeMenu.class);
                        intent.putExtra("emailAddress",RealKullanici);
                        intent.putExtra("activevalue",RealActive);
                        startActivity(intent);
                    }
                }else if (tempIsJoined.equals("1")){
                    Toast.makeText(getApplicationContext(),"Already Joined To This Activity",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void CreateEtkinlikMusteri(EtkinlikMusteriWebApi etkinlikMusteriWebApi){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<EtkinlikMusteriWebApi> call = api.createEtkinlikMusteriler(etkinlikMusteriWebApi);

        call.enqueue(new Callback<EtkinlikMusteriWebApi>() {
            @Override
            public void onResponse(Call<EtkinlikMusteriWebApi> call, Response<EtkinlikMusteriWebApi> response) {

            }

            @Override
            public void onFailure(Call<EtkinlikMusteriWebApi> call, Throwable t) {

            }
        });
    }

    private void getMusteriEtkinlikInfo(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<EtkinlikMusteriWebApi>> call = api.getEtkinlikMusteri();

        call.enqueue(new Callback<List<EtkinlikMusteriWebApi>>() {
            @Override
            public void onResponse(Call<List<EtkinlikMusteriWebApi>> call, Response<List<EtkinlikMusteriWebApi>> response) {
                List<EtkinlikMusteriWebApi> etkinlikMusteri = response.body();
                for(int i=0;i<etkinlikMusteri.size();i++){
                    String tempEtkinlikNo = etkinlikNo[position]+"";
                    String tempWebApiEtkinlikNo = etkinlikMusteri.get(i).getEtkinlikno()+"";
                    if(tempEtkinlikNo.equals(tempWebApiEtkinlikNo)){
                        if(RealKullanici.equals(etkinlikMusteri.get(i).getMusteriKullanici())){
                            tempIsJoined = etkinlikMusteri.get(i).getIsJoined();
                            break;
                        }
                    }
                }
                Toast.makeText(getApplicationContext(),"IsJoined :" + tempIsJoined,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<EtkinlikMusteriWebApi>> call, Throwable t) {

            }
        });
    }
}
