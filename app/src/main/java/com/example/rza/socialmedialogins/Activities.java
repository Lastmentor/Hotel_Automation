package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activities extends AppCompatActivity {

    ListView listview;

    String UserKullanici;
    String ActiveUser;
    int counter = 0;
    int realcounter = 0;
    int[] etkinlikNo;
    int[] limit;
    String[] title;
    String[] description;
    String[] date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        Intent i = getIntent();
        UserKullanici = i.getExtras().getString("usermail");
        ActiveUser = i.getExtras().getString("ActiveForDeneme");

        getActivities();

        listview = findViewById(R.id.listActivities);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activities.this,ActivitiesDetail.class);
                intent.putExtra("title",title);
                intent.putExtra("description",description);
                intent.putExtra("position",position);
                intent.putExtra("etkinlikNo",etkinlikNo);
                intent.putExtra("etkinliklimit",limit);
                intent.putExtra("realusermail",UserKullanici);
                intent.putExtra("activeuser",ActiveUser);
                intent.putExtra("etkinlikdate",date);
                startActivity(intent);
            }
        });

        // Image Button Click
        ImageView img = findViewById(R.id.etkinliklist);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activities.this,ActivitiesCustomer.class);
                intent.putExtra("realusermail2",UserKullanici);
                intent.putExtra("title",title);
                intent.putExtra("etkinlikNo",etkinlikNo);
                intent.putExtra("etkinlikdate",date);
                intent.putExtra("description",description);
                startActivity(intent);
            }
        });
    }

    private void getActivities() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<EtkinlikWebApi>> call = api.getEtkinlik();

        call.enqueue(new Callback<List<EtkinlikWebApi>>() {
            @Override
            public void onResponse(Call<List<EtkinlikWebApi>> call, Response<List<EtkinlikWebApi>> response) {
                List<EtkinlikWebApi> etkinlikler = response.body();
                for(int i=0;i<etkinlikler.size();i++){
                    String tempCheck = etkinlikler.get(i).getGorunur()+"";
                        if(tempCheck.equals("true")){
                            counter++;
                        }
                }

                limit = new int[counter];
                etkinlikNo = new int[counter];
                title = new String[counter];
                description = new String[counter];
                date = new String[counter];

                for(int i=0;i<etkinlikler.size();i++){
                    String tempCheck2 = etkinlikler.get(i).getGorunur()+"";
                        if(tempCheck2.equals("true")){
                            limit[realcounter] = etkinlikler.get(i).getEtkinlikLimit();
                            etkinlikNo[realcounter] = etkinlikler.get(i).getEtkinlikno();
                            title[realcounter] = etkinlikler.get(i).getEtkinlikAdi();
                            description[realcounter] = etkinlikler.get(i).getEtkinlikAciklama();
                            date[realcounter] = etkinlikler.get(i).getEtkinlikTarihi();
                            realcounter++;
                            if (realcounter == counter){
                                break;
                            }
                        }else{

                        }
                }

                /*for(int k=0;k<etkinlikler.size();k++){
                    Log.d("!!!!!!! ETKİNLİK NO  : " , etkinlikNo[k]+"");
                    Log.d("!!!!!!! ETKİNLİK ADI : " , etkinlikler.get(k).getEtkinlikAdi());
                    Log.d("!!!!!!! ETKİNLİK AÇK : " , etkinlikler.get(k).getEtkinlikAciklama());
                    Log.d("!!!!!!! ETKİNLİK TRH : " , etkinlikler.get(k).getEtkinlikTarihi());
                    Log.d("!!!!!!! ETKİNLİK LMT : " , limit[k]+"");
                    Log.d("!!!!!!! ETKİNLİK GRN : " , etkinlikler.get(k).getGorunur()+"");
                }*/

                CustomAdapter customAdapter=new CustomAdapter();
                listview.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<EtkinlikWebApi>> call, Throwable t) {

            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.activities_list_view,null);

            TextView textView = convertView.findViewById(R.id.Abaslik);
            TextView textView2 = convertView.findViewById(R.id.Aaciklama);
            TextView textView3 = convertView.findViewById(R.id.Atarih);

            textView.setText(title[position]);
            textView2.setText(description[position]);
            textView3.setText("Date: " + date[position].substring(0,10) + "  Time: " + date[position].substring(11,16));

            return convertView;
        }
    }
}
