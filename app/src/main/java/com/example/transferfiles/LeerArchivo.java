package com.example.transferfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

public class LeerArchivo extends AppCompatActivity {

    TextView tablero, tablero2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_archivo);

        tablero = findViewById(R.id.tablero);
        tablero2 = findViewById(R.id.tablero2);
        //unirArchivos();
        leerArchivo1();
        leerArchivo2();

    }

    @SuppressLint("SetTextI18n")
    private void leerArchivo2() {
        FileInputStream fis = null;
        Afectado a;

        FileInputStream fis2 = null;
        Afectado a2;

        try{
            fis = openFileInput("datosLocal.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            a = (Afectado) ois.readObject();
            /**tablero2.setText("Nombre: " + a.getNombre() +"\n" +
                    "Edad: " + a.getEdad() +"\n" +
                     "Ubicacion: " + a.getLugar() );**/

            fis2 = openFileInput("datosEntrada.txt");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            a2 = (Afectado) ois2.readObject();
            //BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
            //String texto = bf.readLine();
            String interno = a.getNombre()+";"+a.getEdad()+";"+a.getLugar();
            String externo = a2.getNombre()+";"+a2.getEdad()+";"+a2.getLugar();

            tablero2.setText(interno + "\n" + externo);

            //tablero2.setText(a.toString() + "\n" + a2.toString());
            //String texto1 = a.getNombre()+","+a.getEdad()+","+a.getLugar();
            //String texto2 = a2.getNombre()+","+a2.getEdad()+","+a.getLugar();
            //tablero2.setText(texto1 + "\n" +   texto2);

        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    public void leerArchivo1(){
        FileInputStream fis;
        Afectado a;
        try{
            fis = openFileInput("datosLocal.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            a = (Afectado) ois.readObject();
            //BufferedReader bf = new BufferedReader(new InputStreamReader(ois));
            //String texto = bf.readLine();
            /**
            tablero.setText("Nombre: " + a.getNombre() +"\n" +"Edad: " + a.getEdad() +"\n" +"Ubicacion: " + a.getLugar() );**/
            String interno = a.getNombre()+","+a.getEdad()+","+a.getLugar();
            tablero.setText(interno);

        }catch (Exception e ){
            e.printStackTrace();
        }
    }

}