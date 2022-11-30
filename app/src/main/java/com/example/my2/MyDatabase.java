package com.example.my2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.my2.ui.Product;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {

    public static final String DB_PATH = "/data/data/com.example.my2/databases/";
    public static final String DB_NAME = "Prueba.sqlite";
    private SQLiteDatabase myDataBase;
    private Context myContext;

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }


    public void closeDatabase() {
        if(myDataBase!=null) {
            myDataBase.close();
        }
    }


    public void openDataBase()
    {
        String path = myContext.getDatabasePath(DB_NAME).getPath();
        if(myDataBase != null && myDataBase.isOpen()) {
            return;
        }
        myDataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public ArrayList<DatoReceta> listarReceta()
    {
        DatoReceta receta = null;
        ArrayList<DatoReceta> temp = new ArrayList<>();
        openDataBase();

        Cursor cursor = myDataBase.rawQuery("SELECT * FROM tbl_receta", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            receta = new DatoReceta(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            temp.add(receta);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return temp;
    }

    public void addReceta(SQLiteDatabase db,DatoReceta receta) {

        ContentValues row1 = new ContentValues();
        row1.put("idReceta", 3);
        row1.put("categoria", receta.getCategoria());
        row1.put("nombre", receta.getNombrep());
        row1.put("praparar", receta.getPreparar());


        db.insert("tbl_receta", null, row1);
    }







    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
