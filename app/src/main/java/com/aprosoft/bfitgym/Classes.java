package com.aprosoft.bfitgym;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

import net.mskurt.neveremptylistviewlibrary.NeverEmptyListView;

import org.json.JSONArray;
import org.json.JSONException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.internal.EmptyLoadChangeSet;

public class Classes extends AppCompatActivity {


    ListView lvclasses;
    ClassesAdapter classesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        lvclasses = (ListView)findViewById(R.id.lvclasses);

        classeslist();

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




    public void classeslist()
    {
        final SweetAlertDialog pDialog = new SweetAlertDialog(Classes.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


        RequestQueue requestQueue = Volley.newRequestQueue(Classes.this);
        String url = "http://bfitapi.bfit.co.in//api/allclasses/show";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            pDialog.dismiss();
                            final JSONArray jsonarray = new JSONArray(response);
                            Log.d("jsonarray classes" ,"" +jsonarray.toString());



                            classesAdapter = new ClassesAdapter(Classes.this , jsonarray);
                            lvclasses.setAdapter(classesAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();


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


class ClassesAdapter extends BaseAdapter
{

    Activity context;
    JSONArray jsonArray;
    ImageLoader imageLoader , imageLoader1;


    public ClassesAdapter( Activity context ,JSONArray jsonArray)
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
            v = inflater.inflate(R.layout.classesxml, null, true);
        }

            TextView trainername = (TextView) v.findViewById(R.id.trainername);
            TextView classname = (TextView) v.findViewById(R.id.classname);
            TextView starttime = (TextView) v.findViewById(R.id.starttime);
            TextView duration = (TextView) v.findViewById(R.id.duration);
            Button btnclk = (Button)v.findViewById(R.id.btnclk);
            Button btnclk1 = (Button)v.findViewById(R.id.btnclk1);
            NetworkImageView classimg = (NetworkImageView)v.findViewById(R.id.classimg);
            NetworkImageView img = (NetworkImageView)v.findViewById(R.id.img);

            String photoURL = null, photoURL1=null;
            try {

                Log.d("jsonarray length",""+jsonArray.length());
                Log.d("jsonarray length",""+jsonArray.toString());

                photoURL = jsonArray.getJSONObject(i).getString("imageurl");
                photoURL1 = jsonArray.getJSONObject(i).getString("trainerimage");

                trainername.setText("Trainer: "+jsonArray.getJSONObject(i).getString("trainer"));
                starttime.setText("Day :"+jsonArray.getJSONObject(i).getString("day")+" Time: "+jsonArray.getJSONObject(i).getString("time"));
                duration.setText("Duration: "+jsonArray.getJSONObject(i).getString("duration"));
                classname.setText(jsonArray.getJSONObject(i).getString("class"));

                imageLoader = CustomVolleyRequest.getInstance(context.getApplicationContext())
                        .getImageLoader();
                imageLoader.get(photoURL, ImageLoader.getImageListener(classimg,
                        R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                classimg.setImageUrl(photoURL, imageLoader);

                imageLoader1 = CustomVolleyRequest.getInstance(context.getApplicationContext())
                        .getImageLoader();
                imageLoader1.get(photoURL1, ImageLoader.getImageListener(img,
                        R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                img.setImageUrl(photoURL1, imageLoader1);
                btnclk.setTag(i);
                btnclk1.setTag(i);

                btnclk1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent intent = new Intent(context, ClassDetail.class);
                        try {
                            intent.putExtra("classdetail",jsonArray.getJSONObject((Integer) view.getTag()).toString());

                            context.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

                btnclk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent intent = new Intent(context, ClassDetail.class);
                        try {
                            intent.putExtra("classdetail",jsonArray.getJSONObject((Integer) view.getTag()).toString());

                            context.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }






        return v;
    }
}