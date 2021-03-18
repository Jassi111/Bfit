package com.aprosoft.bfitgym;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by jasvi on 4/4/2018.
 */

public class MyApplication extends MultiDexApplication {

    JSONArray jsonads;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();


        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


    }


    public void calladsapi(Context context , final adsinterface interfacead)
    {


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url ="https://api.backendless.com/BF9B6976-E133-11B9-FF6C-0AECD1BE6A00/45CCC84B-7EC3-701A-FF21-14DEA4250800/data/AdsTable?where=isActive%3Dtrue";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            jsonads = new JSONArray(response);
                            interfacead.apiresponse(jsonads);


                        } catch (JSONException e) {
                            e.printStackTrace();


                        }


                    }
                    //    Log.d("list", "" + list

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "" + error);


            }


        }) ;

        requestQueue.add(stringRequest);

    }

}




