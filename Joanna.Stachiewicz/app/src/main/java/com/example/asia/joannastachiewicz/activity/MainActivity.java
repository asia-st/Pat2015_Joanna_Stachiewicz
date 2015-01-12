package com.example.asia.joannastachiewicz.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.asia.joannastachiewicz.HttpConnect;
import com.example.asia.joannastachiewicz.JsonData;
import com.example.asia.joannastachiewicz.JsonListAdapter;
import com.example.asia.joannastachiewicz.R;
import com.example.asia.joannastachiewicz.onHttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public final static String BASE_SERVER_URL = "http://asia1234.cba.pl/page_0.json";
    private static final String CLASS_NAME = MainActivity.class.getSimpleName();
    private Button WylogujButton;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private ListView JSONListView;
    public List<String> urls = new ArrayList<String>();
    public List<String> titles = new ArrayList<String>();
    public List<String> descs = new ArrayList<String>();
    private JsonListAdapter adapter;
    ProgressBar progress;
    Handler progressHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getJson();
        adapter = new JsonListAdapter(this, titles, descs, urls);
        JSONListView = (ListView)findViewById(R.id.listView);
        JSONListView.setAdapter(adapter);

        pref = getApplicationContext().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        WylogujListener();
    }

    private void getJson() {
        progress = (ProgressBar)findViewById(R.id.progressBar);
        progress.setProgress(0);
        progress.setIndeterminate(true);

        HttpConnect connect = new HttpConnect();
        connect.get(BASE_SERVER_URL, new onHttpResponse() {
            @Override
            public void onHttpResponseSuccess(final String response) {
                getFromResponse(response);
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onHttpResponseError(final String errorMessage) {
                progress.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                Log.e(CLASS_NAME, errorMessage);
            }
        });
    }

    private void getFromResponse(final String response) {
        try {
            titles.clear();
            List<JSONObject> elements = JsonData.getJsonObject(response);
            for (int i = 0; i < elements.size(); i++) {
                titles.add(elements.get(i).getString("title"));
                descs.add(elements.get(i).getString("desc"));
                urls.add(elements.get(i).getString("url"));
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
