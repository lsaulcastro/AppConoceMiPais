package com.example.sauld.appconocemipais.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sauld.appconocemipais.Modelos.Destino;
import com.example.sauld.appconocemipais.R;
import com.example.sauld.appconocemipais.ViewHolders.MyViewHolderDesti;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MyAdapterDesti extends RecyclerView.Adapter<MyViewHolderDesti> implements View.OnClickListener {

    private ArrayList<Destino> arrayList;
    private Context context;
    private View.OnClickListener listener;


    public MyAdapterDesti(Context context1) {

        arrayList = new ArrayList<>();
        this.context = context1;
    }

    @Override
    public MyViewHolderDesti onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_destino, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolderDesti(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolderDesti holder, int position) {

        holder.titulo.setText(arrayList.get(position).getTitulo());
        Picasso.with(context).load(arrayList.get(position).getImg()).
                error(R.color.colorblack99).placeholder(R.color.colorAccent).into(holder.img, new Callback() {
            @Override
            public void onSuccess() {
                // Toast.makeText(context, "Funciono", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                //  Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }

    }

    public void add(Destino destino) {
        this.arrayList.add(0, destino);
        this.notifyItemInserted(0);

    }

    public void clearArray(){

        if (arrayList.size() >= 0){
            arrayList.clear();
        }
    }

    public void updateItem(Destino destino) {
        int position = this.arrayList.indexOf(destino);
        if (position >= 0) {
            this.arrayList.remove(position);
            this.arrayList.add(position, destino);
        }
        this.notifyItemChanged(position);


    }

    public void removeItem(Destino destino) {
        int position = this.arrayList.indexOf(destino);
        if (position >= 0) {
            this.arrayList.remove(position);
        }
        this.notifyItemRemoved(position);
    }

    public ArrayList<Destino> getArrayList(){
        if (arrayList.size() >= 0){

            return arrayList;
        }

        return null;
    }
}
