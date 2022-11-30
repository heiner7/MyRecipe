package com.example.my2.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my2.DatoReceta;
import com.example.my2.MainActivity;
import com.example.my2.OnTapListener;
import com.example.my2.R;
import com.example.my2.SetViewHolder;
import com.example.my2.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecetaAdapter extends RecyclerView.Adapter<SetViewHolder> {

    private Activity activity;
    List<DatoReceta> items = Collections.emptyList();
    private OnTapListener onTapListener;

    public RecetaAdapter(Activity activity, List<DatoReceta>items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public SetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista,parent,false);
        return new SetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SetViewHolder holder, final int position) {
            holder.txtNombre.setText(items.get(position).getNombrep());
            holder.txtDescri.setText(items.get(position).getPreparar());



            /*byte[] recetaImage = items.get(position).getImgp();
            Bitmap bitmap = BitmapFactory.decodeByteArray(recetaImage, 0, recetaImage.length);
            holder.txtImagen.setImageBitmap(bitmap);*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTapListener != null){
                        onTapListener.OnTapView(position);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setOnTapListener(OnTapListener onTapListener){
        this.onTapListener = onTapListener;
    }

}
