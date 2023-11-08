package com.codegama.Taskscheduler.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.codegama.Taskscheduler.R;

public class splashscreen extends AppCompatActivity {


    TextView textView;
    private static int timeout=3000;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        textView=findViewById(R.id.text_name);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splashscreen.this,dashboard.class));
                finish();
            }
        },timeout);
        Animation animation= AnimationUtils.loadAnimation(splashscreen.this,R.anim.animation2);
        textView.startAnimation(animation);
    }
}
