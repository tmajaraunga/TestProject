package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Response;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button buttonSendData;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSendData = findViewById(R.id.buttonSendData);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");

        buttonSendData.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  // put code here to send to Google Sheets
                                                  addDataGoogleSheets();
                                                  progressDialog.show();
                                              }
                                          }

        );

    }

    public void addDataGoogleSheets(){
         String sXvalues = xArray.getText().toString();
         String sYvalues = yArray.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbzAZCSWuRm23F762Ll7vNqyFl3HfvqSVyCgqOz1EIZCbaXpO3YaW8PZLmnl6RBA93vp1w/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                progressDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){

            }
        }){@Override
            protected Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("action", "addDataGoogleSheets");
            params.put("vXvalues", sXvalues);
            params.put("vYvalues", sYvalues);

            return params;
        }

        };

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}