package com.example.rza.socialmedialogins;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Breakfast extends Fragment {

    ListView listview;

    String[] tempTitle;
    String[] tempDescription;

    List<String> tempTitle1 = new ArrayList<String>();
    List<String> tempDescription1 = new ArrayList<String>();

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1,container,false);


        getAnnouncement();

        listview = view.findViewById(R.id.listBreakfast);

        return view;
    }

    private void getAnnouncement() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<YemeklerWebApi>> call = api.getYemekler();

        call.enqueue(new Callback<List<YemeklerWebApi>>() {
            @Override
            public void onResponse(Call<List<YemeklerWebApi>> call, Response<List<YemeklerWebApi>> response) {
                List<YemeklerWebApi> yemekler = response.body();
                tempTitle1.clear();
                tempDescription1.clear();
                for(int i=0;i<yemekler.size();i++){
                    String tempYemekTurID = yemekler.get(i).getYemekTurID()+"";
                    if (tempYemekTurID.contains("1")) {
                        tempTitle1.add(yemekler.get(i).getYemekAdi());
                        tempDescription1.add(yemekler.get(i).getAciklama());
                    }
                }

                tempTitle = tempTitle1.toArray(new String[tempTitle1.size()]);
                tempDescription = tempDescription1.toArray(new String[tempDescription1.size()]);

                CustomAdapter customAdapter = new CustomAdapter();
                listview.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<YemeklerWebApi>> call, Throwable t) {
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

            convertView = getLayoutInflater().inflate(R.layout.fragment_tab1_breakfast,null);

            TextView textView = convertView.findViewById(R.id.Bbaslik);
            TextView textView2 = convertView.findViewById(R.id.Baciklama);

            textView.setText(tempTitle[position]);
            textView2.setText(tempDescription[position]);

            return convertView;
        }
    }
}
