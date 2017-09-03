package com.example.cesar.proyect1.Database;

/**
 * Created by Cesar on 17/08/2017.
 */

public class Contador {
    private int cuenta;
    private String nombre;
    private boolean favorito;

    public Contador(String nombre){
        this.nombre = nombre;
        cuenta = 0;
        favorito = false;
    }

    public Contador(String nombre, int cuenta){
        this.nombre = nombre;
        this.cuenta = cuenta;
        favorito = false;
    }

    public void incrementar(){
        cuenta++;
    }

    public void decrementar(){
        cuenta--;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    @Override
    public String toString() {
        return nombre + " = " + cuenta;
    }
}
