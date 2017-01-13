package com.example.sandi.funtastiq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Have_Winner extends AppCompatActivity {

    TextView feed,group;
    ImageView home,exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_have__winner);

        feed = (TextView)findViewById(R.id.tv_feedback);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Feedback.class);
                startActivity(i);
                finish();
            }
        });
        group = (TextView)findViewById(R.id.tv_group);
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Group.class);
                startActivity(i);
                finish();
            }
        });
        home = (ImageView)findViewById(R.id.iv_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        exit = (ImageView)findViewById(R.id.iv_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
