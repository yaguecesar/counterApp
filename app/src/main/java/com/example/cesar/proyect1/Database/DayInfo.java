package com.example.cesar.proyect1.Database;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Cesar on 17/08/2017.
 */

public class DayInfo {
    Date fecha;
    ArrayList<Contador> contadores;

    public DayInfo(ArrayList<Contador> contadores){
        if (contadores != null && !contadores.isEmpty()){
            this.contadores = contadores;
            fecha = new Date();

        }
    }

}
