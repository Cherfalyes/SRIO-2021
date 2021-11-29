package com.example.td1srio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] permissions = {"android.permission.CALL_PHONE"};
        this.requestAllPermissions(permissions);

        WebView myWebView = findViewById(R.id.webview);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.loadUrl("file:///android_asset/www/index.html");
        //myWebView.loadUrl("https://www.twitch.tv/jltomy");

        //TextView textView = findViewById(R.id.textView);

        //String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
        //        Settings.Secure.ANDROID_ID);

        //textView.setText(android_id);

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");

    }

    private void requestAllPermissions(String[] permissions) {
        //Demande des permissions pendant l'exécution
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 80);
        }
    }

    /**public void onSendButton(View v){
        Context context = getApplicationContext();
        CharSequence text = "coucou";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
     **/
}

class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast() {
        Toast.makeText(mContext, "Well Done!", Toast.LENGTH_SHORT).show();
    }

    public void showId(String toast){
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
    public String getId(){
        return Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @JavascriptInterface
    public void composeNum(String number)
    {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+number));
        mContext.startActivity(intent);
    }


}