package com.example.transferfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
public class Formulario extends AppCompatActivity {

    TextView tvObjecto;
    EditText txtNombre, txtEdad,txtUbicacion;
    Button btnSalve;
    String nombre, ubicacion;
    int edad;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        initialWork();
        listenerWork();

    }

    @SuppressLint("SetTextI18n")
    private void listenerWork() {

        txtUbicacion.setOnClickListener(v -> {
                    Ubicacion coordenadas = new Ubicacion(Formulario.this);
                    ubicacion=Ubicacion.ub;
                   if(ubicacion != null){
                        obtenerUbicacion();
                    }else{
                        Toast.makeText(getApplicationContext(),"Presione otra vez para obtener ubicacion",Toast.LENGTH_SHORT ).show();
                    }

                });

        btnSalve.setOnClickListener(v -> {

            nombre=txtNombre.getText().toString();
            edad=Integer.parseInt(txtEdad.getText().toString());
            Afectado afectado = new Afectado(nombre,ubicacion,edad );
            tvObjecto.setText("Nombre: " + afectado.getNombre() + "\n" +
                    "Edad: " + afectado.getEdad() + "\n" +
                    "Ubicacion: " + afectado.getLugar());

            OutputStreamWriter fichero = null;

            try{
                fichero = new OutputStreamWriter(openFileOutput("datosLocal.txt",Context.MODE_PRIVATE));
                fichero.write(afectado.getNombre()+";"+afectado.getEdad()+";" +afectado.getLugar());
                fichero.flush();
                fichero.close();
            }catch(Exception e){
                Toast.makeText(Formulario.this, "errores: " +e , Toast.LENGTH_SHORT).show();
            }finally{
                try {
                    assert fichero != null;
                    fichero.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    private void obtenerUbicacion() {
        txtUbicacion.setText(ubicacion);
    }

    private void initialWork() {
        //Intanciar
        tvObjecto=findViewById(R.id.tvObjecto);
        txtNombre=findViewById(R.id.txtNombre);
        txtEdad=findViewById(R.id.txtEdad);
        txtUbicacion=findViewById(R.id.txtUbicacion);
        btnSalve=findViewById(R.id.btnSalve);
    }
}