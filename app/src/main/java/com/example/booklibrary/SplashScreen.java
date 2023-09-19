package com.example.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {
    ImageView splash_image;
    TextView splash_logo_text;


    // animation
    Animation sideAnim, buttonAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

//        // hiding Actionbar
        Objects.requireNonNull(getSupportActionBar()).hide();


        // Hooks
        splash_image = findViewById(R.id.splash_image);
        splash_logo_text = findViewById(R.id.splash_logo_text);

        // Animation
        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        buttonAnim = AnimationUtils.loadAnimation(this,R.anim.button_anim);

        // set animation on elements
        splash_image.setAnimation(sideAnim);
        splash_logo_text.setAnimation(buttonAnim);


        // Splash screen using handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iSplash = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(iSplash);
                finish();
            }
        }, 4000);
    }
}