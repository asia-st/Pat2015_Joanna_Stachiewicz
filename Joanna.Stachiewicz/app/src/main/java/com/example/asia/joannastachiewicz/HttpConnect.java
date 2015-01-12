package com.example.asia.joannastachiewicz;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpConnect {

    private static final String CLASS_NAME = HttpConnect.class.getSimpleName();
    private static final Handler handler = new Handler(Looper.getMainLooper());
    private static final int CODE_200 = 200;
    private static final int CODE_201 = 201;
    public static final int CONNECT_TIMEOUT = 10000;
    public static final String CHARSET = "UTF-8";

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void get(String url, onHttpResponse onHttpRequest) {
        executorService.execute(new connectRunnable(url, onHttpRequest));
    }

    private class connectRunnable implements Runnable {
        private String stringUrl;
        private onHttpResponse onHttpRequest;

        public connectRunnable(final String url, final onHttpResponse onHttpRequest) {
            this.stringUrl = url;
            this.onHttpRequest = onHttpRequest;
        }

        @Override
        public void run() {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(stringUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setConnectTimeout(CONNECT_TIMEOUT);

                if (urlConnection.getResponseCode() == CODE_200 || urlConnection.getResponseCode() == CODE_201) {
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    onSuccess(inputStreamToString(inputStream));
                } else {
                    onError("Http error code " + urlConnection.getResponseCode());
                }
            } catch (MalformedURLException e) {
                onError(e.getMessage());
            } catch (IOException e) {
                onError(e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        private String inputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, CHARSET));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }

        private void onSuccess(final String response) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onHttpRequest.onHttpResponseSuccess(response);
                }
            });
        }

        private void onError(final String message) {
            Log.e(CLASS_NAME, message);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onHttpRequest.onHttpResponseError(message);
                }
            });
        }
    }
}
