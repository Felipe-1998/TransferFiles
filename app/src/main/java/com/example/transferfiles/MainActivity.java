package com.example.transferfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    Button servidor , cliente, formulario, leer, crear, ubicacion, wifiP2p;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        servidor = findViewById(R.id.servidor);
        cliente = findViewById(R.id.cliente);
        formulario=findViewById(R.id.formulario);
        leer = findViewById(R.id.leerArchivo);
        crear = findViewById(R.id.btnCreate);
        ubicacion = findViewById(R.id.btnLocation);
        wifiP2p=findViewById(R.id.btnWifiP2p);

        servidor.setOnClickListener(v ->  irAServidor());
        cliente.setOnClickListener(v -> irACliente());
        formulario.setOnClickListener(v -> irAFormulario());
        leer.setOnClickListener(v -> leerArchivo());
        crear.setOnClickListener(v -> crearArchivo());
        ubicacion.setOnClickListener(v -> obtenerUbicacion());
        wifiP2p.setOnClickListener(v -> irAReporte());
    }

    private void irAReporte() {
        Intent i = new Intent(this,ReporteWifiP2p.class);
        startActivity(i);
    }

    private void obtenerUbicacion() {
        Intent i = new Intent(this,Location.class);
        startActivity(i);
    }

    private void crearArchivo() {
        Intent i = new Intent(this,crearArchivo.class);
        startActivity(i);
    }

    private void leerArchivo() {
        Intent i = new Intent(this,LeerArchivo.class);
        startActivity(i);
    }

    private void irAFormulario() {
        Intent i = new Intent(this,Formulario.class);
        startActivity(i);
    }


    private void irAServidor() {
        Intent i = new Intent(this,Servidor.class);
        startActivity(i);
    }

    private void irACliente() {
        Intent i = new Intent(this,Cliente.class);
        startActivity(i);
    }
}