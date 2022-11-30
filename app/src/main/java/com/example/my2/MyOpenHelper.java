package com.example.my2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String TABLA_RECETA = "receta";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMAGEN = "imagen";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_PREPARAR = "preparar";
    public static final String COLUMN_INGREDIENTE = "ingrediente";
    public static final String COLUMN_CATEGORIA = "categoria";


    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Añade un nuevo Row a la Base de Datos

    public void addReceta(DatoReceta receta) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO RECETA VALUES (NULL, ?, ?, ?, ?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, receta.getNombrep());
        statement.bindString(2, receta.getPreparar());
        statement.bindString(3, receta.getIngre());
        statement.bindBlob(4, receta.getImgp());
        statement.bindString(5, receta.getCategoria());

        statement.executeInsert();

    }

    public void updatereceta(DatoReceta receta){
        ContentValues values = new ContentValues();
        values.put(String.valueOf(COLUMN_IMAGEN), receta.getImgp());
        values.put(COLUMN_NOMBRE, receta.getNombrep());
        values.put(COLUMN_PREPARAR, receta.getPreparar());
        /*values.put(COLUMN_EDAD, personas.get_edad());
        values.put(COLUMN_TEL, personas.get_telefono());
        values.put(COLUMN_EMAIL, personas.get_email());
        values.put(COLUMN_SANGRE, personas.get_tiposangre());*/
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLA_RECETA, values, COLUMN_ID + "= ?", new String[] { String.valueOf(receta.getId())});
        db.close();

    }

    // Borrar una persona de la Base de Datos

    public void borrarReceta(int receta_id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLA_RECETA + " WHERE " + COLUMN_ID + " = " + receta_id + ";");
        db.close();
    }


    //listar a todas las personas
    public Cursor listarrecetas(String sql){
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(sql, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }
}
