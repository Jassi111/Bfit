
package com.aprosoft.bfitgym;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class Forgotpassword extends AppCompatActivity implements View.OnClickListener {


    EditText oldpassword,newpassword,confirmpassword,id;
    Button changepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);


        id = (EditText)findViewById(R.id.id);
        oldpassword = (EditText)findViewById(R.id.oldpassword);
          newpassword= (EditText)findViewById(R.id.newpassword);
        confirmpassword = (EditText)findViewById(R.id.confirmpassword);

        changepassword = (Button)findViewById(R.id.changepassword);

        changepassword.setOnClickListener(this);


    }

    @Override
    public void onClick(final View view) {

        if (view.getId()==R.id.changepassword)
        {
            if (id.getText().toString().equalsIgnoreCase(""))
            {
                Snackbar.make(view,"Enter Registration No.",Snackbar.LENGTH_LONG).show();
                return;
            }
       if (oldpassword.getText().toString().equalsIgnoreCase(""))
            {
                Snackbar.make(view,"Incorrect Old Password",Snackbar.LENGTH_LONG).show();
                return;
            }

            if (newpassword.getText().toString().equalsIgnoreCase(""))
            {
                Snackbar.make(view,"Fill New Password",Snackbar.LENGTH_LONG).show();
                return;
            }

            if (!newpassword.getText().toString().equals(confirmpassword.getText().toString()))
            {
                Snackbar.make(view,"New Passwords are not same",Snackbar.LENGTH_LONG).show();
                return;
            }



            final SweetAlertDialog pDialog = new SweetAlertDialog(Forgotpassword.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(Forgotpassword.this);
            String url ="http://bfitapi.bfit.co.in//api/changepassword/change" ;
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


                                    Intent intent = new Intent(Forgotpassword.this,MainActivity.class);
                                    startActivity(intent);
                                    Forgotpassword.this.finish();

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
                    params.put("password",oldpassword.getText().toString());
                    params.put("newpassword",newpassword.getText().toString());




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
}
