package com.aprosoft.bfitgym;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Trainers extends AppCompatActivity {

    ListView lvtrainer;
    TrainerAdapter trainerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainers);

        lvtrainer = (ListView)findViewById(R.id.lvtrainer);

        trainerslist();


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


    public void trainerslist()
    {
        final SweetAlertDialog pDialog = new SweetAlertDialog(Trainers.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


        RequestQueue requestQueue = Volley.newRequestQueue(Trainers.this);
        String url = "http://bfitapi.bfit.co.in//api/alltrainers/show";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            pDialog.dismiss();
                            final JSONArray jsonarray = new JSONArray(response);
                            Log.d("jsonarray" ,"" +jsonarray.toString());



                            trainerAdapter = new TrainerAdapter(Trainers.this , jsonarray);
                            lvtrainer.setAdapter(trainerAdapter);


                            lvtrainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                                    Intent intent = new Intent(Trainers.this,TrainerDetail.class);

                                    try {
                                        intent.putExtra("trainerdetail", jsonarray.getJSONObject(i).toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                    startActivity(intent);

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();

                            new SweetAlertDialog(Trainers.this, SweetAlertDialog.ERROR_TYPE);
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

                new SweetAlertDialog(Trainers.this, SweetAlertDialog.ERROR_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Try Again !");
                pDialog.setCancelable(false);
                pDialog.show();
            }


        }) ;

        requestQueue.add(stringRequest);


    }
}


class TrainerAdapter extends BaseAdapter
{

    Activity context;
    JSONArray jsonArray;
    ImageLoader imageLoader;


    public TrainerAdapter( Activity context ,JSONArray jsonArray)
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
            v = inflater.inflate(R.layout.trainersxml, null, true);
            TextView trainername = (TextView) v.findViewById(R.id.trainername);
            TextView designation = (TextView) v.findViewById(R.id.designation);
            TextView experience = (TextView) v.findViewById(R.id.experience);

            NetworkImageView trainerimg = (NetworkImageView)v.findViewById(R.id.trainerimg);

            String photoURL = null;
            try {
                photoURL = jsonArray.getJSONObject(i).getString("Image");
                trainername.setText(jsonArray.getJSONObject(i).getString("Name"));
                designation.setText(jsonArray.getJSONObject(i).getString("Designation"));
                experience.setText(jsonArray.getJSONObject(i).getString("Experience")+" Years Experience");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            imageLoader = CustomVolleyRequest.getInstance(context.getApplicationContext())
                    .getImageLoader();
            imageLoader.get(photoURL, ImageLoader.getImageListener(trainerimg,
                    R.mipmap.ic_launcher, R.mipmap.ic_launcher));
            trainerimg.setImageUrl(photoURL, imageLoader);

        }

        return v;
    }
}