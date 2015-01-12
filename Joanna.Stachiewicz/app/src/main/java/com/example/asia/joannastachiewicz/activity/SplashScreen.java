package com.example.asia.joannastachiewicz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.asia.joannastachiewicz.R;


public class SplashScreen extends Activity {

    private int time = 5000;
    Thread splashThread;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashThread = new Thread() {
            public void run() {
                try {
                    sleep(time);
                    intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        splashThread.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (splashThread.isAlive()) {
            splashThread.interrupt();
            splashThread = null;
        }
    }
}