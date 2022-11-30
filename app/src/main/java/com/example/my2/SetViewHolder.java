package com.example.my2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class SetViewHolder extends RecyclerView.ViewHolder {

    public TextView txtNombre, txtDescri;
    public ImageView txtImagen;

    public SetViewHolder( View itemView) {
        super(itemView);
        txtNombre = (TextView) itemView.findViewById((R.id.text_word));
        txtDescri = (TextView) itemView.findViewById((R.id.txt_nghia));
        txtImagen = (ImageView)itemView.findViewById((R.id.imagen));
    }

}
