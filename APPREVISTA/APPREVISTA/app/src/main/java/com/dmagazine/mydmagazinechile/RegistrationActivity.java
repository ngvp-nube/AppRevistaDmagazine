package com.dmagazine.mydmagazinechile;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.PatternsCompat;
import kotlin.reflect.KFunction;

import com.dmagazine.mydmagazinechile.modelo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mUser;
    private EditText mName;
    private EditText mApellido;
    private EditText mCorreo;
    private EditText mPass;

    private Button mButtonMainRegistrar;
    private Button mButtonYaTengoCuenta;

    private String user ="";
    private String name ="";
    private String apellido ="";
    private String correo ="";
    private String pass ="";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mUser = (EditText) findViewById(R.id.editUsuario);
        mName = (EditText) findViewById(R.id.editNombre);
        mApellido = (EditText) findViewById(R.id.editApellido);
        mCorreo = (EditText) findViewById(R.id.editCorreo);
        mPass = (EditText) findViewById(R.id.editPass);
        mButtonMainRegistrar = (Button) findViewById(R.id.button);
        mButtonYaTengoCuenta = (Button) findViewById(R.id.BttnYaTengoCuenta);


        mButtonMainRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = mUser.getText().toString();
                name = mName.getText().toString();
                apellido = mApellido.getText().toString();
                correo = mCorreo.getText().toString();
                pass = mPass.getText().toString();
                if(!user.trim().isEmpty() &&!name.trim().isEmpty() &&!apellido.trim().isEmpty() && !correo.trim().isEmpty() && !pass.trim().isEmpty()){
                    if (pass.length() >=6){
                    registerUser();
                    }else{
                        Toast.makeText(com.dmagazine.mydmagazinechile.RegistrationActivity.this, "la password debe contener el menor 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                    }else{
                        Toast.makeText(com.dmagazine.mydmagazinechile.RegistrationActivity.this, "debe completar los campos correctamente", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        mButtonYaTengoCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.dmagazine.mydmagazinechile.RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
    private void registerUser() {
        mAuth.createUserWithEmailAndPassword(correo,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    String id = mAuth.getCurrentUser().getUid();
                    User usuariActual = new User(user, name, apellido, correo, id,pass);

                    mDatabase.child("Users").child(id).setValue(usuariActual).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                FirebaseUser fuser = mAuth.getCurrentUser();
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startActivity(new Intent(com.dmagazine.mydmagazinechile.RegistrationActivity.this, VerficicacionEmailActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(RegistrationActivity.this, "Nose Pudo Realizar La Accion", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }else{
                                Toast.makeText(com.dmagazine.mydmagazinechile.RegistrationActivity.this, "nose pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(com.dmagazine.mydmagazinechile.RegistrationActivity.this, "nose pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void mensajeVotador(){
        AlertDialog.Builder builder = new AlertDialog.Builder(com.dmagazine.mydmagazinechile.RegistrationActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(com.dmagazine.mydmagazinechile.RegistrationActivity.this).inflate(
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