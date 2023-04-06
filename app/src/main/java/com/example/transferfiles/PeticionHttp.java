package com.example.transferfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class PeticionHttp extends AppCompatActivity {

    TextView tvPeticionHttp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticion_http);
        tvPeticionHttp=findViewById(R.id.tvPetitionHttp);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://firebase-api-ja2l.onrender.com";
        initialM(url,queue);
        tvPeticionHttp.setText(url);
    }

    private void initialM(String url, RequestQueue queue) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tvPeticionHttp.setText("Response is" + response.substring(0,500));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvPeticionHttp.setText("That didn't work!");
                    }
                }
        );

        queue.add(stringRequest);
    }
}