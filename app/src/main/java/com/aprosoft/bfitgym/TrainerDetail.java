package com.aprosoft.bfitgym;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class TrainerDetail extends AppCompatActivity {


    TextView experience,position,name,speciality,expertise;
    NetworkImageView trainerimg;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_detail);

      //  experience = (TextView)findViewById(R.id.experience);
        position = (TextView)findViewById(R.id.position);
        name = (TextView)findViewById(R.id.name);
        speciality = (TextView)findViewById(R.id.speciality);
        expertise = (TextView)findViewById(R.id.expertise);


        trainerimg = (NetworkImageView)findViewById(R.id.trainerimg);


        String trainerdetail = getIntent().getExtras().getString("trainerdetail");

        try {
            JSONObject jsonObject = new JSONObject(trainerdetail);

            String photoURL = null;
            try {
                photoURL = jsonObject.getString("Image");
             //   experience.setText(jsonObject.getString("Experience")+" Years of Experience");
                position.setText("("+jsonObject.getString("Designation")+")");
                name.setText("Mr."+jsonObject.getString("Name"));
                speciality.setText(jsonObject.getString("speciality"));
                expertise.setText(jsonObject.getString("expertise"));




                imageLoader = CustomVolleyRequest.getInstance(getApplicationContext())
                        .getImageLoader();
                imageLoader.get(photoURL, ImageLoader.getImageListener(trainerimg,
                        R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                trainerimg.setImageUrl(photoURL, imageLoader);



            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }





    }
}
