package com.dmagazine.mydmagazinechile;

import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class QuienesSomosActivity extends AppCompatActivity {

    Button mButtonQuienesSomos;
    TextView nCelular;
    TextView textCorreo;
    String email1, pass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quienes_somos);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        RecibirDatosdeActividadLogin();

        nCelular = (TextView) findViewById(R.id.numCelular);
        Linkify.addLinks(nCelular,Linkify.PHONE_NUMBERS);

        textCorreo = (TextView) findViewById(R.id.txtCorreo);
        Linkify.addLinks(textCorreo,Linkify.EMAIL_ADDRESSES);

        mButtonQuienesSomos = (Button) findViewById(R.id.AtrasQuienesSomos);
        mButtonQuienesSomos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuienesSomosActivity.this, MenuActivity.class);
                i.putExtra("Email",email1);
                i.putExtra("Pass",pass1);
                startActivity(i);
            }
        });
    }

    private void RecibirDatosdeActividadLogin() {
        Bundle extra = getIntent().getExtras();
        if (extra !=null){
            String email= extra.getString("Email");
            String pass = extra.getString("Pass");
            email1 = email;
            pass1 = pass;
        }
    }
}