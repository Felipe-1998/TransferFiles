package com.example.transferfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class LeerArchivo extends AppCompatActivity {

    TextView tablero, tablero2,tablero3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_archivo);

        tablero = findViewById(R.id.tablero);
        tablero2 = findViewById(R.id.tablero2);
        tablero3 = findViewById(R.id.tablero3);

        unirArchivos();
        leerArchivo1();
        leerArchivo2();

    }

    @SuppressLint("SetTextI18n")
    private void unirArchivos() {

        String[] archivos = fileList();
        OutputStreamWriter miArchivo; //Manipular archivo
        FileInputStream fis1;
        FileInputStream fis2;
        String texto2;

        if (existe(archivos, "datosLocal.txt") && existe(archivos, "datosEntrada.txt")){
            try {
                fis1 = openFileInput("datosLocal.txt");
                BufferedReader bf1 = new BufferedReader(new InputStreamReader(fis1));
                String texto1 = bf1.readLine();

                fis2 = openFileInput("datosEntrada.txt");

                BufferedReader bf2 = new BufferedReader(new InputStreamReader(fis2));
                texto2 = bf2.readLine();

                miArchivo = new OutputStreamWriter(openFileOutput("myFile.txt",Context.MODE_PRIVATE));
                Toast.makeText(this,"Archivo Creado",Toast.LENGTH_SHORT).show();

                miArchivo.write(texto1+"\n");
                miArchivo.write(texto2+"\n");


                miArchivo.flush();
                miArchivo.close();

                tablero3.setText(texto1 + "\n" + texto2 + "\n");


            } catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"error"+ e,Toast.LENGTH_SHORT).show();
            }
        }
        else{
            try {
                fis1 = openFileInput("datosLocal.txt");
                BufferedReader bf1 = new BufferedReader(new InputStreamReader(fis1));
                String texto1 = bf1.readLine();

                miArchivo = new OutputStreamWriter(openFileOutput("myFile.txt",Context.MODE_PRIVATE));
                Toast.makeText(this,"Archivo Creado",Toast.LENGTH_SHORT).show();

                miArchivo.write(texto1+"\n");

                miArchivo.flush();
                miArchivo.close();

                tablero3.setText(texto1 + "\n");


            } catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"error"+ e,Toast.LENGTH_SHORT).show();
            }
        }


    }

    private boolean existe(String[] archivos, String archbusca) {
        for (int f = 0; f < archivos.length; f++){
            if (archbusca.equals(archivos[f])){
                return true;
            }
        }
        return false;
    }

    @SuppressLint("SetTextI18n")
    private void leerArchivo2() {
        FileInputStream fis;
        try{
            fis = openFileInput("datosEntrada.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
            String texto = bf.readLine();
            tablero2.setText(texto);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    public void leerArchivo1(){
        FileInputStream fis;
        try{
            fis = openFileInput("datosLocal.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
            String texto = bf.readLine();
            tablero.setText(texto);

        }catch (Exception e ){
            e.printStackTrace();
        }
    }

}