package com.example.transferfiles;

import static android.app.DatePickerDialog.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Objects;

public class Formulario extends AppCompatActivity {

    TextView tvObjecto;
    Button btnSalve;
    String nombre, ubicacion, edad, apellido,cedula,fecha,genero,rh,prueba;
    TextInputLayout contrasenha, txtNombre,txtUbicacion,txtApellido,txtCedula,txtFechaDeNacimiento,txtGenero,txtRH;
    AutoCompleteTextView auto_Complete_Genero, auto_Complete_RH;
    private int dia,mes,ano;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        initialWork();
        listenerWork();
        autoComplete();
    }

    private void autoComplete() {
        String[] generos = new String[]{
                "M",
                "F",
                "Otro",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                Formulario.this, R.layout.dropdown_item, generos
        );

        auto_Complete_Genero.setAdapter(adapter);

        String[] grupoRh = new String[]{
                "A+",
                "A-",
                "B+",
                "B-",
                "AB+",
                "AB-",
                "O+",
                "O-",
        };

        ArrayAdapter<String> adapterh = new ArrayAdapter<>(
                Formulario.this, R.layout.dropdown_item, grupoRh
        );

        auto_Complete_RH.setAdapter(adapterh);
    }

    @SuppressLint("SetTextI18n")
    private void listenerWork() {

        txtUbicacion.getEditText().setOnClickListener(v -> {
            Ubicacion coordenadas = new Ubicacion(Formulario.this);
            ubicacion=Ubicacion.ub;
            if(ubicacion != null){
                obtenerUbicacion();
            }else{
                Toast.makeText(getApplicationContext(),"Presione otra vez para obtener ubicacion",Toast.LENGTH_SHORT ).show();
            }
            //Utiles.obtenerFechaActual("America/Mexico_City");
            //Utiles.obtenerHoraActual("America/Mexico_City");
        });

        txtFechaDeNacimiento.getEditText().setOnClickListener(v->{
            obtenerFechaDeNacimiento();
        });

        btnSalve.setOnClickListener(v -> {
            String fechaH = Utiles.obtenerFechaActual("GMT-5");
            Afectado afectado = null;
            prueba = contrasenha.getEditText().getText().toString().trim();
            Toast.makeText(this, "Mi contraseña es: " + prueba, Toast.LENGTH_SHORT).show();
            nombre=txtNombre.getEditText().getText().toString().trim() + " " +txtApellido.getEditText().getText().toString().trim();
            apellido=txtApellido.getEditText().getText().toString().trim();
            cedula=txtCedula.getEditText().getText().toString();
            fecha=txtFechaDeNacimiento.getEditText().getText().toString().trim();
            genero=auto_Complete_Genero.getText().toString();
            rh=auto_Complete_RH.getText().toString();
            ubicacion=txtUbicacion.getEditText().getText().toString();
            if(!fecha.equals("")){
                String[] fechaAct = fechaH.split("-");
                String[] fecha_Nacimiento = fecha.split("/");
                int age = Integer.parseInt(fechaAct[0]) - Integer.parseInt(fecha_Nacimiento[2]);
                edad= String.valueOf(age);
            }
            boolean validacion = validacion();
            //Evaluamos las variables nombre && edad && lugar
            if(!validacion){
                Mensaje();
            } else{
                afectado = new Afectado(nombre,ubicacion,edad,cedula,genero,rh);

                tvObjecto.setText("Nombre: " + afectado.getNombre() + "\n" +
                        "Edad: " + afectado.getEdad() + "\n" +
                        "Ubicacion: " + afectado.getLugar() + "\n"+
                        "Cedula: " + afectado.getCedula() +"\n"+
                        "Genero: " + afectado.getGenero() +"\n"+
                        "RH: " + afectado.getRh());

                OutputStreamWriter fichero = null;

                try{
                    fichero = new OutputStreamWriter(openFileOutput("datosLocal.txt",Context.MODE_PRIVATE));
                    fichero.write(afectado.getNombre()+";"
                                    +afectado.getEdad()+";"
                                    +afectado.getLugar()+";"
                                    +afectado.getCedula()+";"
                                    +afectado.getGenero()+";"
                                    +afectado.getRh());

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
            }


        });

    }

    private void obtenerFechaDeNacimiento() {
        final Calendar calendar = Calendar.getInstance();
        dia=calendar.get(Calendar.DAY_OF_MONTH);
        mes=calendar.get(Calendar.MONTH);
        ano=calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Objects.requireNonNull(txtFechaDeNacimiento.getEditText()).setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        },ano,mes,dia);
        datePickerDialog.show();
    }

    private boolean validacion() {
        boolean status=false;
        if(UValidator.validateName(txtNombre, "Nombre incorrecto")) {
            status = true;
        }
        if(UValidator.validateLastName(txtApellido, "Apellido incorrecto")){
            status=true;
        }
        if(UValidator.validateDI(txtCedula, "Numero de Id incorrecto")){
            status=true;
        }
        if(UValidator.validateUb(txtUbicacion,ubicacion, "Ubicacion Incorrecta")){
            status=true;
        }
        return status;
        //return !nombre.equals("") && !edad.equals("") && !ubicacion.equals("")
        //        && !cedula.equals("") && !genero.equals("") && !rh.equals("");
    }

    private void Mensaje() {
        Toast.makeText(Formulario.this,"Por favor complete el formulario",Toast.LENGTH_LONG).show();
    }

    private void obtenerUbicacion() {
        txtUbicacion.getEditText().setText(ubicacion);
    }

    private void initialWork() {

        tvObjecto=findViewById(R.id.tvObjecto);
        txtNombre=findViewById(R.id.txtNombre);
        txtApellido=findViewById(R.id.txtApellido);
        txtCedula=findViewById(R.id.txtCedula);
        txtFechaDeNacimiento=findViewById(R.id.txtFechaDeNacimiento);
        txtRH=findViewById(R.id.txtRH);
        txtUbicacion=findViewById(R.id.txtUbicacion); //here I put instances of the editTexts
        contrasenha=findViewById(R.id.txt_password);
        txtGenero=findViewById(R.id.txtGenero);
        auto_Complete_Genero=findViewById(R.id.auto_Complete_Genero);
        auto_Complete_RH=findViewById(R.id.auto_Complete_RH);
        btnSalve=findViewById(R.id.btnSalve); //here I put a instance of the button to save data
    }
}