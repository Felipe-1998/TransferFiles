package com.example.transferfiles.controllers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class Crud {
    private String url;
    private RequestQueue queue;
    private JSONArray data;

    public Crud(String url, RequestQueue queue, JSONArray data) {
        this.url = url;
        this.queue = queue;
        this.data = data;
    }

    public void postReport(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, data,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("TAG", "Respuesta del servidor: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "Error en la petición: " + error.getMessage());
                    }
                }
        );

        queue.add(request);
    }
}
