package com.example.srio_td2_partie1_webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private WebAppInterface mWebInterface;
    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;
    private static final String LOG_TAG = "AndroidExample SMS --";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = {"android.permission.READ_SMS", "android.permission.SEND_SMS","android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS","android.permission.CALL_PHONE",
                "android.Manifest.permission.READ_PHONE_STATE","android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE"};
        this.requestAllPermissions(permissions);

        mWebInterface= new WebAppInterface(this.getApplicationContext(), this);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("MyApplication", consoleMessage.message() + " -- From line " +
                        consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());
                return true;
            }
        });


        myWebView.addJavascriptInterface(mWebInterface, "Android");

        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        String content = "";
        try {
            is = getAssets().open("www/index.html");
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8 ));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            content = sb.toString();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String encodedHtml = Base64.encodeToString(content.getBytes(),
                Base64.NO_PADDING);
        myWebView.loadData(encodedHtml, "text/html", "base64");


        mWebInterface.showToast("hello");



        //Demande des permissions pendant l'exécution


    }

    public void requestAllPermissions(String[] permissions){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 80);
        }
    }






    // When you have the request results
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE_SEND_SMS: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (SEND_SMS).
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i( LOG_TAG,"Permission granted!");
                    mWebInterface.showToast("Permission granted!");

                    //mWebInterface.sendSMS_by_smsManager();
                }
                // Cancelled or denied.
                else {
                    Log.i( LOG_TAG,"Permission denied!");
                    mWebInterface.showToast("Permission denied!");
                }
                break;
            }
        }
    }

    // When results returned
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_PERMISSION_REQUEST_CODE_SEND_SMS) {
            if (resultCode == RESULT_OK) {
                // Do something with data (Result returned).
                mWebInterface.showToast( "Action OK");
            } else if (resultCode == RESULT_CANCELED) {
                mWebInterface.showToast("Action canceled");
            } else {
                mWebInterface.showToast("Action Failed");
            }
        }
    }

}