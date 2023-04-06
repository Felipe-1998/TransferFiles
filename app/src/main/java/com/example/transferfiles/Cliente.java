package com.example.transferfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Cliente extends AppCompatActivity {

    EditText txtIp,txtPort,txtMsg;
    Button btnSend, btnRead;
    TextView tvData;

    public String ip,msg;
    public int port;

    private static DataOutputStream dos = null;
    private static DataInputStream dis = null;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        txtIp=findViewById(R.id.txtIp);
        txtPort=findViewById(R.id.txtPort);
        txtMsg=findViewById(R.id.txtMsg);

        btnSend=findViewById(R.id.btnSend);
        btnRead=findViewById(R.id.btnRead);

        tvData=findViewById(R.id.tvData);

        //Iniciar variables


        btnSend.setOnClickListener(v -> iniciarCliente());
        //btnRead.setOnClickListener(v -> leer());
    }

    /**
    private void leer() {
        try{
            BufferedReader bf = new BufferedReader(new InputStreamReader(openFileInput("datos1.txt")));
            String texto = bf.readLine();
            tvData.setText(texto);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }**/

    private void iniciarCliente() {

        ip = txtIp.getText().toString();
        port=Integer.parseInt(txtPort.getText().toString());
        msg=txtMsg.getText().toString();

        /**
        Thread t = new Thread(() -> {
            Socket s;
            try {
                s=new Socket(ip,port);
                //DataOutputStream dos=new DataOutputStream(s.getOutputStream());
                //dos.writeUTF(msg);
                //dos.flush();
                //dos.close();
                //String rutaSD = getBaseContext().getExternalCacheDir().getAbsolutePath();
                //File miCarpeta = new File(rutaSD,"MisDatos");

                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                try {
                    byte[] buffer = (byte[]) ois.readObject();
                    @SuppressLint("SdCardPath") FileOutputStream fos =
                            new FileOutputStream("datos.txt");
                    fos.write(buffer);
                    fos.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                s.close();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"error" + e,Toast.LENGTH_SHORT).show();
            }
        });
        t.start();
                    **/

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket s;
                try{
                    s = new Socket(ip,port);
                    dis = new DataInputStream(s.getInputStream());
                    dos = new DataOutputStream(s.getOutputStream());

                    //sendFile("datos.txt");
                    FileInputStream fis = openFileInput("datos.txt");
                    byte[] buffer = new byte[4 * 1024];
                    fis.read(buffer);
                    dos.write(buffer);

                    dos.flush();
                    dis.close();
                    dos.close();
                    s.close();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        t.start();

    }

    /**private void sendFile(String path) {
        try{
        int bytes = 0;
        File file = new File(String.valueOf(openFileInput(path)));
        FileInputStream fis = new FileInputStream(file);

        // send file size
        dos.writeLong(file.length());
        // break file into chunks
        byte[] buffer = new byte[4*1024];
        while ((bytes=fis.read(buffer))!=-1){
            dos.write(buffer,0,bytes);
            dos.flush();
        }
        fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }**/
}