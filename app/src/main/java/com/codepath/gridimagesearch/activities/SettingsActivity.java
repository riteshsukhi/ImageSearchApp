package com.codepath.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.gridimagesearch.R;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String color = null;
    String size = null;
    String site = null;
    String type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner sizeSpinner = (Spinner) findViewById(R.id.spnSize);
        sizeSpinner.setOnItemSelectedListener(this);

        Spinner colorSpinner = (Spinner) findViewById(R.id.spnColor);
        colorSpinner.setOnItemSelectedListener(this);

        Spinner typeSpinner = (Spinner) findViewById(R.id.spnType);
        typeSpinner.setOnItemSelectedListener(this);

        EditText etSite = (EditText) findViewById(R.id.etSite);
        etSite.setText(etSite.getText());

    }




    /**
     * Shared listener to handle selections for all three spinner controls
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            // image size
            case R.id.spnSize:
                switch (position) {
                    case 0:
                        size = null;
                        break;
                    case 1:
                        size = "small";
                        break;
                    case 2:
                        size = "medium";
                        break;
                    case 3:
                        size = "large";
                        break;
                    case 4:
                        size = "xlarge";
                        break;
                }
                break;
            // image color
            case R.id.spnColor:
                switch (position) {
                    case 0:
                        color = null;
                        break;
                    case 1:
                        color = "black";
                        break;
                    case 2:
                        color = "white";
                        break;
                    case 3:
                        color = "blue";
                        break;
                    case 4:
                        color = "yellow";
                        break;

                }
                break;
            // image type
            case R.id.spnType:
                switch (position) {
                    case 0:
                        type = null;
                        break;
                    case 1:
                        type = "face";
                        break;
                    case 2:
                        type = "photo";
                        break;
                    case 3:
                        type = "clipart";
                        break;
                    case 4:
                        type = "lineart";
                        break;
                }
                break;
        }
    }


    public void onFinish(View v) {
        // store site string
        Intent data = new Intent();
        data.putExtra("size",size);
        data.putExtra("color",color);
        data.putExtra("type",type);
        data.putExtra("site",site);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
