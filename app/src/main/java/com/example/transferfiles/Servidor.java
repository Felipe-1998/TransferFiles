package com.example.transferfiles;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends AppCompatActivity {

    TextView txtSMS,txtStatus;
    TextView data;
    String cadena;

    private static DataOutputStream dos = null;
    private static DataInputStream dis = null;


    @SuppressLint({"MissingInflatedId", "SdCardPath", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servidor);

        txtSMS=findViewById(R.id.txtSMS);
        txtStatus=findViewById(R.id.txtStatus);
        data=findViewById(R.id.tvData);


        /**
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    ServerSocket ss = new ServerSocket(8000);
                    //Toast.makeText(Servidor.this, "Running...",Toast.LENGTH_SHORT).show();
                    txtStatus.setText("Running...");
                    while(true){
                        Socket s= ss.accept();
                        txtStatus.setText("accept");
                        //DataInputStream dis= new DataInputStream(s.getInputStream());
                        //txtSMS.setText(dis.readUTF());
                        //dis.close();
                        FileInputStream fis = new FileInputStream(String.valueOf(openFileInput("datos.txt")));
                        byte[] buffer = new byte[fis.available()];
                        fis.read(buffer);
                        fis.close();
                        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                        oos.writeObject(buffer);
                        oos.close();
                        s.close();

                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "error: " + e,Toast.LENGTH_SHORT).show();
                }

            }
        });
        t.start();
         **/

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket ss;
                try{
                    ss = new ServerSocket(8000);
                    //Toast.makeText(Servidor.this,"Listening port 8000", Toast.LENGTH_SHORT).show();
                    txtStatus.setText("Listening port 8000");
                    while(true){
                        Socket s = ss.accept();
                        //Toast.makeText(Servidor.this, "connection accepted",Toast.LENGTH_SHORT).show();
                        txtStatus.setText("connection accepted");
                        dis = new DataInputStream(s.getInputStream());
                        dos = new DataOutputStream(s.getOutputStream());

                        //receiveFile("datosEntrada.txt");

                        try{
                            byte[] buffer = new byte[4 * 1024];
                            dis.read(buffer);
                            FileOutputStream fos=openFileOutput("datosEntrada.txt",MODE_PRIVATE);
                            fos.write(buffer);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        dis.close();
                        dos.close();
                        s.close();
                    }


                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

        t.start();



    }
/**
    private void receiveFile(String fileName) {
        int bytes = 0;
        FileOutputStream fis;
        try {
            fis = new FileOutputStream(String.valueOf(openFileOutput(fileName,MODE_PRIVATE)));

            long size = dis.readLong();     // read file size
            byte[] buffer = new byte[4 * 1024];
            while (size > 0 && (bytes = dis.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                fis.write(buffer, 0, bytes);
                size -= bytes;      // read upto file size
            }
            fis.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    } **/
}