package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.media.tv.TvInputService;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String[] tempMusteri;
    private boolean ifFalse;
    private String[] tempMusteriKullaniciAdiSifre;
    private String[] tempSifreliMusteriEmail;
    private String[] tempSifreliActive;
    private String kullaniciAdiSifre;
    private String sifrelimusteriEmail;
    private String sifrelimusteriActive;
    private boolean tempcheck;
    private CardView cr;
    private LoginButton FaceSignin;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private SignInButton SignIn;
    private EditText txtKullanici,txtSifre;

    private GoogleApiClient googleApiClient;
    private static final String TAG = "MAIN_ACTIVITY";
    private static final int REQ_Code = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        showSifreliMusteriler();

        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        SignIn = findViewById(R.id.btn_login);

        txtKullanici = findViewById(R.id.txtuser);
        txtSifre = findViewById(R.id.txtpassword);

        // Username & Password Login
        cr = findViewById(R.id.logincardview);
        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ifFalse = true;
                kullaniciAdiSifre = txtKullanici.getText().toString()+txtSifre.getText().toString();
                if(tempMusteriKullaniciAdiSifre == null){
                    Toast.makeText(getApplicationContext(),"Bağlantı Problemi Var.\n Yeniden Deneyin !",Toast.LENGTH_SHORT).show();
                }else{
                    for(int i=0;i<tempMusteriKullaniciAdiSifre.length;i++){
                        if(kullaniciAdiSifre == null){
                            Toast.makeText(getApplicationContext(),"Boş Girildi",Toast.LENGTH_SHORT).show();
                        }
                        else if(kullaniciAdiSifre.equals(tempMusteriKullaniciAdiSifre[i])){
                            ifFalse = false;
                            sifrelimusteriEmail = tempSifreliMusteriEmail[i];
                            sifrelimusteriActive = tempSifreliActive[i];
                            Intent intent = new Intent(MainActivity.this,DenemeMenu.class);
                            intent.putExtra("sifreliEmail",sifrelimusteriEmail);
                            intent.putExtra("tempActive",sifrelimusteriActive);
                            startActivity(intent);
                            break;
                        }
                        else{

                        }
                    }
                    if(ifFalse==true){
                        Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // Setting Google Text
        TextView textView = (TextView) SignIn.getChildAt(0);
        textView.setTextSize(13);
        textView.setText("Continue with Google          ");

        // Underlining Personnel Text
        TextView tv = findViewById(R.id.textView3);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PersonnelEnter.class));
            }
        });

        FaceSignin = findViewById(R.id.fcbtn_login);
        FaceSignin.setReadPermissions("email", "public_profile");
        FaceSignin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getApplication())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "You Get an Error", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        FaceSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMusteriler();
                tempcheck=false;
            }
        });
    }

    private void signIn() {
        showMusteriler();
        tempcheck=false;
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_Code);
    }

    private void signout(){
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //For Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);
        //For Google
        if (requestCode == REQ_Code) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {

            }
        }
    }

    // Google Sign-in With Firebase Auth
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String name = user.getDisplayName();
                            String email = user.getEmail();
                            for(int i=0;i<tempMusteri.length;i++){
                                Log.d(TAG, "İÇERİ GİRDİ !!!");
                                if(email.equals(tempMusteri[i])){
                                    Log.d(TAG, "EMAİL HESABIN DOĞRU !!!");
                                    tempcheck=true;
                                    Log.d(TAG,"MÜŞTERİ CHECK :" + tempcheck);
                                    signout();
                                    Intent intent = new Intent(MainActivity.this,DenemeMenu.class);
                                    intent.putExtra("email",email);
                                    startActivity(intent);
                                    break;
                                }
                            }
                            if(tempcheck==false){
                                Log.d(TAG,"FALSE ALANINA GİRDİ");
                                DenemeMusteriler denemeMusteriler = new DenemeMusteriler(
                                        email,
                                        name,
                                        null,
                                        "0"
                                );
                                createMusteriler(denemeMusteriler);
                                signout();
                                Intent intent = new Intent(MainActivity.this,DenemeMenu.class);
                                intent.putExtra("email",email);
                                startActivity(intent);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Facebook Sign-in With Firebase Auth
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String name = user.getDisplayName();
                            String email = user.getEmail();
                            for(int i=0;i<tempMusteri.length;i++){
                                Log.d(TAG, "İÇERİ GİRDİ !!!");
                                if(email.equals(tempMusteri[i])){
                                    Log.d(TAG, "EMAİL HESABIN DOĞRU !!!");
                                    tempcheck=true;
                                    Log.d(TAG,"MÜŞTERİ CHECK :" + tempcheck);
                                    Intent intent = new Intent(MainActivity.this,DenemeMenu.class);
                                    intent.putExtra("email",email);
                                    startActivity(intent);
                                    break;
                                }
                            }
                            if(tempcheck==false){
                                Log.d(TAG,"FALSE ALANINA GİRDİ");
                                DenemeMusteriler denemeMusteriler = new DenemeMusteriler(
                                        email,
                                        name,
                                        null,
                                        "0"
                                );
                                createMusteriler(denemeMusteriler);
                                Intent intent = new Intent(MainActivity.this,DenemeMenu.class);
                                intent.putExtra("email",email);
                                startActivity(intent);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Display Musteriler
    public void showMusteriler(){

        Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(Api.BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

        Api api = retrofit.create(Api.class);

        Call<List<DenemeMusteriler>> call = api.getMusteriler();

        call.enqueue(new Callback<List<DenemeMusteriler>>() {
            @Override
            public void onResponse(Call<List<DenemeMusteriler>> call, Response<List<DenemeMusteriler>> response) {
                List<DenemeMusteriler> denemeMusteriler = response.body();
                tempMusteri = new String[denemeMusteriler.size()];
                for(int i=0;i<denemeMusteriler.size();i++){
                    tempMusteri[i] = denemeMusteriler.get(i).getMusteriMail();
                }
            }

            @Override
            public void onFailure(Call<List<DenemeMusteriler>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Display SifreliMusteriler
    public void showSifreliMusteriler(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<SifreliMusterilerWebApi>> call = api.getSifreliMusteriler();

        call.enqueue(new Callback<List<SifreliMusterilerWebApi>>() {
            @Override
            public void onResponse(Call<List<SifreliMusterilerWebApi>> call, Response<List<SifreliMusterilerWebApi>> response) {
                List<SifreliMusterilerWebApi> sifleriMusteriler = response.body();
                tempMusteriKullaniciAdiSifre = new String[sifleriMusteriler.size()];
                tempSifreliMusteriEmail = new String[sifleriMusteriler.size()];
                tempSifreliActive = new String[sifleriMusteriler.size()];
                for(int i=0;i<sifleriMusteriler.size();i++){
                    tempMusteriKullaniciAdiSifre[i] = sifleriMusteriler.get(i).getKullaniciSifre();
                    tempSifreliMusteriEmail[i] = sifleriMusteriler.get(i).getMusteriMail();
                    tempSifreliActive[i] = sifleriMusteriler.get(i).getMusteriActive();
                }
            }

            @Override
            public void onFailure(Call<List<SifreliMusterilerWebApi>> call, Throwable t) {

            }
        });
    }

    // Creates Musteriler
    private void createMusteriler(DenemeMusteriler denemeMusteriler){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<DenemeMusteriler> call = api.createMusteriler(denemeMusteriler);
        call.enqueue(new Callback<DenemeMusteriler>() {
            @Override
            public void onResponse(Call<DenemeMusteriler> call, Response<DenemeMusteriler> response) {
                Log.d(TAG, "Hesap Yaratıldı");
            }

            @Override
            public void onFailure(Call<DenemeMusteriler> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
