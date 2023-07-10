package com.example.miniwhatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.Duration;

public class SplashScreen extends AppCompatActivity {

    TextView textView;
    ImageView whatsAppIcon;
    Animations animations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        setIds();

        new Handler().postDelayed(new Delay(), 1500);
        animations = new Animations(SplashScreen.this);

        whatsAppIcon.startAnimation(animations.increaseSizeAnim(1500));


    }

    public void setIds(){
        textView = findViewById(R.id.SplashTextView);
        whatsAppIcon = findViewById(R.id.SplashWhatsAppIcon);
    }

    private class Delay implements Runnable{

        @Override
        public void run() {

            SharedPreferences preferences = getSharedPreferences("logIn" , MODE_PRIVATE);
            boolean isSignInDone = preferences.getBoolean("isDone" , false);
            Intent intent;

            if(isSignInDone){
                intent = new Intent(SplashScreen.this , MainActivity.class);
            }else{
                intent = new Intent(SplashScreen.this , SignInActivity.class);
            }
            startActivity(intent);
            finish();
        }
    }
}