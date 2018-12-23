package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivitiesCustomer extends AppCompatActivity {

    ListView listview;

    String etkinlikMusteriEmail;
    int[] etkinlikNo;
    String[] title;
    String[] description;
    String[] date;

    List<String> tempTitle2 = new ArrayList<String>();
    List<String> etkinlikAdi = new ArrayList<String>();
    List<String> etkinlikAciklama = new ArrayList<String>();
    List<String> etkinlikTarih = new ArrayList<String>();

    String[] title2;
    String[] description2;
    String[] date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_customer);

        Intent i = getIntent();
        etkinlikMusteriEmail = i.getExtras().getString("realusermail2");
        title = i.getStringArrayExtra("title");
        description = i.getStringArrayExtra("description");
        etkinlikNo = i.getIntArrayExtra("etkinlikNo");
        date = i.getStringArrayExtra("etkinlikdate");

        getCustomerActivities();

        listview = findViewById(R.id.listCustomerActivities);

    }

    private void getCustomerActivities() {

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
                    if(etkinlikMusteriEmail.equals(etkinlikMusteri.get(i).getMusteriKullanici())){
                        tempTitle2.add(etkinlikMusteri.get(i).getEtkinlikno()+"");
                    }
                }

                for(int i=0;i<etkinlikNo.length;i++){
                    if(tempTitle2.contains(etkinlikNo[i]+"")){
                        etkinlikAdi.add(title[i]);
                        etkinlikAciklama.add(description[i]);
                        etkinlikTarih.add(date[i]);
                    }
                }

                //activitiesNo = tempTitle2.toArray(new String[tempTitle2.size()]);
                title2 = etkinlikAdi.toArray(new String[etkinlikAdi.size()]);
                description2 = etkinlikAciklama.toArray(new String[etkinlikAciklama.size()]);
                date2 = etkinlikTarih.toArray(new String[etkinlikTarih.size()]);

                CustomAdapter customAdapter=new CustomAdapter();
                listview.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<EtkinlikMusteriWebApi>> call, Throwable t) {

            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return title2.length;
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

            convertView = getLayoutInflater().inflate(R.layout.customer_activities_list,null);

            TextView textView = convertView.findViewById(R.id.AEtkinlikAdi);
            TextView textView2 = convertView.findViewById(R.id.AEtkinlikAciklama);
            TextView textView3 = convertView.findViewById(R.id.AEtkinlikTarih);

            textView.setText(title2[position]);
            textView2.setText(description2[position]);
            textView3.setText("Date: " + date2[position].substring(0,10) + "  Time: " + date2[position].substring(11,16));

            return convertView;
        }
    }

}
