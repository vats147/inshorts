package com.inshortsapk.inshorts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class my_login extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);


        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct=GoogleSignIn.getLastSignedInAccount(this);

        if(acct!=null)
        {

            String personName=acct.getDisplayName();
            String personEmail=acct.getEmail();
            Toast.makeText(this, "welcome" + personName, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(my_login.this,MainActivity.class);
            startActivity(intent);
        }
        btnLogin=findViewById(R.id.btnLogin);
        //btnLogin.setBackgroundColor(Color.rgb(29,119,177));
        //(account);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(my_login.this, "mylogin click", Toast.LENGTH_SHORT).show();
                signIn();
            }
        });
    }
    public void signIn(){
        Intent signInIntent=gsc.getSignInIntent();
         startActivityForResult(signInIntent,1000);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                Toast.makeText(this, "TRY 1", Toast.LENGTH_SHORT).show();

                task.getResult(ApiException.class);




                //Toast.makeText(this, "TRY 12", Toast.LENGTH_SHORT).show();

                finish();

                //logout called
               // Intent intent=new Intent(this,logout.class);
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
            } catch (ApiException e) {

                // not logged in, print the actual error message for debugging purposes
                Toast.makeText(this, "Not Logged In: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}