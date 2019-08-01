package com.example.mlm.UI.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.mlm.MainActivity;
import com.example.mlm.R;
import com.example.mlm.Util.PrefrenceHandler;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView img = findViewById(R.id.crezy);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.downtoup);
        img.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (PrefrenceHandler.getPreferences(Splash.this).isLogin())
                    startActivity(new Intent(Splash.this, MainActivity.class));
                else startActivity(new Intent(Splash.this, SendOtp.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
