package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MyVolley extends AppCompatActivity {

    private static final String TAG = MyVolley.class.getName();
    private static final String REQUESTTAG = "string request first" ;
    Button sendRequest;
    TextView textView;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private String url = "http://www.mocky.io/v2/5c9b81b63600006e00d96ce9";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_volley);

        textView.findViewById(R.id.textViewVolley);
        sendRequest = findViewById(R.id.btnsendRequest);
        sendRequest.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View view) {

                sendRequestAndPrintResponse();
            }
        });
    }

    private void sendRequestAndPrintResponse() {
        requestQueue = Volley.newRequestQueue(this);


        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG,"Response : "+ response.toString());
                textView.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"Error : "+ error.toString());
                textView.setText(error.toString());
            }
        });

        stringRequest.setTag(REQUESTTAG);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
    if(requestQueue != null){
        requestQueue.cancelAll(REQUESTTAG);
    }
    }
}
