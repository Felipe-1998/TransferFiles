package com.example.transferfiles;

import java.io.Serializable;

public class Afectado implements Serializable {
    private String Nombre;
    private String lugar;
    private int edad;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Afectado(String nombre, String lugar, int edad) {
        Nombre = nombre;
        this.lugar = lugar;
        this.edad = edad;
    }
}
