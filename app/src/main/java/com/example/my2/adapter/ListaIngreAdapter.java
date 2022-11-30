package com.example.my2.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my2.DatoReceta;
import com.example.my2.OnTapListener;
import com.example.my2.R;
import com.example.my2.SetViewHolder;

import java.util.List;

public class ListaIngreAdapter extends RecyclerView.Adapter<SetViewHolder> {
    private Activity activity;
    private OnTapListener onTapListener;

    public ListaIngreAdapter(Activity activity){
        this.activity = activity;

    }

    @Override
    public SetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista,parent,false);
        return new SetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SetViewHolder holder, int i) {
        holder.txtNombre.setText("f");
        holder.txtDescri.setText("g");
        holder.txtImagen.setImageBitmap(null);
    }



    @Override
    public int getItemCount() {
        return 0;
    }

    public void setOnTapListener(OnTapListener onTapListener){
        this.onTapListener = onTapListener;
    }
}
