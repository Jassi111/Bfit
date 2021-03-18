package com.aprosoft.bfitgym;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity implements View.OnClickListener {


    EditText id,password;
    Button login,forgotpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        id = (EditText)findViewById(R.id.id);
        password = (EditText)findViewById(R.id.password);

        login = (Button)findViewById(R.id.login);
        forgotpassword = (Button)findViewById(R.id.forgotpassword);


        login.setOnClickListener(this);
        forgotpassword.setOnClickListener(this);


    }

    @Override
    public void onClick(final View view) {

        if (view.getId()==R.id.login)
        {

            if (id.getText().toString().equalsIgnoreCase(""))
            {
                Snackbar.make(view,"Enter Registration No.",Snackbar.LENGTH_LONG).show();
                return;
            }

            if (password.getText().toString().equalsIgnoreCase(""))
            {
                Snackbar.make(view,"Enter Password",Snackbar.LENGTH_LONG).show();
                return;
            }



            final SweetAlertDialog pDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            String url ="http://bfitapi.bfit.co.in//api/logineuser/login" ;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            pDialog.dismiss();
                            Log.d("Login result", "" + response.toString());

                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                if (jsonArray.getJSONObject(0).getString("Msg").equalsIgnoreCase("success"))
                                {

                                    SharedPreferences.Editor editor = getSharedPreferences("userlogin",MODE_PRIVATE).edit();
                                    editor.putString("data",jsonArray.getJSONObject(0).toString());
                                    editor.apply();


                                    Intent intent = new Intent(Login.this,MainActivity.class);
                                    startActivity(intent);
                                    Login.this.finish();

                                }

                                else
                                {
                                    Snackbar.make(view,"Incorrect Registration No. or Passord",Snackbar.LENGTH_LONG).show();

                                }


                            } catch (JSONException e) {
                                pDialog.dismiss();
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



                    params.put("ReceiptNo",id.getText().toString());
                    params.put("password",password.getText().toString());




                    return params;
                }



                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded";
                }
            };
            requestQueue.add(stringRequest);

        }


        if (view.getId()==R.id.forgotpassword)
        {

            Intent intent = new Intent(Login.this,Forgotpassword.class);
            startActivity(intent);

        }


    }
}
