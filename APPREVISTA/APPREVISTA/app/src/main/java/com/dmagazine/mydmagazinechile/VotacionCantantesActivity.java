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

public class VotacionCantantesActivity extends AppCompatActivity {
    Button atras,lafer,denise;
    TextView txtmont,txtdenise;
    EditText edtloginemail, edtloginPass;
    int contmont,contdenis;

    private DatabaseReference mBaseReference;
    private FirebaseAuth mAuth;

    String email1,pass1,emailfinal,passfinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votacion_cantantes);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();
        mBaseReference = FirebaseDatabase.getInstance().getReference();

        ebtDatosMont();
        recibirdatosdeactividad();
        QuienVota();
        obtDatoDenise();

        edtloginemail = findViewById(R.id.editUsuario);
        edtloginPass = findViewById(R.id.editLoginPass);
        atras =(Button) findViewById(R.id.bttnAtrasVotacionCantantes);
        lafer =(Button)findViewById(R.id.bttnVotoMontlafert);
        denise=(Button)findViewById(R.id.bttnVotoDenisse);
        txtmont =(TextView) findViewById(R.id.textVotoMontlafert);
        txtmont.setText(Integer.toString(contmont));
        txtdenise=(TextView) findViewById(R.id.textVotoDenisse) ;
        txtdenise.setText(Integer.toString(contdenis));

        //set on clide botones
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(com.dmagazine.mydmagazinechile.VotacionCantantesActivity.this, com.dmagazine.mydmagazinechile.participantesActivity.class));
                Intent i = new Intent(VotacionCantantesActivity.this, participantesActivity.class);
                i.putExtra("Email",emailfinal);
                i.putExtra("Pass",passfinal);
                startActivity(i);
            }
        });
        lafer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseReference.child("Cantantes").child("Montlafert").child("Votos").setValue(contmont+1);
                UsuarioVotador();
                mensajeVotador();
            }
        });
        denise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseReference.child("Cantantes").child("Denisse").child("Votos").setValue(contdenis+1);
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
    private void UsuarioVotador() {
        Map<String, Object> map = new HashMap<>();
        map.put("Email",emailfinal);
        map.put("Pass",passfinal);
        String id = mAuth.getCurrentUser().getUid();
        mBaseReference.child("Usuarios Votadores Cantos").child(id).setValue(map);
    }
    private void ebtDatosMont(){
        mBaseReference.child("Cantantes").child("Montlafert").child("Votos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contmont = snapshot.getValue(Integer.class);
                txtmont.setText(Integer.toString(contmont));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void QuienVota() {
        String id = mAuth.getCurrentUser().getUid();
        mBaseReference.child("Usuarios Votadores Cantos").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    lafer.setEnabled(false);
                    denise.setEnabled(false);
                } else {
                    lafer.setEnabled(true);
                    denise.setEnabled(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void obtDatoDenise(){
        mBaseReference.child("Cantantes").child("Denisse").child("Votos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contdenis = snapshot.getValue(Integer.class);
                txtdenise.setText(Integer.toString(contdenis));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void mensajeVotador(){
        AlertDialog.Builder builder = new AlertDialog.Builder(VotacionCantantesActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(VotacionCantantesActivity.this).inflate(
                R.layout.layout_success_dialog,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
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