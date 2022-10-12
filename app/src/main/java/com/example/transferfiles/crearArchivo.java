package com.example.transferfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class crearArchivo extends AppCompatActivity {
    
    Button crear;
    private String archivo = "miarchivo";
    private String carpeta = "/archivos/";
    String contenido;
    public static File file;
    String file_path="";
    String name = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_archivo);
        
        crear = findViewById(R.id.btnCrearA);
        
        //crear.setOnClickListener(v -> crearFile());
        crearFile();
    }

    private void crearFile() {
        this.file_path = (Environment.getExternalStorageDirectory() + this.carpeta);
        File localFile = new File(this.file_path);
        Toast.makeText(this,"Path: " + file_path,Toast.LENGTH_LONG).show();
        if(!localFile.exists()){
            localFile.mkdir();
        }
        this.name = (this.archivo + ".txt");
        Toast.makeText(this, "Archivo: " + name, Toast.LENGTH_LONG).show();
        file = new File(localFile, this.name);
        try{
            file.createNewFile();
            Toast.makeText(this,"Se creo archivo 2",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}