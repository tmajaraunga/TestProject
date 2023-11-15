package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button buttonSendData;
    ProgressDialog progressDialog;
    EditText editTextX, editTextY;
    Double x = 0.0;
    Double y = 0.0;
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextX = findViewById(R.id.editTextX);
        editTextY = findViewById(R.id.editTextY);
        tvStatus = findViewById(R.id.textViewStatus);

        // Set up Button
        buttonSendData = findViewById(R.id.buttonSendData);
        buttonSendData.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  // put code here to send to Google Sheets
                  x =  Double.parseDouble(editTextX.getText().toString());
                  y =  Double.parseDouble(editTextY.getText().toString());
                  Log.d("CIS 4444", "Button Pressed with X = "+x+" and Y = "+y);
                  addDataGoogleSheets();
              }
          }
        );
    }

    public void addDataGoogleSheets() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://script.google.com/macros/s/AKfycbzAZCSWuRm23F762Ll7vNqyFl3HfvqSVyCgqOz1EIZCbaXpO3YaW8PZLmnl6RBA93vp1w/exec";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        tvStatus.setText("Response is: " + response.substring(0, 500));
                        Log.d("CIS 4444", "Response Recieved");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvStatus.setText("That didn't work!");
                Log.d("CIS 4444", "Error Recieved: "+error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Log.d("CIS 4444", "Params being set");
                Map<String, String> params = new HashMap<>();
                params.put("action", "addDataGoogleSheets");
                params.put("vXvalues", x.toString());
                params.put("vYvalues", y.toString());
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}