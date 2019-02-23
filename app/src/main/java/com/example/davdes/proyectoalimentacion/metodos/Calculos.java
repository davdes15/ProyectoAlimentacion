package com.example.davdes.proyectoalimentacion.metodos;

public class Calculos {

    public static final int ROJO = 2;
    public static final int VERDE = 0;
    public static final int NARANJA = 1;

    public static int valoracion(int[] comb) {
        int verdes = 0;
        int rojos = 0;
        int naranjas = 0;
        for (int i : comb) {
            if (i == VERDE) {
                verdes++;
            } else if (i == NARANJA) {
                naranjas++;
            } else {
                rojos++;
            }
        }
        if (verdes >= 2) {
            if (verdes == 3) {
                return VERDE;
            } else {
                return NARANJA;
            }
        }
        if (rojos >= 2) {
            return ROJO;
        } else if (verdes == 1 & naranjas > 1) {
            return NARANJA;
        } else {
            return ROJO;
        }
    }
}
