package com.aprosoft.bfitgym;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;

public class WeightMeasurement extends AppCompatActivity {

    EditText weight,neck,shoulder,chest,biceps,waist,hips,thighs,calves,date,duedate;
    Button submit;
    private int mYear, mMonth, mDay;
    String  date1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_measurement);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        weight = (EditText)findViewById(R.id.weight);
        neck = (EditText)findViewById(R.id.neck);
        shoulder = (EditText)findViewById(R.id.shoulder);
        chest = (EditText)findViewById(R.id.chest);
        biceps = (EditText)findViewById(R.id.biceps);
        waist = (EditText)findViewById(R.id.waist);
        hips = (EditText)findViewById(R.id.hips);
        thighs = (EditText)findViewById(R.id.thigh);
        calves = (EditText)findViewById(R.id.calves);
        date = (EditText)findViewById(R.id.date);
        duedate = (EditText)findViewById(R.id.duedate);







        submit = (Button) findViewById(R.id.submit);



        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        date.setText(formattedDate);





        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);







                DatePickerDialog datePickerDialog = new DatePickerDialog(WeightMeasurement.this,
                        new DatePickerDialog.OnDateSetListener() {




                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {




                                if (dayOfMonth<10 && monthOfYear<10)
                                {
                                    date1 =   "0"+dayOfMonth + "-0" + (monthOfYear + 1)  + "-" +year;

                                }

                                else if (dayOfMonth<10)
                                {
                                    date1 =   "0"+dayOfMonth + "-" + (monthOfYear + 1)  + "-" +year;

                                }
                                else if (monthOfYear<10)
                                {
                                    date1 =   dayOfMonth + "-0" + (monthOfYear + 1)  + "-" +year;

                                }
                                else
                                {
                                    date1 =   dayOfMonth + "-" + (monthOfYear + 1)  + "-" +year;

                                }

                                date.setText(date1);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();




            }
        });




        duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);




                DatePickerDialog datePickerDialog = new DatePickerDialog(WeightMeasurement.this,
                        new DatePickerDialog.OnDateSetListener() {




                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                if (dayOfMonth<10 && monthOfYear<10)
                                {
                                    date1 =   "0"+dayOfMonth + "-0" + (monthOfYear + 1)  + "-" +year;

                                }

                                else if (dayOfMonth<10)
                                {
                                    date1 =   "0"+dayOfMonth + "-" + (monthOfYear + 1)  + "-" +year;

                                }
                                else if (monthOfYear<10)
                                {
                                    date1 =   dayOfMonth + "-0" + (monthOfYear + 1)  + "-" +year;

                                }
                                else
                                {
                                    date1 =   dayOfMonth + "-" + (monthOfYear + 1)  + "-" +year;

                                }
                                duedate.setText(date1);


                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();



            }
        });





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(weight.getText().toString().equalsIgnoreCase(""))
                {
                    Snackbar.make(view,"Fill all the fields",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(neck.getText().toString().equalsIgnoreCase(""))
                {
                    Snackbar.make(view,"Fill all the fields",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(shoulder.getText().toString().equalsIgnoreCase(""))
                {
                    Snackbar.make(view,"Fill all the fields",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(chest.getText().toString().equalsIgnoreCase(""))
                {
                    Snackbar.make(view,"Fill all the fields",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(biceps.getText().toString().equalsIgnoreCase(""))
                {
                    Snackbar.make(view,"Fill all the fields",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(thighs.getText().toString().equalsIgnoreCase(""))
                {
                    Snackbar.make(view,"Fill all the fields",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(hips.getText().toString().equalsIgnoreCase(""))
                {
                    Snackbar.make(view,"Fill all the fields",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(waist.getText().toString().equalsIgnoreCase(""))
                {
                    Snackbar.make(view,"Fill all the fields",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(duedate.getText().toString().equalsIgnoreCase(""))
                {
                    Snackbar.make(view,"Fill all the fields",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(calves.getText().toString().equalsIgnoreCase(""))
                {
                    Snackbar.make(view,"Fill all the fields",Snackbar.LENGTH_LONG).show();
                    return;
                }

                Realm.init(WeightMeasurement.this);
// Get a Realm instance for this thread
                Realm realm = Realm.getDefaultInstance();
                // Persist your data in a transaction
                realm.beginTransaction();



                WeighmeasurementObject measurementObject = realm.createObject(WeighmeasurementObject.class);
                measurementObject.setWeight(weight.getText().toString());

                measurementObject.setShoulder(shoulder.getText().toString());

                measurementObject.setNeck(neck.getText().toString());

                measurementObject.setChest(chest.getText().toString());

                measurementObject.setBiceps(biceps.getText().toString());

                measurementObject.setWaist(waist.getText().toString());

                measurementObject.setHips(hips.getText().toString());

                measurementObject.setThighs(thighs.getText().toString());

                measurementObject.setCalves(calves.getText().toString());

                measurementObject.setDate(date.getText().toString());

                measurementObject.setDuedate(duedate.getText().toString());


                Log.d("Measurement info", "data inserted successfully");



                realm.commitTransaction();


                SweetAlertDialog pDialog =  new SweetAlertDialog(WeightMeasurement.this, SweetAlertDialog.SUCCESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Success !");
                pDialog.setContentText("Measurement Added Successfully");
                pDialog.setConfirmText("ok");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent intent = new Intent(WeightMeasurement.this,MainActivity.class);
                        startActivity(intent);

                        sweetAlertDialog.dismiss();
                    }
                });
                pDialog.show();




            }
        });

    }
}
