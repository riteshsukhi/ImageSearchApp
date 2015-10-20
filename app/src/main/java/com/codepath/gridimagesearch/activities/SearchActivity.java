package com.codepath.gridimagesearch.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.codepath.gridimagesearch.EndlessScrollListener;
import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.adapters.ImageResultsAdapter;
import com.codepath.gridimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    private static final int FILTER = 20 ;
    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    String color = null;
    String size = null;
    String site = null;
    String type = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onComposeAction(MenuItem mi) {
        // handle click here
        Intent i = new Intent(this,SettingsActivity.class);
        startActivityForResult(i, FILTER);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( RESULT_OK == resultCode && FILTER == requestCode ) {
            color = data.getExtras().getString("color");
            size = data.getExtras().getString("size");
            type = data.getExtras().getString("type");
            site = data.getExtras().getString("site");

            // re-run image search
            onImageSearch(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        //Creates the data source
        imageResults = new ArrayList<ImageResult>();
        //Attaches the data source to an adapter
        aImageResults = new ImageResultsAdapter(this, imageResults);
        //Link the adapter to adapterview(gridview)
        gvResults.setAdapter(aImageResults);



        // Attach the listener to the AdapterView onCreate
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });


        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Create an intent to display the full screen image
                Intent i = new Intent(getApplicationContext(),ImageDisplayActivity.class);
                //Get the image to display
                ImageResult imageResult = imageResults.get(position);
                //Pass image result to the intent
                i.putExtra("result",imageResult);
                //Launch the activity
                startActivity(i);

            }
        });
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter

        String query = etQuery.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        // https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=monkey
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8" + "&start=" + offset;
        if ( null != size) searchUrl.concat("&imgsz=" + size);
        if ( null != color ) searchUrl.concat("&imgcolor=" + color);
        if ( null != type ) searchUrl.concat("&imgtype=" + type);
        if ( null != site ) searchUrl.concat("&as_sitesearch=" + Uri.encode(site));
        client.get(searchUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    //imageResults.clear();//clear the existing images from the array(in cases where its a new search)
                    //when you make changes to the adapter, it modifies the underlying data
                    aImageResults.addAll(ImageResult.formJSONArray(imageResultsJson));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
    }

    //Fired when button is pressed
    public void onImageSearch(View  v){
        String query = etQuery.getText().toString();
       AsyncHttpClient client = new AsyncHttpClient();
        // https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=monkey
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
        if ( null != size) searchUrl =  searchUrl.concat("&imgsz=" + size);
        if ( null != color ) searchUrl = searchUrl.concat("&imgcolor=" + color);
        if ( null != type )searchUrl =  searchUrl.concat("&imgtype=" + type);
        if ( null != site ) searchUrl = searchUrl.concat("&as_sitesearch=" + Uri.encode(site));
        client.get(searchUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();//clear the existing images from the array(in cases where its a new search)
                    //when you make changes to the adapter, it modifies the underlying data
                    aImageResults.addAll(ImageResult.formJSONArray(imageResultsJson));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
