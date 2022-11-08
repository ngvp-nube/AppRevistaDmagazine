package com.dmagazine.mydmagazinechile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {

    private Button mButtonSalir;
    private Button mBttnProfiler;
    private Button mBttnVotation;
    private Button mButtnSWhos;
    private Button mBttnRedSocial;
    private Button mBttnRevisita;
    DatabaseReference database;
    String message;
    Button view;
    private FirebaseAuth mAuth;
    String email1;
    String pass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("pdf");


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        RecibirDatosdeActividadLogin();

        mButtonSalir = (Button) findViewById(R.id.bttnCerrarSession);
        mBttnProfiler = (Button) findViewById(R.id.bttnProfiler);
        mBttnVotation = (Button) findViewById(R.id.bttnVotacion);
        mButtnSWhos = (Button) findViewById(R.id.bttnquienes);
        mBttnRedSocial = (Button) findViewById(R.id.bttnRedSocial);
        mBttnRevisita = (Button) findViewById(R.id.bttnRevisita);

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                message = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenuActivity.this, "Error al cargar", Toast.LENGTH_SHORT).show();
            }
        });

        mButtonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));

                finish();
            }
        });
        mBttnVotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, participantesActivity.class);
                i.putExtra("Email",email1);
                i.putExtra("Pass",pass1);
                startActivity(i);
            }
        });
        mButtnSWhos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, QuienesSomosActivity.class);
                i.putExtra("Email",email1);
                i.putExtra("Pass",pass1);
                startActivity(i);
            }
        });
        mBttnRedSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, SiguenosActivity.class);
                i.putExtra("Email",email1);
                i.putExtra("Pass",pass1);
                startActivity(i);
            }
        });
        mBttnProfiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, DashboardActivity.class);
                i.putExtra("Email",email1);
                i.putExtra("Pass",pass1);
                startActivity(i);
            }
        });
        mBttnRevisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[] = new CharSequence[]{
                        "Descargar Revista PDF",
                        "Ver Revista",
                        "Cancelar"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Seleccione una Opcion:");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ( which == 0){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
                            startActivity(intent);
                        }
                        if (which == 1){
                            Intent intent = new Intent(v.getContext(), PdfviewActivity.class);
                            intent.putExtra("url",message);
                            startActivity(intent);
                            }
                    }
                });
                builder.show();


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