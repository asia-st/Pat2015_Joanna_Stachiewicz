package com.example.joannastachiewicz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity {
    private Button WalyButton;
    private Button OrlyButton;
    private Button KatedraButton;
    private ImageView ZabytekIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ZabytkiListener();
    }

    public void ZabytkiListener() {
        ZabytekIV = (ImageView) findViewById(R.id.imageViewZabytek);

        WalyButton = (Button) findViewById(R.id.button1);
        OrlyButton = (Button) findViewById(R.id.button2);
        KatedraButton = (Button) findViewById(R.id.button3);

        WalyButton.setOnClickListener(listener1);
        OrlyButton.setOnClickListener(listener2);
        KatedraButton.setOnClickListener(listener3);
    }

    View.OnClickListener listener1 = new View.OnClickListener() {
        public void onClick(View v) {
            ZabytekIV.setImageResource(R.drawable.waly);
        }
    };

    View.OnClickListener listener2 = new View.OnClickListener() {
        public void onClick(View v) {
            ZabytekIV.setImageResource(R.drawable.orly);
        }
    };

    View.OnClickListener listener3 = new View.OnClickListener() {
        public void onClick(View v) {
            ZabytekIV.setImageResource(R.drawable.katedra);
        }
    };
}
