package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class Announcement extends AppCompatActivity {

    private String Name,odatip,email;
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
        setContentView(R.layout.activity_announcement);

        Intent i = getIntent();
        Name = i.getExtras().getString("Name");
        odatip = i.getExtras().getString("odatip");
        email = i.getExtras().getString("emailAnnouncement");

        getAnnouncement();

        listview = findViewById(R.id.listDuyuru);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Announcement.this,DuyuruDetail.class);
                intent.putExtra("title",tempTitle);
                intent.putExtra("description",tempDescription);
                intent.putExtra("date",tempDate);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
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
                List<DuyuruWebApi> duyurular = response.body();
                for(int i=0;i<duyurular.size();i++){
                    String tempCheck = duyurular.get(i).getGorunur()+"";
                        if(tempCheck.equals("true")){
                            if("Belirtilmedi".equals(duyurular.get(i).getKriter())){
                                tempTitle2.add(duyurular.get(i).getBaslik());
                                tempDescription2.add(duyurular.get(i).getIcerik());
                                tempDate2.add(duyurular.get(i).getDuyuruTarihi());
                            }
                            else if("Oda Grubu".equals(duyurular.get(i).getKriter()))  {
                                if(odatip.equals(duyurular.get(i).getOdaGrubu())){
                                    tempTitle2.add(duyurular.get(i).getBaslik());
                                    tempDescription2.add(duyurular.get(i).getIcerik());
                                    tempDate2.add(duyurular.get(i).getDuyuruTarihi());
                                }
                                else {

                                }
                            }
                            else if("Müsteri".equals(duyurular.get(i).getKriter())){
                                if(Name.equals(duyurular.get(i).getMusteriAdi())){
                                    tempTitle2.add(duyurular.get(i).getBaslik());
                                    tempDescription2.add(duyurular.get(i).getIcerik());
                                    tempDate2.add(duyurular.get(i).getDuyuruTarihi());
                                }
                                else {

                                }
                            }
                        }
                        else{

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
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    class CustomAdapter extends BaseAdapter{

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

            convertView = getLayoutInflater().inflate(R.layout.duyuru_list_view,null);

            TextView textView = convertView.findViewById(R.id.baslik);
            TextView textView2 = convertView.findViewById(R.id.aciklama);
            TextView textView3 = convertView.findViewById(R.id.tarih);

            textView.setText(tempTitle[position]);
            textView2.setText(tempDescription[position]);
            textView3.setText("Date: " + tempDate[position].substring(0,10) + "  Time: " + tempDate[position].substring(11,16));

            return convertView;
        }
    }
}
