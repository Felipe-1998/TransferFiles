package com.example.transferfiles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Location extends AppCompatActivity {

    int LOCATION_REQUEST_CODE = 1001;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static String ub ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            askLocationPermission();
        }
    }

    private void askLocationPermission() {

        int Permiso1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int Permiso2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (Permiso1 == PackageManager.PERMISSION_GRANTED && Permiso2 == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
            Toast.makeText(this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_REQUEST_CODE);
            }

        }

    }

    private void getLastLocation() {
        @SuppressLint("MissingPermission") Task<android.location.Location> locationTask =
                fusedLocationProviderClient.getLastLocation();

        locationTask.addOnSuccessListener(location -> {
            if(location != null)
            {
                Toast.makeText(getApplicationContext(), location.getLatitude() + "," + location.getLongitude(),Toast.LENGTH_SHORT).show();
                ub=location.getLatitude()+"," + location.getLongitude();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Location was null",Toast.LENGTH_SHORT).show();
            }
        });

        locationTask.addOnFailureListener(e -> {

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==LOCATION_REQUEST_CODE)
        {
            if(grantResults.length>0 && grantResults[0] != PackageManager.PERMISSION_DENIED && grantResults[1]!=PackageManager.PERMISSION_DENIED)
            {
                //permisos aceptados
                getLastLocation();
            }
            else
            {
                //permisos no aceptados
                Toast.makeText(this, "los permisos no han sido aceptados",Toast.LENGTH_SHORT).show();
            }
        }
    }
}