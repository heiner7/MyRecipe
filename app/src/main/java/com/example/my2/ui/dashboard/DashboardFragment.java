package com.example.my2.ui.dashboard;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my2.DatoReceta;
import com.example.my2.MyOpenHelper;
import com.example.my2.R;
import com.example.my2.adapter.ListaIngreAdapter;
import com.example.my2.adapter.RecetaAdapter;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {

    private RecyclerView mRvInicio;
    private Cursor cursor;
    private MyOpenHelper dbHandler;
    private ArrayList<DatoReceta> arrayList = new ArrayList<DatoReceta>();
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        /*dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
       /* dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               ;
            }
        });*/

        dbHandler = new MyOpenHelper(getActivity(), "RECETADB.sqlite", null, 1);


        mRvInicio =(RecyclerView)root.findViewById(R.id.listaIngre);
        agregarIngre();
        return root;
    }

    public void agregarIngre()
    {
        cursor=dbHandler.listarrecetas("SELECT * FROM RECETA");
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ListaIngreAdapter adapter = new ListaIngreAdapter(getActivity());
        mRvInicio.setHasFixedSize(true);
        mRvInicio.setLayoutManager(linearLayoutManager);
        mRvInicio.setAdapter(adapter);
    }

}
