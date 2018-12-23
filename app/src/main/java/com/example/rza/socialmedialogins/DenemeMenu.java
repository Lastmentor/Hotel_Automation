package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DenemeMenu extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private CardView cardViewExit;
    private String ActEmail,ActActive;
    private String tempEmail;
    private String tempSifreliEmail;
    private String tempRealEmail;
    private String TempSifreliActive;
    private String[] tmpActive;
    private String realActive = null;
    private int tempCounter=0,Counter=0;
    private String Name,odatip,OdaNo,checkin,checkout,blok,katno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deneme_menu);

        mAuth = FirebaseAuth.getInstance();

        Intent i = getIntent();
        TempSifreliActive = i.getExtras().getString("tempActive");
        tempSifreliEmail = i.getExtras().getString("sifreliEmail");

        ActEmail = i.getExtras().getString("emailAddress");
        ActActive = i.getExtras().getString("activevalue");

        cardViewExit = findViewById(R.id.cardView8);
        cardViewExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(DenemeMenu.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        if(ActEmail!=null){
            Toast.makeText(DenemeMenu.this, "Email : " + ActEmail, Toast.LENGTH_SHORT).show();
            Toast.makeText(DenemeMenu.this, "Active : " + ActActive, Toast.LENGTH_SHORT).show();
            realActive="1";
            tempRealEmail= ActEmail;
        }else {
            if(TempSifreliActive==null){
                getMusteriUserActiveNumber();
                tempRealEmail = tempEmail;
            }else{
                Toast.makeText(DenemeMenu.this, "Email : " + tempSifreliEmail, Toast.LENGTH_SHORT).show();
                realActive=TempSifreliActive;
                tempRealEmail = tempSifreliEmail;
            }
        }

        getUserInfo();
    }

    public void ClickCard (View view){
        if(realActive == null){
            Toast.makeText(DenemeMenu.this, "Account Not Active", Toast.LENGTH_SHORT).show();
        }
        else if(realActive.equals("0")){
            Toast.makeText(DenemeMenu.this, "Account Not Active", Toast.LENGTH_SHORT).show();
        }
        else {
            switch (view.getId()) {
                case R.id.cardView1:
                    Intent intent = new Intent(DenemeMenu.this, UserInfo.class);
                    intent.putExtra("Checkin",checkin);
                    intent.putExtra("Checkout",checkout);
                    intent.putExtra("UserName",Name);
                    intent.putExtra("UserOdaNo",OdaNo);
                    intent.putExtra("UserBlokNo",blok);
                    startActivity(intent);
                    break;
                case R.id.cardView2:
                    startActivity(new Intent(DenemeMenu.this, HotelInfo.class));
                    break;
                case R.id.cardView3:
                    startActivity(new Intent(DenemeMenu.this, Meal.class));
                    break;
                case R.id.cardView4:
                    Intent intentAnnouncement = new Intent(DenemeMenu.this, Announcement.class);
                    intentAnnouncement.putExtra("emailAnnouncement",tempRealEmail);
                    intentAnnouncement.putExtra("Name",Name);
                    intentAnnouncement.putExtra("odatip",odatip);
                    startActivity(intentAnnouncement);
                    break;
                case R.id.cardView5:
                    Intent intentActivities = new Intent(DenemeMenu.this,Activities.class);
                    intentActivities.putExtra("usermail",tempRealEmail);
                    intentActivities.putExtra("ActiveForDeneme",realActive);
                    startActivity(intentActivities);
                    break;
                case R.id.cardView6:
                    Intent intentRoom = new Intent(DenemeMenu.this,Room.class);
                    intentRoom.putExtra("OdaNo",OdaNo);
                    intentRoom.putExtra("KatNo",katno);
                    intentRoom.putExtra("BlokNo",blok);
                    startActivity(intentRoom);
                    break;
                case R.id.cardView7:
                    startActivity(new Intent(DenemeMenu.this, Reception.class));
                    break;
                case R.id.cardView9:
                    startActivity(new Intent(DenemeMenu.this, Pool.class));
                    break;
                case R.id.cardView10:
                    Intent intentKapi = new Intent(DenemeMenu.this, KapiUnlock.class);
                    intentKapi.putExtra("OdaNoKapi",OdaNo);
                    intentKapi.putExtra("NameKapi",Name);
                    startActivity(intentKapi);
                    break;
            }
        }
    }

    public void getMusteriUserActiveNumber(){

        Intent i = getIntent();
        tempEmail = i.getExtras().getString("email");
        Toast.makeText(DenemeMenu.this, "Email : " + tempEmail, Toast.LENGTH_SHORT).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<DenemeMusteriler>> call = api.getMusteriler();

        call.enqueue(new Callback<List<DenemeMusteriler>>() {
            @Override
            public void onResponse(Call<List<DenemeMusteriler>> call, Response<List<DenemeMusteriler>> response) {
                List<DenemeMusteriler> tempUserInfo = response.body();
                tmpActive = new String[tempUserInfo.size()];
                for(int i=0;i<tempUserInfo.size();i++){
                    tmpActive[i] = tempUserInfo.get(i).getMusteriMail();
                }
                for(int i=0;i<tmpActive.length;i++){
                    if(tempEmail.equals(tmpActive[i])){
                        realActive = tempUserInfo.get(i).getMusteriActive();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<DenemeMusteriler>> call, Throwable t) {

            }
        });
    }

    private void getUserInfo() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<OdaHareketWebApi>> call = api.getOdaHareketInfo();

        call.enqueue(new Callback<List<OdaHareketWebApi>>() {
            @Override
            public void onResponse(Call<List<OdaHareketWebApi>> call, Response<List<OdaHareketWebApi>> response) {
                List<OdaHareketWebApi> tempOdaHareket = response.body();
                for(int i=0;i<tempOdaHareket.size();i++){
                    if(tempRealEmail.equals(tempOdaHareket.get(i).getMail())){
                        tempCounter++;
                    }
                }
                for(int i=0;i<tempOdaHareket.size();i++){
                    if(tempRealEmail.equals(tempOdaHareket.get(i).getMail())){
                        Counter++;
                        if(Counter==tempCounter){
                            Name = tempOdaHareket.get(i).getMusteriPersonel();
                            odatip = tempOdaHareket.get(i).getOdaTip();
                            OdaNo = tempOdaHareket.get(i).getOdaNo();
                            checkin = tempOdaHareket.get(i).getIslemTarihi1().substring(0,10);
                            checkout = tempOdaHareket.get(i).getIslemTarihii2().substring(0,10);
                            blok = tempOdaHareket.get(i).getBlok();
                            katno = tempOdaHareket.get(i).getKatno();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OdaHareketWebApi>> call, Throwable t) {

            }
        });
    }
}
