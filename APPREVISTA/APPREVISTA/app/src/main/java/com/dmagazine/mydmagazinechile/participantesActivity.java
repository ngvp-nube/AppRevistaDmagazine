package com.dmagazine.mydmagazinechile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class participantesActivity extends AppCompatActivity {
    Button mascota,festival,menu;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    String email1;
    String pass1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        RecibirDatosdeActividadLogin();
        mascota =(Button) findViewById(R.id.btnirmasc);
        festival = (Button)findViewById(R.id.btnirfest);
        menu = (Button)findViewById(R.id.iramenu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(participantesActivity.this, MascotaActivity.class);
                i.putExtra("Email",email1);
                i.putExtra("Pass",pass1);
                startActivity(i);
            }
        });
        festival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(participantesActivity.this, VotacionCantantesActivity.class);
                i.putExtra("Email",email1);
                i.putExtra("Pass",pass1);
                startActivity(i);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(participantesActivity.this, MenuActivity.class);
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