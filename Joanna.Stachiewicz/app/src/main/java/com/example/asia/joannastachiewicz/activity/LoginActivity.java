package com.example.asia.joannastachiewicz.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asia.joannastachiewicz.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends Activity {

    private Button ZalogujButton;
    private EditText EmailET;
    private EditText HasloET;
    String Email;
    String Haslo;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean Error = false;
    boolean LoggedIn = false;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref = getApplicationContext().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        LoggedIn = pref.getBoolean("LoggedIn",false);
        if (LoggedIn) {
            intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailET = (EditText)findViewById(R.id.email);
        HasloET = (EditText)findViewById(R.id.password);
        ZalogujListener();
    }

    public void ZalogujListener() {
        ZalogujButton = (Button)findViewById(R.id.email_sign_in_button);
        ZalogujButton.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {
            EmailET.setError(null);
            HasloET.setError(null);
            Error = false;

            Email = EmailET.getText().toString();
            Haslo = HasloET.getText().toString();

            if (!EmailValid()) {
                EmailET.setError("Nieprawidłowy email!");
                Error = true;
            }
            if (TextUtils.isEmpty(Email)) {
                EmailET.setError("Pole wymagane!");
                Error = true;
            }
            if (!PasswordValid()) {
                HasloET.setError("Nieprawidłowe hasło!");
                Error = true;
            }
            if (TextUtils.isEmpty(Haslo)) {
                HasloET.setError("Pole wymagane!");
                Error = true;
            }

            if (!Error) {
                Login();
            }
        }
    };

    public void Login() {
        editor = pref.edit();
        editor.putBoolean("LoggedIn", true);
        editor.putString("Email", Email);
        editor.putString("Haslo", Haslo);
        editor.commit();

        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intentMain);
    }

    private boolean EmailValid() {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }

    private boolean PasswordValid() {
        final String PASS_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})";
        Pattern pattern = Pattern.compile(PASS_PATTERN);
        Matcher matcher = pattern.matcher(Haslo);
        return matcher.matches();
    }

    @Override
    public void onRestart() {
        LoggedIn = pref.getBoolean("LoggedIn",false);
        if(LoggedIn)
            finish();
        EmailET.setText("");
        HasloET.setText("");
        super.onRestart();
    }
}



