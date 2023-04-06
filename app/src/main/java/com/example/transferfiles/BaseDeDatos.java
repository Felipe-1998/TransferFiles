package com.example.transferfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transferfiles.controllers.ControllerDB;

import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

public class BaseDeDatos extends AppCompatActivity {

    TextView tv_Base_datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_de_datos);
        tv_Base_datos=findViewById(R.id.tv_Base_datos);
        inicializar();
    }

    private void inicializar() {
        if(cantidadDeRegistros()==0){
            String[] texto = leerArchivo();
            ControllerDB controllerDB = new ControllerDB(texto,this);
            controllerDB.firstRegister();
            Toast.makeText(this,"Registros insertados!!!" + texto.length, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"La tabla ya estaba ok", Toast.LENGTH_SHORT).show();
        }
    }

    private void dataShow() {
        BaseHelper baseHelper = new BaseHelper(this, "mibase", null, 1);
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        String[] projection = {"Id", "Nombre", "Edad", "Lugar","Cedula","Genero","Rh"};
        Cursor cursor = db.query("Reportes", projection, null, null, null, null, null);
        String nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
        tv_Base_datos.setText(nombre);
        cursor.close();
        db.close();
    }

    private long cantidadDeRegistros() {
        BaseHelper baseHelper = new BaseHelper(this,"mibase",null,1);
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        long cn = DatabaseUtils.queryNumEntries(db,"Reportes");
        db.close();
        return cn;
    }

    private String[] leerArchivo() {
        InputStreamReader isr;
        ByteArrayOutputStream baos = null;
        try {
            isr = new InputStreamReader(openFileInput("datosSal.txt"));
            baos = new ByteArrayOutputStream();
            int i = isr.read();
            while (i != -1) {
                baos.write(i);
                i = isr.read();
            }
            Toast.makeText(this, i, Toast.LENGTH_SHORT).show();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toString().split("\n");
    }
}