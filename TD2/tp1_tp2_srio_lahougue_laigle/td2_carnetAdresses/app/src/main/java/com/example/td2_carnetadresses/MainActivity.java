package com.example.td2_carnetadresses;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] perm = {"android.permission.INTERNET","android.permission.READ_CONTACTS","android.permission.SEND_SMS","android.permission.CALL_PHONE"};
        requestAllPermissions(perm);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context activityContext=this.getApplicationContext();
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/www/index.html");
    }

    //Demande des permissions pendant l'exécution
    public void requestAllPermissions(String[] permissions) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 80);
        }
    }
}