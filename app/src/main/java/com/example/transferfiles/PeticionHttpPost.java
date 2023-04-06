package com.example.transferfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.transferfiles.controllers.ControllerDB;
import com.example.transferfiles.controllers.Crud;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PeticionHttpPost extends AppCompatActivity {

    TextView tvPetitionPost;
    TextView tvConexion;

    JSONArray jsonArray;
    JSONObject newAffected;

    int Count = 0;
    int status = 0;

    ControllerDB controllerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticion_http_post);
        //TextViews
        tvPetitionPost=findViewById(R.id.tvPetitionPOST);
        tvConexion=findViewById(R.id.tvConexion);
        //parametros json
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "https://firebase-api-ja2l.onrender.com/new-affected";
        String url = "http://192.168.1.6:3000/new-affected";
        jsonArray = new JSONArray();


        //Consultar base de datos
        BaseHelper conn;
        conn= new BaseHelper(this,"mibase",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] campos = new String[]{"Nombre","Edad","Lugar","Cedula","Genero","Rh","Enviado"};
        try{
            Cursor cursor = db.query("Reportes",campos,null,null,null,null,null);
            cursor.moveToFirst();
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                newAffected = new JSONObject();
                if(cursor.getInt(6)==0){
                    newAffected
                            .put("name", cursor.getString(0))
                            .put("age", cursor.getInt(1))
                            .put("location", cursor.getString(2))
                            .put("idCard", cursor.getString(3))
                            .put("gender",cursor.getString(4))
                            .put("rh", cursor.getString(5));
                    jsonArray.put(newAffected);
                }
            }
        }catch (Exception e){
            Toast.makeText(this,"Error" + e,Toast.LENGTH_LONG).show();
        }

        try {
            actualizarEstado( queue, url, jsonArray );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //initialWork(url,queue,jsonArray);
        iniciarHilo( queue, url, jsonArray );
    }

    private void actualizarEstado( RequestQueue queue, String url, JSONArray jsonArray ) throws JSONException {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        Count += 1;
        //int index = (jsonArray.length())-1;
        //JSONObject object = jsonArray.getJSONObject(index);
        //int status = object.getInt("Enviado");

        try{
            controllerDB = new ControllerDB(null,this);
            controllerDB.updateStatus();
            System.out.println("Holaaaaa...");
        }catch (Exception e){ System.out.println(e);}
        if(isConnected){
            tvConexion.setText("is connected");

            if((jsonArray.length()>0) && status==0) {
                Crud newReport = new Crud(url, queue, jsonArray);
                newReport.postReport();
                status=1;
            }
            tvPetitionPost.setText("Enviando json..." + Count);
        }else{
            tvConexion.setText("isn't connected");
        }
    }

    private void iniciarHilo( RequestQueue queue, String url, JSONArray jsonArray  ) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
               while (!Thread.interrupted()){
                   try{
                       Thread.sleep(5000);
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               try {
                                   actualizarEstado( queue, url, jsonArray );
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                       });
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
            }
        });
        t.start();
    }

    private void initialWork(boolean isConnected, String url, RequestQueue queue, JSONArray jsonArray ) {

    }
/*
    private void initialM(String url, RequestQueue queue, JSONArray jsonArray) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, jsonArray,
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
*/

/**
    private class ConnectionThread extends Thread {
        @Override
        public void run() {
            while(!isInterrupted()){
                try {
                    InetAddress address = InetAddress.getByName("www.google.com");
                    boolean isConneted = address != null && !address.equals("");
                    String status = isConneted ? "Connected" : "Not Connected";

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvConexion.setText(status);
                        }
                    });
                    Thread.sleep(5000);
                } catch (UnknownHostException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
**/
    /** private void initialM(String url, RequestQueue queue) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tvPetitionPost.setText("Response is" + response.substring(0,500));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvPetitionPost.setText("That didn't work!");
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", "Camilo Rodriguez");
                        params.put("age", "24");
                        params.put("country", "Colombia");
                        params.put("idCard", "963852741");
                        params.put("location", "3.44,-76.545");
                        params.put("birthday", "30-12-1998");
                        return params;
                    }
                };
        queue.add(stringRequest);
    } **/
}
