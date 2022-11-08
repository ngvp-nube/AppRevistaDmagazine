package com.dmagazine.mydmagazinechile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.dmagazine.mydmagazinechile.modelo.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    private String idUsuario;
    private TextView usuarioP, nombreP, correoP;
    Button atrasDashboard;
    String email1;
    String pass1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        RecibirDatosdeActividadLogin();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        TextView idUserView = (TextView) findViewById(R.id.txtIDPerfil);
        usuarioP = (TextView) findViewById(R.id.txtUserPerfil);
        nombreP = (TextView) findViewById(R.id.txtNamesPerfil);
        correoP = (TextView) findViewById(R.id.txtCorreoPerfil);

        atrasDashboard = (Button) findViewById(R.id.bttnAtrasProfile);


        idUsuario = mAuth.getCurrentUser().getUid();

        atrasDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, MenuActivity.class);
                i.putExtra("Email", email1);
                i.putExtra("Pass", pass1);
                startActivity(i);
            }
        });

        //addValueEvent = leer contantemente
        //addValueFOrSinglw = leer una sola vez
        mDatabase.child("Users").child(idUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //snapshop es el objeto
                User usuario = snapshot.getValue(User.class);
                usuarioP.setText(usuario.getUsuario());
                correoP.setText(usuario.getCorreo());
                String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();
                nombreP.setText(nombreCompleto);
                idUserView.setText(usuario.getIdUsuario());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }//onCreate:end

    private void RecibirDatosdeActividadLogin() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            String email = extra.getString("Email");
            String pass = extra.getString("Pass");
            email1 = email;
            pass1 = pass;
        }

    }
}