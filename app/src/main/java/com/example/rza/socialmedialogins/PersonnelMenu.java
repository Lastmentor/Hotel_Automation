package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonnelMenu extends AppCompatActivity {

    private String textEmail,txtActive,txtDepartman,txtAdSoyad;
    private CardView cardViewLogout,cardViewDoor;
    private String tempBlok,tempKatNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_menu);

        Intent i = getIntent();
        textEmail = i.getExtras().getString("PersonnelEmail");
        txtActive = i.getExtras().getString("PersonnelActive");
        txtDepartman = i.getExtras().getString("PersonnelDepartman");
        txtAdSoyad = i.getExtras().getString("PersonnelAdSoyad");

        Toast.makeText(getApplicationContext(),"Email : " + textEmail, Toast.LENGTH_SHORT).show();

        if(txtDepartman.equals("Temizlikçi")){
            getKatSorumluPersonel();
        }

        cardViewLogout = findViewById(R.id.cardLogout);
        cardViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonnelMenu.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        cardViewDoor = findViewById(R.id.cardDoor);
        cardViewDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtDepartman.equals("Temizlikçi") && txtActive.equals("true")){
                    Intent intentDoor = new Intent(PersonnelMenu.this, PersonnelKapiUnlock.class);
                    intentDoor.putExtra("PersonelBlok",tempBlok);
                    intentDoor.putExtra("PersonelKatNo",tempKatNo);
                    intentDoor.putExtra("PersonelAdSoyAd",txtAdSoyad);
                    intentDoor.putExtra("PersoneEmail",textEmail);
                    startActivity(intentDoor);
                }
                else if(txtActive.equals("false")){
                    Toast.makeText(getApplicationContext(),"Account Not Active",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Only Housekeeper Will Open Doors.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ClickCard (View view){
        if(txtActive.equals("false")){
            Toast.makeText(getApplicationContext(), "Account Not Active", Toast.LENGTH_SHORT).show();
        }else {
            switch (view.getId()) {
                case R.id.cardMeatings:
                    Intent intent = new Intent(PersonnelMenu.this, PersonnelMeating.class);
                    intent.putExtra("Departman",txtDepartman);
                    startActivity(intent);
                    break;
                case R.id.cardAnnouncement:
                    Intent intentduyuru = new Intent(PersonnelMenu.this, PersonnelAnnouncement.class);
                    intentduyuru.putExtra("PersonelAdSoyAdDuyuru",txtAdSoyad);
                    intentduyuru.putExtra("PersonelBlok1",tempBlok);
                    intentduyuru.putExtra("PersonelKaTno1",tempKatNo);
                    intentduyuru.putExtra("PersonelDpt",txtDepartman);
                    startActivity(intentduyuru);
                    break;
            }
        }
    }

    private void getKatSorumluPersonel(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<KatSorumluPersonellerWebApi>> call = api.getSorumluPersoneller();

        call.enqueue(new Callback<List<KatSorumluPersonellerWebApi>>() {
            @Override
            public void onResponse(Call<List<KatSorumluPersonellerWebApi>> call, Response<List<KatSorumluPersonellerWebApi>> response) {
                List<KatSorumluPersonellerWebApi> tempKatSorumluPersonnel = response.body();
                for(int i=0;i<tempKatSorumluPersonnel.size();i++){
                    if(txtAdSoyad.equals(tempKatSorumluPersonnel.get(i).getPersonelAdiSoyAdi())){
                        tempBlok = tempKatSorumluPersonnel.get(i).getBlok();
                        tempKatNo = tempKatSorumluPersonnel.get(i).getKatNo();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<KatSorumluPersonellerWebApi>> call, Throwable t) {

            }
        });
    }
}