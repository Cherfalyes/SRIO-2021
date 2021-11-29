package com.example.td2_carnetadresses;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class WebAppInterface {
    Context mContext;
    ArrayList<String> contactsList;

    /**
     * Instantiate the interface and set the context
     */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /**
     * Function to read contacts using content resolver
     */
    @JavascriptInterface
    public String readContacts() {
        //Récupérer les contacts
        Cursor phones = mContext.getContentResolver().query
                (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        while (phones.moveToNext())
        {
            @SuppressLint("Range") String name=phones.getString(phones.getColumnIndex
                    (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String phoneNumber = phones.getString(phones.getColumnIndex
                    (ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts.add(new Contact(name, phoneNumber));
        }
        phones.close();
        String donneesJson = new Gson().toJson(contacts);
        return donneesJson;
    }

    @JavascriptInterface
    public void sendSMS(String number, String content) {
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("SMS ", "sent");
                Toast.makeText(mContext, "SMS sent", Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("SMS ", "delivered");
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));
        SmsManager smsManager = SmsManager.getDefault();
        PendingIntent sentIntent = PendingIntent.getBroadcast(mContext, 100, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveryIntent = PendingIntent.getBroadcast(mContext, 200, new Intent("SMS_DELIVERED_ACTION"), 0);
        smsManager.sendTextMessage(number, null, content, sentIntent, deliveryIntent);
    }

    @JavascriptInterface
    public void call(String number) {
        String numToCall = "tel:"+number;
        Uri num = Uri.parse(numToCall);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, num);
        mContext.startActivity(callIntent);
    }

    @JavascriptInterface
    public String getRequest(String url) throws IOException {
        final String[] result = {};
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
        System.out.println("contacts = "+informationsJson);
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

        requestQueue.add(stringRequest);
    }

}
