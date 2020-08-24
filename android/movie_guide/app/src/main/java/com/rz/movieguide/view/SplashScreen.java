package com.rz.movieguide.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.rz.movieguide.R;

public class SplashScreen extends AppCompatActivity {
    ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashImage = findViewById(R.id.splash_image);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_image);
        splashImage.startAnimation(animation);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, MovieActivity.class);
            startActivity(intent);
        }, 2200);
    }
}