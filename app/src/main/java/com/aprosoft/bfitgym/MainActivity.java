package com.aprosoft.bfitgym;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import com.facebook.ads.*;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements adsinterface, View.OnClickListener {


    AdView adView;

    TextView tvtodayclass,feedue;
    GridView gridView;
    RelativeLayout relativeLayout;
    JSONArray jsonArray;
    FeaturesAdapter adapter;
    Button btnupdate;
    JSONObject jsonObject;
    String ReceiptNo="",feedbackstring;

    ImageView profile,notification,feedback;
    LinearLayout adContainer;
  //  NetworkImageView adsimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



        adView = new AdView(this, "432550640612034_434427897090975", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
         adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        adView.loadAd();

        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Toast.makeText(MainActivity.this, "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });







        MyApplication myApplication = (MyApplication) getApplication();
        myApplication.calladsapi(this,this);






     //   adsimage=(NetworkImageView)findViewById(R.id.adsimage);
        relativeLayout =(RelativeLayout)findViewById(R.id.rellay1);
        gridView =(GridView)findViewById(R.id.gridview1);
        tvtodayclass = (TextView)findViewById(R.id.todayclass);
        feedue = (TextView)findViewById(R.id.feedue);
        btnupdate = (Button)findViewById(R.id.update);

        profile = (ImageView)findViewById(R.id.profile);
        notification = (ImageView)findViewById(R.id.notification);
        feedback = (ImageView)findViewById(R.id.feedback);



        SharedPreferences editor = getSharedPreferences("userlogin",MODE_PRIVATE);

        try {
            jsonObject = new JSONObject(editor.getString("data",""));
            ReceiptNo = jsonObject.getString("ReceiptNo");

        } catch (JSONException e) {
            e.printStackTrace();
        }




        if (!ReceiptNo.equalsIgnoreCase(""))
        {
            try {

                @SuppressLint("ResourceType") Animation a = AnimationUtils.loadAnimation(this, R.animator.textviewanimation);
                a.reset();

                feedue.setText("Fee due Date : " +jsonObject.getString("NextRenewalOn").split("T")[0]);

                feedue.startAnimation(a);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




        profile.setOnClickListener(this);

        notification.setOnClickListener(this);
  feedback.setOnClickListener(this);

        todayclass();


        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                todayclass();

            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todayclass();
            }
        });

        jsonArray = new JSONArray();
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("feature","Trainer");
            jsonObject.put("image",R.mipmap.trainer);
            jsonArray.put(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject jsonObject1= new JSONObject();
        try {
            jsonObject1.put("feature","Classes");
            jsonObject1.put("image",R.mipmap.classes);
            jsonArray.put(jsonObject1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject2= new JSONObject();
        try {
            jsonObject2.put("feature","Packages");
            jsonObject2.put("image",R.mipmap.packages);
            jsonArray.put(jsonObject2);

        } catch (JSONException e) {
            e.printStackTrace();
        }



        JSONObject jsonObject3= new JSONObject();
        try {
            jsonObject3.put("feature","Notifications");
            jsonObject3.put("image",R.mipmap.notify);
            jsonArray.put(jsonObject3);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject jsonObject4= new JSONObject();
        try {
            jsonObject4.put("feature","Body Measurement");
            jsonObject4.put("image",R.mipmap.bodymeasurement);
            jsonArray.put(jsonObject4);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject5= new JSONObject();
        try {
            jsonObject5.put("feature","View Measurement");
            jsonObject5.put("image",R.mipmap.measurementview);
            jsonArray.put(jsonObject5);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("jsonarray",jsonArray.toString());



        adapter = new FeaturesAdapter(MainActivity.this,jsonArray);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("i",""+i);
                if (i==0)
                {
                    Intent intent = new Intent(MainActivity.this,Trainers.class);
                    startActivity(intent);
                }

                if (i==1)
                {
                    Intent intent = new Intent(MainActivity.this,Classes.class);
                    startActivity(intent);
                }


                if (i==2)
                {
                    Intent intent = new Intent(MainActivity.this,Packages.class);
                    startActivity(intent);
                }


                if (i==3)
                {
                    Intent intent = new Intent(MainActivity.this,Notifications.class);
                    startActivity(intent);
                }
                if (i==4)
                {
                    Intent intent = new Intent(MainActivity.this,WeightMeasurement.class);
                    startActivity(intent);
                }

                if (i==5)
                {
                    Intent intent = new Intent(MainActivity.this,ViewMeasurement.class);
                    startActivity(intent);
                }
            }
        });

     //   gridView.setAdapter(jsonArray);
    }





    public void insertfeedback()
    {



        final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url ="http://bfitapi.bfit.co.in//api/addfeedback/add" ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        pDialog.dismiss();
                        Log.d("feedback response" ,"" +response.toString());


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "" + error);
                pDialog.dismiss();
            }


        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                HashMap<String, String> params = new HashMap<String, String>();



                params.put("ReceiptNo",ReceiptNo);
                params.put("feedback",feedbackstring);




                return params;
            }



            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }
        };
        requestQueue.add(stringRequest);
    }






    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }




    public void todayclass()
    {

        btnupdate.setText("Updating class schedule");

//        final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Loading");
//        pDialog.setCancelable(false);
//        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        String date = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()));

        Log.d("date",date);

        String url = "http://bfitapi.bfit.co.in//api/upcomingclass/show";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                         //   pDialog.dismiss();
                            final JSONArray jsonarray = new JSONArray(response);
                            Log.d("jsonarray" ,"" +jsonarray.toString());



                            btnupdate.setText("Tap to Update");









                            if (jsonarray.length()==0) {
                                tvtodayclass.setText("No upcoming class yet! We will notify you when a class is available");



                            } else {
                                String classString = "";
                                classString += jsonarray.getJSONObject(0).getString("class");
                                classString += " class ";
                                classString += "By "+jsonarray.getJSONObject(0).getString("trainer");
                                classString += " At "+ jsonarray.getJSONObject(0).getString("time");
                                tvtodayclass.setText(classString);
                            }








                        } catch (JSONException e) {
                                        e.printStackTrace();
//                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE);
//                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                            pDialog.setTitleText("Try Again !");
//                            pDialog.setCancelable(true);
//                            pDialog.show();
                                    }


                            }
                            //    Log.d("list", "" + list

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "" + error);
//                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE);
//                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                pDialog.setTitleText("Try Again !");
//                pDialog.setCancelable(true);
//                pDialog.show();
            }


        }) ;

        requestQueue.add(stringRequest);


    }

    @Override
    public void apiresponse(JSONArray adsjson) {


        Log.d("ads",adsjson.toString());
        ImageLoader imageLoader;

//        try {
//            String   photoURL = adsjson.getJSONObject(0).getString("imageurl");
//
//            imageLoader = CustomVolleyRequest.getInstance(getApplicationContext())
//                    .getImageLoader();
//            imageLoader.get(photoURL, ImageLoader.getImageListener(adsimage,
//                    R.mipmap.ic_launcher, R.mipmap.ic_launcher));
//            adsimage.setImageUrl(photoURL, imageLoader);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


    }

    @Override
    public void onClick(View view) {


        if(view.getId()==R.id.profile)
        {
            if (ReceiptNo.equalsIgnoreCase(""))
            {
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }

            else
            {
                Intent intent = new Intent(MainActivity.this,Profile.class);
                startActivity(intent);
            }
        }

        if(view.getId()==R.id.feedback)
        {
            if (ReceiptNo.equalsIgnoreCase(""))
            {
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }

            else
            {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("FeedBack Form");
              //  alertDialog.setMessage("Enter Feedback");

                final EditText input = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setHint("Your Feedback");
                alertDialog.setView(input);
                alertDialog.setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                feedbackstring = input.getText().toString();
                                if (feedbackstring.equalsIgnoreCase("")) {

                                        Toast.makeText(getApplicationContext(),
                                                "Insert Feedback", Toast.LENGTH_SHORT).show();

                                    } else {
                                       insertfeedback();
                                    }
                                }
                        });

                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }


        }

        if(view.getId()==R.id.notification)
        {
            Intent intent = new Intent(MainActivity.this,Notifications.class);
            startActivity(intent);
        }


    }
}




class FeaturesAdapter extends BaseAdapter
{

    Activity context;
    JSONArray jsonArray;


    public FeaturesAdapter( Activity context ,JSONArray jsonArray)
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
            v = inflater.inflate(R.layout.feauresxml, null, true);
            TextView itemname = (TextView) v.findViewById(R.id.itemname);

            ImageView image = (ImageView) v.findViewById(R.id.image);


            try {
                image.setImageResource(jsonArray.getJSONObject(i).getInt("image"));
                itemname.setText(jsonArray.getJSONObject(i).getString("feature"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return v;

    }

}

