package com.codepath.gridimagesearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);


        //extract the url that was passed
        //String url = getIntent().getStringExtra("url");
        ImageResult imageResult = (ImageResult) getIntent().getSerializableExtra("result");
        String url = imageResult.fullUrl;
                //find image view
        ImageView ivImage = (ImageView) findViewById(R.id.ivResult);
        //use Picassso to load the image
        Picasso.with(this).load(url).into(ivImage);



    }

}
