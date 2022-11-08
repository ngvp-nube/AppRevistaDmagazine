package com.dmagazine.mydmagazinechile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    TextView nombre,chile;
    ImageView Logo;


       @Override
    protected void onCreate(Bundle saveInstanceState) {
         super.onCreate(saveInstanceState);
         setContentView(R.layout.activity_splashscreen_animation);
         ActionBar actionBar = getSupportActionBar();
         actionBar.hide();
         Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
         Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

         nombre = findViewById(R.id.textViewSS);
         Logo = findViewById(R.id.imageLogo);
         chile = findViewById(R.id.textChile);

         chile.setAnimation(animacion2);
         nombre.setAnimation(animacion2);
         Logo.setAnimation(animacion1);

         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
               Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
               startActivity(intent);
               finish();
             }
         },4000);

     }
}

