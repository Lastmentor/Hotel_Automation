package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonnelMeating extends AppCompatActivity {

    private String departman;
    ListView listview;

    String[] tempTitle;
    String[] tempDescription;
    String[] tempDate;
    String[] tempPlace;

    // For Kriter
    List<String> tempTitle2 = new ArrayList<String>();
    List<String> tempDescription2 = new ArrayList<String>();
    List<String> tempDate2 = new ArrayList<String>();
    List<String> tempPlace2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_meating);

        Intent i = getIntent();
        departman = i.getExtras().getString("Departman");

        getToplanti();

        listview = findViewById(R.id.listMeatings);
    }

    private void getToplanti() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<PersonnelToplantiWebApi>> call = api.getPersonnelToplanti();

        call.enqueue(new Callback<List<PersonnelToplantiWebApi>>() {
            @Override
            public void onResponse(Call<List<PersonnelToplantiWebApi>> call, Response<List<PersonnelToplantiWebApi>> response) {
                List<PersonnelToplantiWebApi> toplantilar = response.body();
                for(int i=0;i<toplantilar.size();i++){
                    if("Tüm Personeller".equals(toplantilar.get(i).getToplantiPersonelKategori())){
                        tempTitle2.add(toplantilar.get(i).getToplantiKonusu());
                        tempDescription2.add(toplantilar.get(i).getAciklama());
                        tempDate2.add(toplantilar.get(i).getToplantiTarihi());
                        tempPlace2.add(toplantilar.get(i).getToplantiSalonu());
                    }
                    else if(departman.equals(toplantilar.get(i).getToplantiPersonelKategori())){
                        tempTitle2.add(toplantilar.get(i).getToplantiKonusu());
                        tempDescription2.add(toplantilar.get(i).getAciklama());
                        tempDate2.add(toplantilar.get(i).getToplantiTarihi());
                        tempPlace2.add(toplantilar.get(i).getToplantiSalonu());
                    }
                }

                tempTitle = tempTitle2.toArray(new String[tempTitle2.size()]);
                tempDescription = tempDescription2.toArray(new String[tempDescription2.size()]);
                tempDate = tempDate2.toArray(new String[tempDate2.size()]);
                tempPlace = tempPlace2.toArray(new String[tempPlace2.size()]);

                CustomAdapter customAdapter = new CustomAdapter();
                listview.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<PersonnelToplantiWebApi>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return tempTitle.length;
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

            convertView = getLayoutInflater().inflate(R.layout.toplanti_list_view,null);

            TextView textView = convertView.findViewById(R.id.Tbaslik);
            TextView textView2 = convertView.findViewById(R.id.Taciklama);
            TextView textView3 = convertView.findViewById(R.id.Ttarih);
            TextView textView4 = convertView.findViewById(R.id.Tmekan);

            textView.setText(tempTitle[position]);
            textView2.setText(tempDescription[position]);
            textView3.setText("Date: " + tempDate[position].substring(0,10) + "  Time: " + tempDate[position].substring(11,16));
            textView4.setText("Place : " + tempPlace[position]);

            return convertView;
        }
    }
}
