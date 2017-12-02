package com.example.sauld.appconocemipais.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sauld.appconocemipais.R;


public class MyViewHolderDesti extends RecyclerView.ViewHolder {
    public TextView titulo;
    public ImageView img;

    public MyViewHolderDesti(View itemView) {
        super(itemView);
        titulo = itemView.findViewById(R.id.tituloDestino);
        img = itemView.findViewById(R.id.imgDestino);
    }
}
