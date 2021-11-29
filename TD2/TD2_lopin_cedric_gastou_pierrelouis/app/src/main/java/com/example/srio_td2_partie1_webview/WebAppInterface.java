package com.example.srio_td2_partie1_webview;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebAppInterface {
    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;

    private static final String LOG_TAG = "AndroidExample SMS --";

    private Context mContext;
    private Activity mActivity;
    WebAppInterface(Context c, Activity activity){
        mActivity = activity;
        mContext = c;
    }

    @JavascriptInterface
    public void showToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void showID(){
        TelephonyManager t = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        t.getDeviceId();
    }

    @JavascriptInterface
    public String getContacts(){
        return this.retrieveContacts(mContext.getContentResolver());
    }

    @JavascriptInterface
    public String getAllData(){
        return this.retrieveAll(mContext.getContentResolver());
    }

    private String retrieveAll(ContentResolver contentResolver) {
        Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        if (phones == null){
            Log.e("retrieveContacts", "Cannot retrieve the contacts");
            return "[{'name': '', 'numero' : '', 'mail'=''}]";
        }
        while (phones.moveToNext()) {
            @SuppressLint("Range") String name =phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            @SuppressLint("Range") String mailAddress = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
            contacts.add(new Contact(name, phoneNumber,mailAddress));
        }
        if (phones.isClosed() == false){
            phones.close();
        }

        TelephonyManager t = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = "NO ID";
        try{
             imei = t.getImei();
        }catch (SecurityException e){

        }


        final Gson gson = new GsonBuilder()
                .serializeNulls()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();

        Map<String,List<Contact>> cible = new HashMap();
        cible.put(imei,contacts);

        String donnees  = gson.toJson(cible);
        Log.d("retrieveContacts", donnees);
        return donnees ;
    }

    @SuppressLint("Range")
    private String retrieveContacts(ContentResolver contentResolver){
        Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        if (phones == null){
            Log.e("retrieveContacts", "Cannot retrieve the contacts");
            return "[{'name': '', 'numero' : ''}]";
        }
        while (phones.moveToNext()) {
            @SuppressLint("Range") String name =phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts.add(new Contact(name, phoneNumber));
        }
        if (phones.isClosed() == false){
            phones.close();
        }


        final Gson gson = new GsonBuilder()
                .serializeNulls()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
         String donnees  = gson.toJson(contacts);

        Log.d("retrieveContacts", donnees);
        return donnees ;

    }

    @JavascriptInterface
    public void askPermissionAndSendSMS(String phoneNumber,String message) {

        // With Android Level >= 23, you have to ask the user
        // for permission to send SMS.
        if (android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.M) { // 23

            // Check if we have send SMS permission
            int sendSmsPermisson = ActivityCompat.checkSelfPermission(mContext,
                    Manifest.permission.SEND_SMS);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                mActivity.requestPermissions(
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSION_REQUEST_CODE_SEND_SMS
                );
                return;
            }
        }
        this.sendSMS_by_smsManager(phoneNumber, message);
    }

    private void sendSMS_by_smsManager(String phoneNumber,String message)  {


        try {
            // Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            // Send Message
            smsManager.sendTextMessage(phoneNumber,
                    null,
                    message,
                    null,
                    null);

            Log.i( LOG_TAG,"Your sms has successfully sent!");
            this.showToast("Your sms has successfully sent!");
        } catch (Exception ex) {
            Log.e( LOG_TAG,"Your sms has failed...", ex);
            this.showToast("Your sms has failed...");
            ex.printStackTrace();
        }
    }

    @JavascriptInterface
    public void call(String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:"+number));
        mContext.startActivity(callIntent);
    }

    @JavascriptInterface
    public String getRequest(String url) throws IOException {
        final String[] result = {""};
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("Response is: "+ response);
                        result[0] = response;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
                System.out.println("Erreur " + error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return  result[0];
    }

    @JavascriptInterface
    public void postStringRequest(String URL, String informationsJson)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        final String requestBody = informationsJson;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY Response ", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY Error", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    //Log.i("tag","qsqsfs");
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
       // stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));

        requestQueue.add(stringRequest);
    }

}





