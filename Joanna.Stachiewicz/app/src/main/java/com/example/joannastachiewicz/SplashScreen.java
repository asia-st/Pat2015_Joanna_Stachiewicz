package com.example.joannastachiewicz;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class SplashScreen extends Activity {
    private int time = 10000;
    Thread splashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashThread = new Thread() {
            public void run(){
                try {
                    sleep(time);
                    Intent intentMain = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intentMain);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        splashThread.start();
    }

    @Override
    public void onStop(){
        super.onStop();
        if(splashThread.isAlive()) {
            splashThread.interrupt();
            splashThread = null;
        }
    }
}
