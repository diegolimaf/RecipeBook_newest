package com.example.recipebook_newest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Timer;
import java.util.TimerTask;

public class cover extends AppCompatActivity {

    Animation fadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cover);

        new Timer().schedule(new TimerTask()
        {
            public void run()
            {
                startActivity(new Intent(cover.this, MainActivity.class));
                overridePendingTransition(R.anim.fade_in_mine, R.anim.fade_out_mine);
                finish();
            }
        }, 4000 );
    }
}