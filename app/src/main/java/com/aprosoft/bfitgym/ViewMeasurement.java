package com.aprosoft.bfitgym;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ViewMeasurement extends AppCompatActivity {


    TextView norecord,previousshoulder,currentshoulder,previousweight,currentweight,previousneck,currentneck,previouschest,currentchest,previousbiceps,currentbiceps,
            previouswaist,currentwaist,previouships,currenthips,previousthigh,currentthigh,previouscalves,currentcalves;
    List<String> list;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    JSONArray jsonarray1;
    TextView spinnerdate,lastdate,diffweight,diffshoulder,diffneck,diffchest,diffbiceps,diffwaist,diffhips,diffthigh,diffcalves;


    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_measurement);

        norecord = (TextView) findViewById(R.id.norecord);
        previousshoulder = (TextView) findViewById(R.id.previousshoulder);
        currentshoulder = (TextView) findViewById(R.id.currentshoulder);
        previousweight = (TextView) findViewById(R.id.previousweight);
        currentweight = (TextView) findViewById(R.id.currentweight);
        previousneck = (TextView) findViewById(R.id.previousneck);
        currentneck = (TextView) findViewById(R.id.currentneck);
        previouschest = (TextView) findViewById(R.id.previouschest);
        currentchest = (TextView) findViewById(R.id.currentchest);
        previousbiceps = (TextView) findViewById(R.id.previousbiceps);
        currentbiceps = (TextView) findViewById(R.id.currentbiceps);
        previouswaist = (TextView) findViewById(R.id.previouswaist);
        currentwaist = (TextView) findViewById(R.id.currentwaist);
        previouships = (TextView) findViewById(R.id.previouships);
        currenthips = (TextView) findViewById(R.id.currenthips);
        previousthigh = (TextView) findViewById(R.id.previousthigh);
        currentthigh = (TextView) findViewById(R.id.currentthigh);
        previouscalves = (TextView) findViewById(R.id.previouscalves);
        currentcalves = (TextView) findViewById(R.id.currentcalves);



        diffshoulder = (TextView) findViewById(R.id.diffshoulder);
        diffweight = (TextView) findViewById(R.id.diffweight);
        diffneck = (TextView) findViewById(R.id.diffneck);
        diffchest = (TextView) findViewById(R.id.diffchest);
        diffbiceps = (TextView) findViewById(R.id.diffbiceps);
        diffwaist = (TextView) findViewById(R.id.diffwaist);
        diffthigh = (TextView) findViewById(R.id.diffthigh);
        diffhips = (TextView) findViewById(R.id.diffhips);
        diffcalves = (TextView) findViewById(R.id.diffcalves);




        spinnerdate = (TextView) findViewById(R.id.spinnerdate);
        lastdate = (TextView) findViewById(R.id.lastdate);
        spinner = (Spinner)findViewById(R.id.spinner);








        list = new ArrayList<String>();

       list.add(0,"select date");



        viewmeasurement();
        allmeasurement();


    }





    public void viewmeasurement()
    {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ViewMeasurement.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


        RequestQueue requestQueue = Volley.newRequestQueue(ViewMeasurement.this);
        String url = "http://bfitapi.bfit.co.in//api/currentmeasurement/show";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            pDialog.dismiss();
                            final JSONArray jsonarray = new JSONArray(response);
                            Log.d("current measurement" ,"" +jsonarray.toString());




                            currentweight.setText(jsonarray.getJSONObject(0).getString("weight"));
                            currentshoulder.setText(jsonarray.getJSONObject(0).getString("shoulder"));
                            currentneck.setText(jsonarray.getJSONObject(0).getString("neck"));
                            currentchest.setText(jsonarray.getJSONObject(0).getString("chest"));
                            currentbiceps.setText(jsonarray.getJSONObject(0).getString("biceps"));
                            currentthigh.setText(jsonarray.getJSONObject(0).getString("thighs"));
                            currentcalves.setText(jsonarray.getJSONObject(0).getString("calves"));
                            currentwaist.setText(jsonarray.getJSONObject(0).getString("waist"));
                            currenthips.setText(jsonarray.getJSONObject(0).getString("hips"));
                            lastdate.setText(jsonarray.getJSONObject(0).getString("date"));





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



    public void allmeasurement()
    {



        final SweetAlertDialog pDialog = new SweetAlertDialog(ViewMeasurement.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(ViewMeasurement.this);
        String url ="http://bfitapi.bfit.co.in//api/showmeasurement/show" ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        pDialog.dismiss();
                        Log.d("all measurements" ,"" +response.toString());

                        try {

                            jsonarray1 = new JSONArray(response);

                            Log.d("all measurements" ,"" +jsonarray1.toString());

                            for (int i=0;i<jsonarray1.length();i++)
                            {
                                list.add(jsonarray1.getJSONObject(i).getString("date"));
                            }
                            adapter = new ArrayAdapter<String>(getApplicationContext(),

                                    android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                    date = spinner.getSelectedItem().toString();
                    spinnerdate.setText(spinner.getSelectedItem().toString());


                    Log.d("here jsonarroay1",""+jsonarray1.length());
                    Log.d("value of i is",""+i);

                    if (i==0)
                    {
                        Snackbar.make(view,"Select Date",Snackbar.LENGTH_LONG).show();
                        return;
                    }


                try {
                    previousweight.setText(jsonarray1.getJSONObject(i-1).getString("weight"));
                    previousneck.setText( jsonarray1.getJSONObject(i-1).getString("neck"));
                    previousshoulder.setText( jsonarray1.getJSONObject(i-1).getString("shoulder"));
                    previouschest.setText( jsonarray1.getJSONObject(i-1).getString("chest"));
                    previousbiceps.setText( jsonarray1.getJSONObject(i-1).getString("biceps"));
                    previouswaist.setText( jsonarray1.getJSONObject(i-1).getString("waist"));
                    previouships.setText( jsonarray1.getJSONObject(i-1).getString("hips"));
                    previousthigh.setText( jsonarray1.getJSONObject(i-1).getString("thighs"));
                    previouscalves.setText( jsonarray1.getJSONObject(i-1).getString("calves"));



                    diffweight.setText(String.valueOf(Float.parseFloat(currentweight.getText().toString()) - Float.parseFloat(previousweight.getText().toString())));
                    diffshoulder.setText(String.valueOf(Float.parseFloat(currentshoulder.getText().toString()) - Float.parseFloat(previousshoulder.getText().toString())));
                    diffneck.setText(String.valueOf(Float.parseFloat(currentneck.getText().toString()) - Float.parseFloat(previousneck.getText().toString())));
                    diffchest.setText(String.valueOf(Float.parseFloat(currentchest.getText().toString()) - Float.parseFloat(previouschest.getText().toString())));
                    diffbiceps.setText(String.valueOf(Float.parseFloat(currentbiceps.getText().toString()) - Float.parseFloat(previousbiceps.getText().toString())));
                    diffwaist.setText(String.valueOf(Float.parseFloat(currentwaist.getText().toString()) - Float.parseFloat(previouswaist.getText().toString())));
                    diffthigh.setText(String.valueOf(Float.parseFloat(currentthigh.getText().toString()) - Float.parseFloat(previousthigh.getText().toString())));
                    diffhips.setText(String.valueOf(Float.parseFloat(currenthips.getText().toString()) - Float.parseFloat(previouships.getText().toString())));
                    diffcalves.setText(String.valueOf(Float.parseFloat(currentcalves.getText().toString()) - Float.parseFloat(previouscalves.getText().toString())));



                } catch (JSONException e) {
                    e.printStackTrace();
                }







            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }








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



                params.put("userid","1");




                return params;
            }



            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }
        };
        requestQueue.add(stringRequest);
    }

}
