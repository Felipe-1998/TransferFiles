package com.example.transferfiles;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ReporteWifiP2p extends AppCompatActivity {

    Button btnOnOff, btnDiscover, btnSend;
    ListView listView;
    TextView read_msg_box, connectionStatus;
    EditText writeMsg;

    WifiManager wifiManager;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;

    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;

    List<WifiP2pDevice> peers = new ArrayList<>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;

    ServerClass serverClass;
    ClienteClass clienteClass;
    //SendReceive sendReceive;

    private static DataOutputStream dos = null;
    private static DataInputStream dis = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_wifi_p2p);

        InitialWork();
        verificarPermisos();


    }

    private void verificarPermisos() {
        int Permiso1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int Permiso2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (Permiso1 == PackageManager.PERMISSION_GRANTED && Permiso2 == PackageManager.PERMISSION_GRANTED) {
            exqListener();
            Toast.makeText(this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }

        }
    }


    /**
    Handler handler = new Handler(new Handler.Callback() {
        @ O verride //unir
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == MESSAGE_READ) {
                byte[] readBuff = (byte[]) msg.obj;
                String tempMsg = new String(readBuff, 0, msg.arg1);
                read_msg_box.setText(tempMsg);
            }
            return true;
        } //se hicieron cambios con el original
    });
     **/

    private void exqListener() {
        btnOnOff.setOnClickListener(v -> wifiManager.setWifiEnabled(!wifiManager.isWifiEnabled()));

        btnDiscover.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSuccess() {
                    connectionStatus.setText("Discovery Started");
                    Toast.makeText(getApplicationContext(), "Discovery Started", Toast.LENGTH_SHORT).show();
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onFailure(int i) {
                    connectionStatus.setText("Discovery Starting Failed");
                    Toast.makeText(getApplicationContext(), "Discovery Started", Toast.LENGTH_SHORT).show();
                }
            });
        });

        listView.setOnItemClickListener((parent, view, i, l) -> {
            final WifiP2pDevice device = deviceArray[i];
            WifiP2pConfig config = new WifiP2pConfig();
            config.deviceAddress = device.deviceAddress;

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(),"Connected to " +device.deviceName,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int i) {
                    Toast.makeText(getApplicationContext(),"Not Connected",Toast.LENGTH_SHORT).show();
                }
            });

        });

        btnSend.setOnClickListener(v -> {
            //String msg=writeMsg.getText().toString();
            //sendReceive.write(msg.getBytes(StandardCharsets.UTF_8));

        });
    }

    private void InitialWork() {
        btnOnOff=(Button) findViewById(R.id.onOff);
        btnDiscover=(Button) findViewById(R.id.discover);
        btnSend=(Button) findViewById(R.id.sendButton);
        listView=(ListView) findViewById(R.id.peerListView);
        read_msg_box=(TextView) findViewById(R.id.readMSG);
        connectionStatus=(TextView) findViewById(R.id.connectionStatus);
        writeMsg=(EditText) findViewById(R.id.writeMsg);

        wifiManager=(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mManager=(WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel=mManager.initialize(this,getMainLooper(),null);
        mReceiver=new WifiDirectBroadcastReceiver(mManager,mChannel,this);
        mIntentFilter=new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    WifiP2pManager.PeerListListener peerListListener=new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if(!peerList.getDeviceList().equals(peers)){

                peers.clear();
                peers.addAll(peerList.getDeviceList());

                deviceNameArray=new String[peerList.getDeviceList().size()];
                deviceArray=new WifiP2pDevice[peerList.getDeviceList().size()];
                int index=0;

                for(WifiP2pDevice device : peerList.getDeviceList()){
                    deviceNameArray[index]=device.deviceName;
                    deviceArray[index]=device;
                    index++;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNameArray);
                listView.setAdapter(adapter);
            }

            if(peers.size()==0){
                Toast.makeText(getApplicationContext(),"No Device Found",Toast.LENGTH_SHORT).show();
            }
        }
    };

    WifiP2pManager.ConnectionInfoListener connectionInfoListener=new WifiP2pManager.ConnectionInfoListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
            final InetAddress groupOwnerAddress=wifiP2pInfo.groupOwnerAddress;

            if(wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner){
                connectionStatus.setText("Host");
                serverClass=new ServerClass();
                serverClass.start();
            }else{
                connectionStatus.setText("Client");
                clienteClass=new ClienteClass(groupOwnerAddress);
                clienteClass.start();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver,mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public class ServerClass extends Thread{

        ServerSocket ss;
        Socket s;

        @Override
        public void run() {
            try {
                ss = new ServerSocket(8000);

                while(true){

                    s = ss.accept();


                    dis = new DataInputStream(s.getInputStream());
                    dos = new DataOutputStream(s.getOutputStream());


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
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public class ClienteClass extends Thread{
        Socket s;
        String hostAdd;
        public ClienteClass(InetAddress hostAddress){
            hostAdd=hostAddress.getHostAddress();
            s=new Socket();
        }

        @Override
        public void run() {
            try{
                s.connect(new InetSocketAddress(hostAdd,8000),500);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());

                FileInputStream fis = openFileInput("datosLocal.txt");
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
    }

/**
    public class ServerClass extends Thread{
        Socket socket;
        ServerSocket serverSocket;
        @Override
        public void run(){
            try{
                serverSocket=new ServerSocket(8888);
                socket=serverSocket.accept();
                sendReceive=new SendReceive(socket);
                sendReceive.start();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    } **/

/**
    private class SendReceive extends Thread{
        private final Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public SendReceive(Socket skt){
            socket=skt;
            try {
                inputStream=socket.getInputStream();
                outputStream=socket.getOutputStream();
            } catch(IOException e){
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            byte[] buffer=new byte[1024];
            int bytes;

            while(socket!=null)
            {
                try {
                    bytes=inputStream.read(buffer);
                    if(bytes>0){
                        handler.obtainMessage(MESSAGE_READ,bytes,-1,buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes){
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } **/

/**
    public class ClienteClass extends Thread{
        Socket socket;
        String hostAdd;
        public ClienteClass(InetAddress hostAddress)
        {
            hostAdd=hostAddress.getHostAddress();
            socket=new Socket();
        }

        @Override
        public void run() {
            try {
                socket.connect(new InetSocketAddress(hostAdd,8888),500);
                sendReceive = new SendReceive(socket);
                sendReceive.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }**/



}