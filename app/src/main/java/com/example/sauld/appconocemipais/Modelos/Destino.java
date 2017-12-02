package com.example.sauld.appconocemipais.Modelos;

/**
 * Created by sauld on 24/10/2017.
 */

public class Destino {
    public String uid;
    public String descripcion;
    public String img;
    public String titulo;

    public Destino() {

    }

    public String getKey() {
        return uid;
    }

    public void setKey(String key) {
        this.uid = key;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Destino{" +
                "key='" + uid + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", img='" + img + '\'' +
                ", titulo='" + titulo + '\'' +
                '}';
    }

    public Destino(String key, String descripcion, String img, String titulo) {
        this.uid = key;
        this.descripcion = descripcion;
        this.img = img;
        this.titulo = titulo;
    }
}
