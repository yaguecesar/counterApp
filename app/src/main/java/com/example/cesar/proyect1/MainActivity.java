package com.example.cesar.proyect1;

import android.app.Activity;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        btnOK = (ImageButton) findViewById(R.id.btn_nuevo_contador);
        newCounter = (EditText) findViewById(R.id.edit_nuevo_contador);
        listView = (ListView) findViewById(R.id.list_view_contadores);

        btnOK.setOnClickListener(this);

        List<Contador> lista = null;

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

        listView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnOK.getId()){
            Toast.makeText(this, "Click", Toast.LENGTH_LONG).show();
            if (!newCounter.getText().toString().isEmpty()) {
                newCounter();
                update();
            }

        }
    }

    private void newCounter(){
        Contador c = new Contador(newCounter.getText().toString());

        try{
            dbHelper.guardarContador(c);
            this.newCounter.setText("");
            Toast.makeText(this, R.string.guardado_con_exito, Toast.LENGTH_SHORT).show();
        }catch (SQLiteException e){
            Toast.makeText(this, getString(R.string.error_guardar_db), Toast.LENGTH_SHORT).show();
        }
    }

    public void update(){
        CounterAdapter adapter = new CounterAdapter(this, 1, dbHelper.obtenerContadores());
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_update){
            update();
        }

        return super.onOptionsItemSelected(item);
    }
}
