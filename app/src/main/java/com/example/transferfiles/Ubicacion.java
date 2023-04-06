package com.example.transferfiles;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Ubicacion extends FusedLocationProviderClient {

    private final FusedLocationProviderClient fusedLocationProviderClient;
    public static String ub;


    public Ubicacion(@NonNull Activity activity) {
        super(activity);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

        getLastLocationN();
    }

    private void getLastLocationN() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location != null)
                {
                    ub=location.getLatitude()+";" + location.getLongitude();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Location was null",Toast.LENGTH_SHORT).show();
                }

            }
        });

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error" + e , Toast.LENGTH_SHORT).show();
            }
        });
    }



}
