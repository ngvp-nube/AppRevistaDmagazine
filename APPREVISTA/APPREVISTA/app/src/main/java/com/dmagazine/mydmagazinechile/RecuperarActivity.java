package com.dmagazine.mydmagazinechile;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private EditText edittxtCorreoRecuperarClave;
    private Button bttnRecupera;
    private Button bttnCancelar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();
        /////
        edittxtCorreoRecuperarClave = (EditText) findViewById(R.id.edittxtCorreoRecuperarClave);
        bttnRecupera = (Button) findViewById(R.id.bttnRecuperar);
        bttnCancelar = (Button) findViewById(R.id.bttnCancelar);

        bttnRecupera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = edittxtCorreoRecuperarClave.getText().toString();
                if(TextUtils.isEmpty(userEmail)){
                    mostrarError();
                }
                else{
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mostrarMensaje();
                            }
                            else{
                                String message = task.getException().getMessage();
                                mostrarErrorCorreo();
                            }
                        }
                    });
                }
            }
        });
        bttnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.dmagazine.mydmagazinechile.RecuperarActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
    private void mostrarMensaje(){
        AlertDialog.Builder builder = new AlertDialog.Builder(com.dmagazine.mydmagazinechile.RecuperarActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(com.dmagazine.mydmagazinechile.RecuperarActivity.this).inflate(
                R.layout.layout_success_dialog,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView)view.findViewById(R.id.textTitle)).setText(getResources().getString(R.string.recuperarPassT));
        ((TextView)view.findViewById(R.id.textMessege)).setText(getResources().getString(R.string.recuperarPassM));
        ((Button)view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.okey));
        ((ImageView)view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_success);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                alertDialog.dismiss();
                startActivity(new Intent(com.dmagazine.mydmagazinechile.RecuperarActivity.this, LoginActivity.class));
            }
        });

        if(alertDialog.getWindow() !=null ){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    private void mostrarError(){
        AlertDialog.Builder builder = new AlertDialog.Builder(com.dmagazine.mydmagazinechile.RecuperarActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(com.dmagazine.mydmagazinechile.RecuperarActivity.this).inflate(
                R.layout.layout_success_dialog,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView)view.findViewById(R.id.textTitle)).setText(getResources().getString(R.string.titleError));
        ((TextView)view.findViewById(R.id.textMessege)).setText(getResources().getString(R.string.MensajeError));
        ((Button)view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.okey));
        ((ImageView)view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_error);

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
    private void mostrarErrorCorreo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(com.dmagazine.mydmagazinechile.RecuperarActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(com.dmagazine.mydmagazinechile.RecuperarActivity.this).inflate(
                R.layout.layout_success_dialog,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView)view.findViewById(R.id.textTitle)).setText(getResources().getString(R.string.titleError));
        ((TextView)view.findViewById(R.id.textMessege)).setText(getResources().getString(R.string.MensajeErrorCorreo));
        ((Button)view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.okey));
        ((ImageView)view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_error);

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