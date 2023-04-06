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
        //Instanciar elementos
        tablero = findViewById(R.id.tablero);
        tablero2 = findViewById(R.id.tablero2);
        tablero3 = findViewById(R.id.tablero3);
        //Iniciar metodos
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
        String texto1;
        String texto2;
        String texto="";

        if (existe(archivos, "datosLocal.txt") && existe(archivos, "datosEnt.txt")){
            try {
                fis1 = openFileInput("datosLocal.txt");
                BufferedReader bf1 = new BufferedReader(new InputStreamReader(fis1));
                texto1 = bf1.readLine();


                fis2 = openFileInput("datosEnt.txt");
                BufferedReader bf2 = new BufferedReader(new InputStreamReader(fis2));
                texto2 = bf2.readLine();
                while(texto2 != null){
                    texto = texto + texto2+"\n";
                    texto2=bf2.readLine();
                }

                miArchivo = new OutputStreamWriter(openFileOutput("datosSal.txt",Context.MODE_PRIVATE));
                Toast.makeText(this,"Archivo Creado",Toast.LENGTH_SHORT).show();


                miArchivo.write(texto1+"\n");
                miArchivo.write(texto+"\n");


                miArchivo.flush();
                miArchivo.close();


                tablero3.setText(texto1 + "\n" + texto + "\n");


            } catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"error"+ e,Toast.LENGTH_SHORT).show();
            }
        }
        else{
            try {
                fis1 = openFileInput("datosLocal.txt");
                BufferedReader bf1 = new BufferedReader(new InputStreamReader(fis1));
                texto1 = bf1.readLine();

                miArchivo = new OutputStreamWriter(openFileOutput("datosSal.txt",Context.MODE_PRIVATE));
                Toast.makeText(this,"Archivo ya existe",Toast.LENGTH_SHORT).show();

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
        String cadena;
        String texto = "";
        try{
            fis = openFileInput("datosEnt.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
            cadena = bf.readLine();
            while(cadena != null){
                texto= texto + cadena+"\n";
                cadena=bf.readLine();
            }
            fis.close();
            tablero2.setText(texto);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    public void leerArchivo1(){
        FileInputStream fis;
        String cadena;
        String texto="";
        try{
            //fis = openFileInput("datosLocal.txt");
            fis = openFileInput("datosSal.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
            cadena = bf.readLine();
            while(cadena != null){
                texto= texto + cadena+"\n";
                cadena=bf.readLine();
            }
            bf.close();
            fis.close();
            tablero.setText(texto);

        }catch (Exception e ){
            e.printStackTrace();
        }
    }

}