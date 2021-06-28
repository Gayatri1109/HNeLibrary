package com.example.elibrary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_TIME = 2500;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent();
                intent.setClass(MainActivity .this, LoginActivity.class);

                MainActivity .this.startActivity(intent);
                MainActivity .this.finish();

            }
        }, SPLASH_DISPLAY_TIME);

    }

}



