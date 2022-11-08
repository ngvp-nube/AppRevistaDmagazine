package com.dmagazine.mydmagazinechile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.dmagazine.mydmagazinechile.modelo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.file.attribute.UserDefinedFileAttributeView;

public class LoginActivity extends RegistrationActivity {

    private EditText mLogUser, mLogPass;
    private TextView mLogOlvidarContraseña;;
    private Button mButtnIniciaSeccion, mButtnRegistrar;

     String email ="";
     String pass ="";


    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        mLogUser = (EditText) findViewById(R.id.editLoginUsuario);
        mLogPass = (EditText) findViewById(R.id.editLoginPass);
        mButtnIniciaSeccion = (Button) findViewById(R.id.bttnInicioSeccion);
        mButtnRegistrar = (Button) findViewById(R.id.bttnRegistrar);

        mLogOlvidarContraseña = (TextView) findViewById(R.id.txtView_forgot_password);

            mButtnIniciaSeccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mLogUser.getText().toString();
                pass = mLogPass.getText().toString();
                if(!email.isEmpty() && !pass.isEmpty()){

                    loginUser();
                }
                else{
                    Toast.makeText(LoginActivity.this, "complete los campos", Toast.LENGTH_SHORT).show();
                }
            }

        });
        mButtnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
            }
        });

        mLogOlvidarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RecuperarActivity.class));
            }
        });
    }
    private void loginUser() {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser fuser = mAuth.getCurrentUser();
                    if(!fuser.isEmailVerified()){
                        Toast.makeText(LoginActivity.this, "Correo No Verificado", Toast.LENGTH_SHORT).show();
                    }
                    if(fuser.isEmailVerified()){
                        Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                        i.putExtra("Email",email);
                        i.putExtra("Pass",pass);
                        startActivity(i);
                        //startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    }


                }else{
                    Toast.makeText(LoginActivity.this, "Nose pudo inciar session", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}


//https://www.androidcode.ninja/android-alertdialog-example/ -> Android AlertDialog.Builder Example Codes and Output
