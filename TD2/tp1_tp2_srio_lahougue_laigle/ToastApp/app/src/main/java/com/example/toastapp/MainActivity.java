package com.example.toastapp;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] perm = {"android.permission.INTERNET"};
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