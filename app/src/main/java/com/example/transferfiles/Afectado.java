package com.example.transferfiles;

import java.io.Serializable;

public class Afectado implements Serializable {
    private String nombre;
    private String lugar;
    private String edad;
    private String cedula;
    private String genero;
    private String rh;

    public Afectado(String nombre, String lugar, String edad, String cedula, String genero, String rh) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.edad = edad;
        this.cedula = cedula;
        this.genero = genero;
        this.rh = rh;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }


}
