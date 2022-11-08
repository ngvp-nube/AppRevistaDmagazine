package com.dmagazine.mydmagazinechile;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MascotaActivity extends AppCompatActivity {

    //variables del login
    EditText edtloginemail;
    EditText edtloginPass;

    private Button mBttnMenu;
    Button btnVotoVotosGatitos;
    Button btnVotosPerritos;
    Button atrasMascota;
    TextView txtVotosGatito;
    TextView txtVotosPerrito;

    //creamos 2 variables temporales

    String emailfinal;
    String passfinal;
    //declaramos un contador
    int contadorGatito;
    int contadorPerrito;
    //referenciamos la base de datos
    DatabaseReference mBaseReference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota);
        mBaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        recibirdatosdeactividad();
        QuienVoto();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mBttnMenu = (Button) findViewById(R.id.bttnMenuMascota);

        recuperarDatos();
        recuperardatosperrito();


        edtloginemail = findViewById(R.id.editUsuario);
        edtloginPass = findViewById(R.id.editLoginPass);

        //los objeto
        txtVotosPerrito = (TextView) findViewById(R.id.textVotoDog);
        btnVotosPerritos = findViewById(R.id.bttnVotoDog);

        txtVotosGatito = findViewById(R.id.textVotoCat);
        btnVotoVotosGatitos = findViewById(R.id.bttnVotoCat);
        //le pasamos el valor a txt
        txtVotosGatito.setText(Integer.toString(contadorGatito));
        txtVotosPerrito.setText(Integer.toString(contadorPerrito));
        //Button de atras
        atrasMascota = (Button) findViewById(R.id.bttnMenuMascota);

        mBttnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MascotaActivity.this, MenuActivity.class));
            }
        });
        atrasMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(com.dmagazine.mydmagazinechile.MascotaActivity.this, participantesActivity.class));
                Intent i = new Intent(MascotaActivity.this, participantesActivity.class);
                i.putExtra("Email",emailfinal);
                i.putExtra("Pass",passfinal);
                startActivity(i);
            }
        });
        //boton gatitos
        btnVotoVotosGatitos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseReference.child("Mascotas").child("Gatito Hermoso").child("Votos").setValue(contadorGatito + 1);
                UsuarioVotador();
                mensajeVotador();
            }
        });
       // el boton de perritos
        btnVotosPerritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseReference.child("Mascotas").child("Perrito Dog Chow").child("Votos").setValue(contadorPerrito + 1);
                UsuarioVotador();
                mensajeVotador();
            }
        });
    }
    private void  recibirdatosdeactividad(){

        Bundle extra = getIntent().getExtras();
        if (extra !=null){
            String email = extra.getString("Email");
            String pass = extra.getString("Pass");
            emailfinal=email;
            passfinal=pass;
        }


    }
    private void recuperardatosperrito(){
        mBaseReference.child("Mascotas").child("Perrito Dog Chow").child("Votos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contadorPerrito = snapshot.getValue(Integer.class);
                txtVotosPerrito.setText(Integer.toString(contadorPerrito));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void recuperarDatos(){
        mBaseReference.child("Mascotas").child("Gatito Hermoso").child("Votos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contadorGatito = snapshot.getValue(Integer.class);
                txtVotosGatito.setText(Integer.toString(contadorGatito));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void UsuarioVotador(){
               Map<String, Object> map = new HashMap<>();
        map.put("Email",emailfinal);
        map.put("Pass",passfinal);
        String id = mAuth.getCurrentUser().getUid();
        mBaseReference.child("Usuarios Votadores").child(id).setValue(map);
    }

    private void QuienVoto(){
        String id  = mAuth.getCurrentUser().getUid();
        mBaseReference.child("Usuarios Votadores").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    btnVotoVotosGatitos.setEnabled(false);
                    btnVotosPerritos.setEnabled(false);

                }else{
                    btnVotosPerritos.setEnabled(true);
                    btnVotoVotosGatitos.setEnabled(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    private void mensajeVotador(){
        AlertDialog.Builder builder = new AlertDialog.Builder(com.dmagazine.mydmagazinechile.MascotaActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(com.dmagazine.mydmagazinechile.MascotaActivity.this).inflate(
                R.layout.layout_success_dialog,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        ) ;
        builder.setView(view);
        ((TextView)view.findViewById(R.id.textTitle)).setText(getResources().getString(R.string.VotaciomTitulo));
        ((TextView)view.findViewById(R.id.textMessege)).setText(getResources().getString(R.string.VotacionMensaje));
        ((Button)view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.VotacionBttn));
        ((ImageView)view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_success);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow() !=null ){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }




}