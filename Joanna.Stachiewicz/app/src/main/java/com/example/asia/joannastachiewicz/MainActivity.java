package com.example.asia.joannastachiewicz;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private Button WylogujButton;
    private TextView EmailTV;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetEmail();
        WylogujListener();
    }

    public void SetEmail() {
        EmailTV = (TextView)findViewById(R.id.textView2);
        pref = getApplicationContext().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        EmailTV.setText(pref.getString("Email",""));
    }

    public void WylogujListener() {
        WylogujButton = (Button)findViewById(R.id.button_wyloguj);
        WylogujButton.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {
            editor = pref.edit();
            editor.clear();
            editor.commit();
            finish();
        }
    };
}
