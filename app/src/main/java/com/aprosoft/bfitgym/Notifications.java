package com.aprosoft.bfitgym;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.mskurt.neveremptylistviewlibrary.NeverEmptyListView;

import org.json.JSONArray;
import org.json.JSONException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Notifications extends AppCompatActivity {

    NeverEmptyListView lvnotification;
    NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        lvnotification = (NeverEmptyListView)findViewById(R.id.lvnotification);

        notilist();


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) //home is back button id which is bydefault in android
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void notilist()
    {
        final SweetAlertDialog pDialog = new SweetAlertDialog(Notifications.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


        RequestQueue requestQueue = Volley.newRequestQueue(Notifications.this);
        String url = "http://bfitapi.bfit.co.in//api/shownotification/show";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            pDialog.dismiss();
                            final JSONArray jsonarray = new JSONArray(response);
                            Log.d("jsonarray" ,"" +jsonarray.toString());



                            notificationAdapter = new NotificationAdapter(Notifications.this , jsonarray);
                            lvnotification.setAdapter(notificationAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            new SweetAlertDialog(Notifications.this, SweetAlertDialog.ERROR_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Try Again !");
                            pDialog.setCancelable(false);
                            pDialog.show();

                        }


                    }
                    //    Log.d("list", "" + list

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "" + error);

                new SweetAlertDialog(Notifications.this, SweetAlertDialog.ERROR_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Try Again !");
                pDialog.setCancelable(false);
                pDialog.show();
            }


        }) ;

        requestQueue.add(stringRequest);


    }
}


class NotificationAdapter extends BaseAdapter
{

    Activity context;
    JSONArray jsonArray;
    ImageLoader imageLoader;


    public NotificationAdapter( Activity context ,JSONArray jsonArray)
    {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            v = inflater.inflate(R.layout.notificationxml, null, true);
            TextView title = (TextView) v.findViewById(R.id.title);
            TextView description = (TextView) v.findViewById(R.id.description);


            try {
                title.setText(jsonArray.getJSONObject(i).getString("title"));
                description.setText(jsonArray.getJSONObject(i).getString("description"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return v;
    }

}
