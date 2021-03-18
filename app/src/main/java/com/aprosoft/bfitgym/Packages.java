package com.aprosoft.bfitgym;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
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

import org.json.JSONArray;
import org.json.JSONException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Packages extends AppCompatActivity {

    ListView lvpackages;
    PackagesAdapter packagesAdapter;
    TextView price,duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        price = (TextView)findViewById(R.id.price);
        duration = (TextView) findViewById(R.id.duration);


        lvpackages = (ListView)findViewById(R.id.lvpackages);

        packageslist();


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


    public void packageslist()
    {
        final SweetAlertDialog pDialog = new SweetAlertDialog(Packages.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


        RequestQueue requestQueue = Volley.newRequestQueue(Packages.this);
        String url = "http://bfitapi.bfit.co.in//api/showpackages/show";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            pDialog.dismiss();
                            final JSONArray jsonarray = new JSONArray(response);
                            Log.d("jsonarray" ,"" +jsonarray.toString());




                            price.setText("RS "+jsonarray.getJSONObject(jsonarray.length()-1).getString("Fee"));
                            duration.setText(jsonarray.getJSONObject(jsonarray.length()-1).getString("Name"));


                            packagesAdapter = new PackagesAdapter(Packages.this , jsonarray);
                            lvpackages.setAdapter(packagesAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            new SweetAlertDialog(Packages.this, SweetAlertDialog.ERROR_TYPE);
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

                new SweetAlertDialog(Packages.this, SweetAlertDialog.ERROR_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Try Again !");
                pDialog.setCancelable(false);
                pDialog.show();
            }


        }) ;

        requestQueue.add(stringRequest);


    }
}


class PackagesAdapter extends BaseAdapter
{

    Activity context;
    JSONArray jsonArray;
    ImageLoader imageLoader;


    public PackagesAdapter( Activity context ,JSONArray jsonArray)
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
            v = inflater.inflate(R.layout.packagexml, null, true);
        }
            TextView packagename = (TextView) v.findViewById(R.id.packagename);
            TextView packagefee = (TextView) v.findViewById(R.id.packagefee);


            try {
                packagename.setText(jsonArray.getJSONObject(i).getString("Name"));
                packagefee.setText("RS  "+jsonArray.getJSONObject(i).getString("Fee"));

            } catch (JSONException e) {
                e.printStackTrace();
            }



        return v;
    }
}