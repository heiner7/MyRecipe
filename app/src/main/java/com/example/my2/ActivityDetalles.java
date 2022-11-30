package com.example.my2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my2.adapter.RecetaAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityDetalles extends AppCompatActivity {

    private TextView mTxtNom, mTxtpre, mTxtDesc;
    private Button mBtnpedido;
    private ImageView mImgPro;
    private AlertDialog.Builder builder;
    private ProgressDialog dialog;
    private String mUser, saldofinal;
    private FloatingActionButton mBtnFlo;
    private MyOpenHelper dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        // Obtencion del id del producto
        Bundle dato = this.getIntent().getExtras();
        int sID = dato.getInt("Id");
        Log.d("id", String.valueOf(sID));
        builder = new AlertDialog.Builder(ActivityDetalles.this);
        mTxtDesc = (TextView)findViewById(R.id.txtdetallesd);

        mTxtNom = (TextView) findViewById(R.id.txtdetallesd);
        mTxtpre = (TextView) findViewById(R.id.txtpred);
        mImgPro = (ImageView) findViewById(R.id.imgdetalles);
       // mBtnFlo = (FloatingActionButton)findViewById(R.id.btnflo);

        dialog = new ProgressDialog(this);

        dbHandler = new MyOpenHelper(this, "RECETADB.sqlite", null, 1);

        Cursor cursor = dbHandler.listarrecetas("SELECT * FROM RECETA WHERE Id = "+sID);
        if (cursor != null){
            if(cursor.moveToFirst()) {
                do {
                    DatoReceta item = new DatoReceta();
                    item.setNombrep(cursor.getString(1));
                    mTxtNom.setText(item.getNombrep());
                    item.setPreparar(cursor.getString(2));
                    item.setImgp(cursor.getBlob(4));
                    byte[] recetaImage = item.getImgp();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(recetaImage, 0, recetaImage.length);
                    mImgPro.setImageBitmap(bitmap);

                } while (cursor.moveToNext());
            }
        }


    }
}
