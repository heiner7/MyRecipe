package com.example.my2.ui.home;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.my2.ActivityDetalles;
import com.example.my2.DatoReceta;
import com.example.my2.MainActivity;
import com.example.my2.MyDatabase;
import com.example.my2.MyOpenHelper;
import com.example.my2.OnTapListener;
import com.example.my2.adapter.RecetaAdapter;
import com.example.my2.busqueda_avanzadas;
import com.example.my2.ui.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.my2.R;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class HomeFragment extends  Fragment {

    // Variables Globales
    private RecyclerView mRvInicio;
    private MyDatabase dbHandler2;
    private ProgressBar mProgInicio;
    private String mUser, key;
    private FloatingActionButton mBtnFlo;
    private ImageView mImgPizza, mImgCH, mImgHam;
          TextView  mostrar;
    private String mCategoria;
    private MyOpenHelper dbHandler;
    private ArrayList<DatoReceta> arrayList = new ArrayList<DatoReceta>();

    private Cursor cursor;
    private RecetaAdapter adapter;
    private MainActivity main;
    public int posicionReceta;
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });*/

        mRvInicio =(RecyclerView)root.findViewById(R.id.lista);
        mImgCH = (ImageView)root.findViewById(R.id.imgchi);
        mImgHam = (ImageView)root.findViewById(R.id.imghami);
        mImgPizza = (ImageView)root.findViewById(R.id.imgpizzai);
        mBtnFlo = (FloatingActionButton)root.findViewById(R.id.btnflo);


        dbHandler = new MyOpenHelper(getActivity(), "RECETADB.sqlite", null, 1);

        //mProgInicio = (ProgressBar)root.findViewById(R.id.progress);

       //loadDatabase("SELECT * FROM receta");
        dbHandler2 = new MyDatabase(getActivity());
        dbHandler2.getReadableDatabase();
        copyDatabase(getContext());
        arrayList = dbHandler2.listarReceta();
        Log.d("Arra", arrayList.get(0).getNombrep());
        RecetaAdapter adapter = new RecetaAdapter(getActivity(),arrayList);
        mRvInicio.setLayoutManager(linearLayoutManager);
        mRvInicio.setAdapter(adapter);

        Drawable originalDrawable = getResources().getDrawable(R.drawable.pizza);
        Drawable originalDrawable3 = getResources().getDrawable(R.drawable.comidachina);
        Drawable originalDrawable2 = getResources().getDrawable(R.drawable.hamburgusa);

        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
        Bitmap originalBitmap2 = ((BitmapDrawable) originalDrawable2).getBitmap();
        Bitmap originalBitmap3 = ((BitmapDrawable) originalDrawable3).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable2 =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap2);

        //asignamos el CornerRadius
        roundedDrawable2.setCornerRadius(originalBitmap3.getHeight());

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable3 =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap3);

        //asignamos el CornerRadius
        roundedDrawable3.setCornerRadius(originalBitmap3.getHeight());

        mImgPizza.setImageDrawable(roundedDrawable);
        mImgHam.setImageDrawable(roundedDrawable2);
        mImgCH.setImageDrawable(roundedDrawable3);

        mImgPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategoria = "pizza";
                mRvInicio.setVisibility(View.VISIBLE);
               // mProgInicio.setVisibility(View.VISIBLE);

                loadDatabase("SELECT * FROM RECETA WHERE categoria = 'pizza'");
            }
        });

        mBtnFlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), busqueda_avanzadas.class);

                startActivity(intent);
            }
        });

        return root;
    }

    /*@Override
    public void onStart() {
        super.onStart();



    }*/

    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(MyDatabase.DB_NAME);
            String outFileName = MyDatabase.DB_PATH + MyDatabase.DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity","DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cargarDetalle(int position){
        Intent intent = new Intent(getContext(), ActivityDetalles.class);
        intent.putExtra("Id", position);
        startActivity(intent);
    }

    public void loadDatabase(String sql){
        arrayList.clear();
        cursor=dbHandler.listarrecetas(sql);
        if (cursor != null){
            if(cursor.moveToFirst()) {
                do {
                    DatoReceta item = new DatoReceta();
                    item.setId(cursor.getInt(0));
                    item.setNombrep(cursor.getString(1));
                    item.setPreparar(cursor.getString(2));
                    item.setImgp(cursor.getBlob(4));
                    arrayList.add(item);
                } while (cursor.moveToNext());
            }
        }


        RecetaAdapter adapter = new RecetaAdapter(getActivity(),arrayList);
        adapter.setOnTapListener(new OnTapListener() {
            @Override
            public void OnTapView(int position) {
                // Toast.makeText(getContext(),"Click to"+position, Toast.LENGTH_SHORT).show();
                posicionReceta = arrayList.get(position).getId();
                cargarDetalle(posicionReceta);

            }
        });
        mRvInicio.setHasFixedSize(true);
        mRvInicio.setLayoutManager(linearLayoutManager);
        mRvInicio.setAdapter(adapter);
        MainActivity.arrayList(arrayList, mRvInicio);

    }

    public RecetaAdapter adapter(final ArrayList<DatoReceta> arrayList,RecyclerView mRvInicio1)
    {

        RecetaAdapter adapter = new RecetaAdapter(getActivity(), arrayList);
        mRvInicio1.setLayoutManager(linearLayoutManager);
        mRvInicio1.setAdapter(adapter);
        return adapter;

    }





}
