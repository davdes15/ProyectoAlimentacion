package com.example.davdes.proyectoalimentacion.Objetos;

public class Alimento {
    private float az,gr,sod;
    private String nombre;

    public Alimento(float az, float gr, float sod, String nombre) {
        this.az = az;
        this.gr = gr;
        this.sod = sod;
        this.nombre = nombre;
    }

    public float getAz() {
        return az;
    }

    public void setAz(float az) {
        this.az = az;
    }

    public float getGr() {
        return gr;
    }

    public void setGr(float gr) {
        this.gr = gr;
    }

    public float getSod() {
        return sod;
    }

    public void setSod(float sod) {
        this.sod = sod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
