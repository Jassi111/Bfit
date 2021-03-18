package com.aprosoft.bfitgym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class ClassDetail extends AppCompatActivity {


    TextView classname,trainername,duration,date,time,instructiontext;
    NetworkImageView trainerimg , ivimage;
    ImageLoader imageLoader , imageLoader1;
    String photoURL=null,photoURL1=null;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);


        classname = (TextView)findViewById(R.id.classname);
        trainername = (TextView)findViewById(R.id.trainername);
        duration = (TextView)findViewById(R.id.duration);
        date = (TextView)findViewById(R.id.date);
        time = (TextView)findViewById(R.id.time);
        instructiontext = (TextView)findViewById(R.id.instructiontext);


        trainerimg = (NetworkImageView)findViewById(R.id.trainerimg);
        ivimage = (NetworkImageView)findViewById(R.id.ivimage);

        try {
             jsonObject = new JSONObject(getIntent().getExtras().getString("classdetail"));

             photoURL = jsonObject.getJSONObject("Trainer").getString("Image");

            photoURL1 = jsonObject.getJSONObject("Class").getString("bannerImage");

             classname.setText(jsonObject.getJSONObject("Class").getString("ClassName"));
            trainername.setText("Trainer Name: "+jsonObject.getJSONObject("Trainer").getString("Name"));
            duration.setText("Duration: "+jsonObject.getString("Duration")+" minutes");
            date.setText("Date: "+jsonObject.getString("classDate"));
            time.setText("StartTime: "+jsonObject.getString("StartTime"));
            instructiontext.setText(jsonObject.getString("Instructions"));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (photoURL1!=null)

        {
            imageLoader1 = CustomVolleyRequest.getInstance(this.getApplicationContext())
                    .getImageLoader();
            imageLoader1.get(photoURL1, ImageLoader.getImageListener(ivimage,
                    R.mipmap.classes, R.mipmap.classes));
            ivimage.setImageUrl(photoURL1, imageLoader1);
        }

        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(photoURL, ImageLoader.getImageListener(trainerimg,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        trainerimg.setImageUrl(photoURL, imageLoader);


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
}
