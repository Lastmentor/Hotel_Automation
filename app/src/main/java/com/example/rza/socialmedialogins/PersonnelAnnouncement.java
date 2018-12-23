package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonnelAnnouncement extends AppCompatActivity {

    private String Name,blok,kat,dpt;
    ListView listview;

    String[] tempTitle;
    String[] tempDescription;
    String[] tempDate;

    // Kriter For Belirtilmedi
    List<String> tempTitle2 = new ArrayList<String>();
    List<String> tempDescription2 = new ArrayList<String>();
    List<String> tempDate2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_announcement);

        Intent i = getIntent();
        Name = i.getExtras().getString("PersonelAdSoyAdDuyuru");
        blok = i.getExtras().getString("PersonelBlok1");
        kat = i.getExtras().getString("PersonelKaTno1");
        dpt = i.getExtras().getString("PersonelDpt");

        getAnnouncement();

        listview = findViewById(R.id.listDuyuruPersonel);
    }

    private void getAnnouncement() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<DuyuruWebApi>> call = api.getDuyurular();

        call.enqueue(new Callback<List<DuyuruWebApi>>() {
            @Override
            public void onResponse(Call<List<DuyuruWebApi>> call, Response<List<DuyuruWebApi>> response) {
                List<DuyuruWebApi> duyurularpersonel = response.body();
                for(int i=0;i<duyurularpersonel.size();i++){
                    String tempCheck = duyurularpersonel.get(i).getGorunur()+"";
                    if(tempCheck.equals("true")){
                        if(dpt.equals("Temizlikçi")){
                            if("Personel".equals(duyurularpersonel.get(i).getKriter())){
                                tempTitle2.add(duyurularpersonel.get(i).getBaslik());
                                tempDescription2.add(duyurularpersonel.get(i).getIcerik());
                                tempDate2.add(duyurularpersonel.get(i).getDuyuruTarihi());
                            }
                        } else {
                            if(!duyurularpersonel.get(i).getDuyuruGrubu().equals("Temizlik") && "Personel".equals(duyurularpersonel.get(i).getKriter())){
                                tempTitle2.add(duyurularpersonel.get(i).getBaslik());
                                tempDescription2.add(duyurularpersonel.get(i).getIcerik());
                                tempDate2.add(duyurularpersonel.get(i).getDuyuruTarihi());
                            }
                        }
                    }else{

                    }
                }
                tempTitle = tempTitle2.toArray(new String[tempTitle2.size()]);
                tempDescription = tempDescription2.toArray(new String[tempDescription2.size()]);
                tempDate = tempDate2.toArray(new String[tempDate2.size()]);

                CustomAdapter customAdapter = new CustomAdapter();
                listview.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<DuyuruWebApi>> call, Throwable t) {

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

            convertView = getLayoutInflater().inflate(R.layout.personnel_announcements,null);

            TextView textView = convertView.findViewById(R.id.personelduyurubaslik);
            TextView textView2 = convertView.findViewById(R.id.personelduyuruaciklama);
            TextView textView3 = convertView.findViewById(R.id.personelduyurutarih);

            textView.setText(tempTitle[position]);
            textView2.setText(tempDescription[position]);
            textView3.setText("Date: " + tempDate[position].substring(0,10) + "  Time: " + tempDate[position].substring(11,16));

            return convertView;
        }
    }
}
