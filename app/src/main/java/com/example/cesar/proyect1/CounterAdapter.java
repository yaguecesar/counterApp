package com.example.cesar.proyect1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cesar.proyect1.Database.Contador;
import com.example.cesar.proyect1.Database.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar on 03/09/2017.
 */

public class CounterAdapter extends ArrayAdapter<Contador> {

    private List<Contador> counters;
    private Context context;
    private DBHelper db;

    public CounterAdapter(Context context, int resource, List<Contador> objects) {
        super(context, resource, objects);
        this.context = context;
        counters = objects;
        db = new DBHelper(getContext());


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater

        DBHelper db = new DBHelper(getContext());
        //List<Contador> list = db.obtenerContadores();


        Contador c = null;

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        final View rowView = inflater.inflate(R.layout.fila_contador, parent, false);




        if (!counters.isEmpty()){
//             c = db.obtenerContadores().get(position);
            c = counters.get(position);
            TextView nameText = (TextView) rowView.findViewById(R.id.texto_nombre);
            TextView numberText = (TextView) rowView.findViewById(R.id.texto_cantidad);

            ImageButton imgInc = (ImageButton) rowView.findViewById(R.id.btn_incrementar);
            ImageButton imgDec = (ImageButton) rowView.findViewById(R.id.btn_decrementar);
            ImageButton imgDel = (ImageButton) rowView.findViewById(R.id.btn_eliminar);

//            imgInc.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    inc(rowView);
//                }
//            });
//
//            imgDec.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dec(rowView);
//                }
//            });


            nameText.setText(c.getNombre());
            numberText.setText(Integer.toString(c.getCuenta()));

        }

        // 5. retrn rowView
        return rowView;
    }



//    private void inc(View row){
//        TextView txt = (TextView) row.findViewById(R.id.texto_cantidad);
//        int cantidad = Integer.parseInt(txt.getText().toString());
//        cantidad++;
//        txt.setText(cantidad);
//
//        // CONTINUAR
//
//    }
//
//    private void dec(View row){
//
//    }
//
//    private void delete(View row){
//
//        TextView txt = (TextView) row.findViewById(R.id.texto_nombre);
//        String nombre = txt.getText().toString();
//        db.eliminarContador(nombre);
//
//        Contador c = new Contador("a", 0);
//
//        for (int i = 0; i < counters.size(); i++){
//            c = counters.get(i);
//            if (c.getNombre().compareTo(nombre) == 0){
//
//
//            }
//        }
//    }
//
//    public void update(){
////        for (Contador c : counters) {
////            this.remove(c);
////        }
////        this.addAll(db.obtenerContadores());
//    }

}
