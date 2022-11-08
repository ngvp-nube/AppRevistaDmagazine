package com.dmagazine.mydmagazinechile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SiguenosActivity extends AppCompatActivity {

    private Button mBttnFacebook;
    private Button mBttnInstagram;
    private Button mBttnTwitter;
    private Button mBttnYoutube;
    private Button bttnPagWEB;
    String email1, pass1;
    private Button atrasSiguenos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siguenos);
        ActionBar actionBar = getSupportActionBar();
        RecibirDatosdeActividadLogin();
        actionBar.hide();

        mBttnFacebook = (Button) findViewById(R.id.bttnFacebook);
        mBttnInstagram = (Button) findViewById(R.id.bttnInstagram);
        mBttnTwitter = findViewById(R.id.bttnTwitter);
        mBttnYoutube = (Button) findViewById(R.id.bttnYoutube);
        bttnPagWEB = (Button) findViewById(R.id.bttnWeb);
        atrasSiguenos = (Button) findViewById(R.id.bttnAtrasSiguenos);

        mBttnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri urlFaceBook = Uri.parse("https://www.facebook.com/Dmagazinechile/?modal=admin_todo_tour");
                Intent intFacebook = new Intent(Intent.ACTION_VIEW,urlFaceBook);
                startActivity(intFacebook);
            }
        });
        mBttnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri urlInstagram = Uri.parse("https://www.instagram.com/dmagazinechile/?hl=es-la");
                Intent intInstagram = new Intent(Intent.ACTION_VIEW,urlInstagram);
                startActivity(intInstagram);
            }
        });
        mBttnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri urlTwitter = Uri.parse("https://twitter.com/dmagazine_chile");
                Intent intTwitter = new Intent(Intent.ACTION_VIEW,urlTwitter);
                startActivity(intTwitter);
            }
        });
        mBttnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri urlYoutube = Uri.parse("https://www.youtube.com/channel/UCrGjasZoFBBQILfkKEr8a-A");
                Intent intYoutube = new Intent(Intent.ACTION_VIEW,urlYoutube);
                startActivity(intYoutube);
            }
        });
        bttnPagWEB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri urlPagWeb = Uri.parse("https://www.chilemagazine.net/");
                Intent intPagWeb = new Intent(Intent.ACTION_VIEW,urlPagWeb);
                startActivity(intPagWeb);
            }
        });

        atrasSiguenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SiguenosActivity.this, MenuActivity.class);
                i.putExtra("Email",email1);
                i.putExtra("Pass",pass1);
                startActivity(i);

            }
        });
    }
    private void RecibirDatosdeActividadLogin(){
        Bundle extra = getIntent().getExtras();
        if (extra !=null){
            String email= extra.getString("Email");
            String pass = extra.getString("Pass");
            email1 = email;
            pass1 = pass;
        }

    }
}