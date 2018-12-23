package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonnelEnter extends AppCompatActivity {

    private CardView cardPersonnel;
    private String[] PersonnelMail,PersonnelEnter,PersonnelActive,PersonnelDepartman,PersonnelAdSoyad;
    private boolean ifFalse;
    private String PersonnelAdiSifre;
    private EditText txtUsername,txtPassword;
    private String PersonnelGirisMail;
    private String PersonnelGirisActive;
    private String PersonnelGirisDepartman;
    private String PersonnelGirisAdSoyad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_enter);

        getPersonnelInformation();

        txtUsername = findViewById(R.id.editText);
        txtPassword = findViewById(R.id.editText2);

        cardPersonnel = findViewById(R.id.cardViewEnter);
        cardPersonnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ifFalse = false;
                PersonnelAdiSifre = txtUsername.getText().toString()+txtPassword.getText().toString();
                if(PersonnelEnter == null){
                    Toast.makeText(getApplicationContext(),"Bağlantı Problemi Var.\n Yeniden Deneyin !",Toast.LENGTH_SHORT).show();
                }else{
                    for(int i=0;i<PersonnelEnter.length;i++){
                        if(PersonnelAdiSifre == null){
                            Toast.makeText(getApplicationContext(),"Boş Girildi",Toast.LENGTH_SHORT).show();
                        }
                        else if(PersonnelAdiSifre.equals(PersonnelEnter[i])){
                            ifFalse = true;
                            PersonnelGirisMail = PersonnelMail[i];
                            PersonnelGirisActive = PersonnelActive[i];
                            PersonnelGirisDepartman = PersonnelDepartman[i];
                            PersonnelGirisAdSoyad = PersonnelAdSoyad[i];
                            Intent intent = new Intent(PersonnelEnter.this,PersonnelMenu.class);
                            intent.putExtra("PersonnelEmail",PersonnelGirisMail);
                            intent.putExtra("PersonnelActive",PersonnelGirisActive);
                            intent.putExtra("PersonnelDepartman",PersonnelGirisDepartman);
                            intent.putExtra("PersonnelAdSoyad",PersonnelGirisAdSoyad);
                            startActivity(intent);
                            break;
                        }
                        else {

                        }
                    }
                    if(ifFalse==false){
                        Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getPersonnelInformation() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<PersonnelWebApi>> call = api.getPersonnelInfo();

        call.enqueue(new Callback<List<PersonnelWebApi>>() {
            @Override
            public void onResponse(Call<List<PersonnelWebApi>> call, Response<List<PersonnelWebApi>> response) {
                List<PersonnelWebApi> tempPersonnel = response.body();
                PersonnelMail = new String[tempPersonnel.size()];
                PersonnelEnter = new String[tempPersonnel.size()];
                PersonnelActive = new String[tempPersonnel.size()];
                PersonnelDepartman = new String[tempPersonnel.size()];
                PersonnelAdSoyad = new String[tempPersonnel.size()];
                for(int i=0;i<tempPersonnel.size();i++){
                    PersonnelMail[i] = tempPersonnel.get(i).getPersonelMail();
                    PersonnelEnter[i] = tempPersonnel.get(i).getUsernamePassword();
                    PersonnelActive[i] = tempPersonnel.get(i).getSil()+"";
                    PersonnelDepartman[i] = tempPersonnel.get(i).getPersonelDepartman();
                    PersonnelAdSoyad[i] = tempPersonnel.get(i).getPersonelAdSoyad();
                }
            }

            @Override
            public void onFailure(Call<List<PersonnelWebApi>> call, Throwable t) {

            }
        });

    }
}
