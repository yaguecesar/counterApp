package com.example.cesar.proyect1.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cesar.proyect1.CounterAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar on 17/08/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bookDB";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE_COUNTER = "counter";
    private static final String NAME_COLUMN = "name";
    private static final String COUNT_COLUMN = "count";
    private static final String ID_COLUMN = "id";
    private CounterAdapter adapter;

    private static final String SQL_CREATE_ENTRIES = "Create Table "
            + DB_TABLE_COUNTER
            + "("
            + NAME_COLUMN + " TEXT PRIMARY KEY,"
            + COUNT_COLUMN + " TEXT);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DB_TABLE_COUNTER;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void guardarContador(Contador c)throws SQLiteException{

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME_COLUMN, c.getNombre());
        values.put(COUNT_COLUMN, c.getCuenta());


        try {
            db.insert(DB_TABLE_COUNTER, null, values);
        }catch (SQLiteException e){
            throw e;
        }finally {
            db.close();
        }
    }

    public List<Contador> obtenerContadores() throws SQLiteException{

        SQLiteDatabase db = getReadableDatabase();

        Cursor cr = null;

        List<Contador> list = new ArrayList<>();

        try{

            cr = db.query(DB_TABLE_COUNTER, new String[]{NAME_COLUMN, COUNT_COLUMN}, null, null, null, null, null);
            // SELECT * FROM table1 where column1='XYZ';

            if (cr != null){
                if (cr.moveToFirst()) {
                    do {
                        String name = cr.getString(cr.getColumnIndex(NAME_COLUMN));
                        String counter = cr.getString(cr.getColumnIndex(COUNT_COLUMN));

                        Contador c = new Contador(name);
                        c.setCuenta(Integer.parseInt(counter));

                        list.add(c);
                    } while (cr.moveToNext());
                }
            }

        }catch (SQLiteException e){
            throw e;
        }finally {
            db.close();
        }

        return list;
    }

    public void editarContador(Contador c){

    }

    public void eliminarContador(String nombre){
        SQLiteDatabase db = getWritableDatabase();

        try{
            db.delete(DB_TABLE_COUNTER, NAME_COLUMN + "='" + nombre + "'", null);
        }catch (SQLiteException e){

        }
    }
}
