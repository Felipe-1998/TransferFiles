package com.example.transferfiles.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.transferfiles.BaseHelper;

public class ControllerDB {
    private String[] texto;
    private Context context;

    public ControllerDB(String[] texto,Context context) {
        this.texto = texto;
        this.context= context;
    }

    public void firstRegister(){
        BaseHelper baseHelper = new BaseHelper(context,"mibase",null,1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        db.beginTransaction();
        for(int i = 0; i< texto.length-1; i++){
            String[] linea = texto[i].split(";");
            ContentValues contentValues = new ContentValues();
            contentValues.put("Nombre",linea[0]);
            contentValues.put("Edad", linea[1]);
            contentValues.put("Lugar", linea[2]+","+linea[3]);
            contentValues.put("Cedula",linea[4]);
            contentValues.put("Genero",linea[5]);
            contentValues.put("Rh",linea[6]);
            contentValues.put("Enviado",0);
            contentValues.put("Fuente","App");
            db.insert("Reportes",null, contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void updateStatus(){
        BaseHelper baseHelper = new BaseHelper(context,"mibase",null,1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        ContentValues status = new ContentValues();
        status.put("Enviado",1);
        String selection = "Fuente=?";
        String[] args = new String []{"App"};
        db.update("Reportes",status,selection,args);
    }

    public int getRegistersQuantity() {
        BaseHelper baseHelper = new BaseHelper(context, "mibase", null, 1);
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        Cursor cursor = db.query("Reportes",null,null,null,null,null,null);
        int count = cursor.getCount();
        Toast.makeText(context,count + "  registros",Toast.LENGTH_SHORT).show();
        return count;
    }
}
