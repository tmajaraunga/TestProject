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
import java.util.concurrent.TimeUnit;

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
                  x = Double.parseDouble(editTextX.getText().toString());
                  y = Double.parseDouble(editTextY.getText().toString());
                  Log.d("CIS 4444", "Button Pressed with X = " + x + " and Y = " + y);
                  addDataGoogleSheets();
              }
          }
        );
    }

    public void addDataGoogleSheets() {

        String url = "https://script.google.com/macros/s/AKfycbw53dw4J4jvDAtqLL-sucMzxSz8DdxyGCrdfsSYWQGpRkOUtduFN_fZELhD3Uuj7xABsQ/exec";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    tvStatus.setText("Response is: " + response);
                    Log.d("CIS 4444", "Response Recieved: " + response);

                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvStatus.setText("That didn't work!");
                Log.d("CIS 4444", "Error Recieved: " + error);
                }
            }
        )
            {
            @Override
            protected Map<String, String> getParams() {
                Log.d("CIS 4444", "Params being set");
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "addDataGoogleSheets");
                params.put("x", x.toString());
                params.put("y", y.toString());
                return params;
            }
        };

        // Add the request to the RequestQueue.
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                (int) TimeUnit.SECONDS.toMillis(20),
//                0,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Instantiate the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}