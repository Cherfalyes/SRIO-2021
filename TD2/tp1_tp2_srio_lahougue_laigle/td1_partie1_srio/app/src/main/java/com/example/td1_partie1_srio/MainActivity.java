package com.example.td1_partie1_srio;

import android.content.Context;
import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context activityContext=this.getApplicationContext();
        WebView myWebView = new WebView(activityContext);
        setContentView(myWebView);
        //partie2
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("file:///android_asset/www/index.html");  //.loadUrl("https://news.ycombinator.com"); partie 1
    }
}