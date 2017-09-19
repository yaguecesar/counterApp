package com.example.cesar.proyect1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cesar.proyect1.Database.Contador;
import com.example.cesar.proyect1.Database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{

    private ImageButton btnOK;
    private EditText newCounter;
    private DBHelper dbHelper;
    private ListView listView;
    private CounterAdapter adapter;
    List<Contador> lista = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        btnOK = (ImageButton) findViewById(R.id.btn_nuevo_contador);
        newCounter = (EditText) findViewById(R.id.edit_nuevo_contador);
        listView = (ListView) findViewById(R.id.list_view_contadores);

        btnOK.setOnClickListener(this);



        try{
            lista = dbHelper.obtenerContadores(); //new ArrayList<Contador>();
        }catch (SQLiteException e){
            Contador aux = new Contador("A", 1);

            lista = new ArrayList<Contador>();

            lista.add(aux);
            aux = new Contador("B", 16);
            lista.add(aux);
            Toast.makeText(this, R.string.changing_to_fixed_list, Toast.LENGTH_SHORT).show();
        }




        CounterAdapter adapter = new CounterAdapter(this, 1, lista);
        this.adapter = adapter;

        listView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnOK.getId()){
            Toast.makeText(this, "Click", Toast.LENGTH_LONG).show();
            if (!newCounter.getText().toString().isEmpty()) {
                newCounter();
            }

        }
    }

    private void newCounter(){
        Contador c = new Contador(newCounter.getText().toString());

        try{
            dbHelper.guardarContador(c);
            lista.add(c);
            adapter.notifyDataSetChanged();
//            this.newCounter.setText("");
            Toast.makeText(this, R.string.guardado_con_exito, Toast.LENGTH_SHORT).show();
        }catch (SQLiteException e){
            Toast.makeText(this, getString(R.string.error_guardar_db), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private class CounterAdapter extends ArrayAdapter<Contador> {

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

            final Contador c;

            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Get rowView from inflater
            final View rowView = inflater.inflate(R.layout.fila_contador, parent, false);




            if (!counters.isEmpty() && position <= counters.size()){
//             c = db.obtenerContadores().get(position);
                c = counters.get(position);
                TextView nameText = (TextView) rowView.findViewById(R.id.texto_nombre);
                TextView numberText = (TextView) rowView.findViewById(R.id.texto_cantidad);

                ImageButton imgInc = (ImageButton) rowView.findViewById(R.id.btn_incrementar);
                ImageButton imgDec = (ImageButton) rowView.findViewById(R.id.btn_decrementar);
                ImageButton imgDel = (ImageButton) rowView.findViewById(R.id.btn_eliminar);


                imgInc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inc(rowView, c);
                    }
                });

                imgDec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dec(rowView, c);
                    }
                });

                imgDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete(rowView);
                    }
                });

                nameText.setText(c.getNombre());
                numberText.setText(Integer.toString(c.getCuenta()));

            }

            // 5. retrn rowView
            return rowView;
        }

        private void inc(View row, Contador c){
            TextView txtNombre = (TextView) row.findViewById(R.id.texto_cantidad);

            c.incrementar();
            txtNombre.setText(Integer.toString(c.getCuenta()));


            db.actualizarContador(c);

        }

        private void dec(View row, Contador c){
            TextView txtCantidad = (TextView) row.findViewById(R.id.texto_cantidad);

            c.decrementar();

            txtCantidad.setText(Integer.toString(c.getCuenta()));

            db.actualizarContador(c);

        }

        private void delete(View row){

            TextView txt = (TextView) row.findViewById(R.id.texto_nombre);
            String nombre = txt.getText().toString();
            db.eliminarContador(nombre);

            int position = listView.getPositionForView(row);
            counters.remove(position);
            notifyDataSetChanged();



        }

    }
}