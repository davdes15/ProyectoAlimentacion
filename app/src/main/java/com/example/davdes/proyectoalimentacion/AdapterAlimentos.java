package com.example.davdes.proyectoalimentacion;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterAlimentos extends ArrayAdapter {

    private Activity context;
    private ArrayList<String> alim;

    public AdapterAlimentos(@NonNull Activity context, @NonNull ArrayList<String> alim) {
        super(context, R.layout.layout_lista_alim, alim);
        this.context = context;
        this.alim = alim;
    }

    public View getView(int pos, View view, ViewGroup parent) {

        View fila = view;
        ViewHolder holder;
        if (fila == null) {
            LayoutInflater li = context.getLayoutInflater();
            fila = li.inflate(R.layout.layout_lista_alim, null);

            holder = new ViewHolder();
            holder.nombre = (TextView) fila.findViewById(R.id.tvlista);
            holder.cimg = (CircleImageView) fila.findViewById(R.id.imglista);
            fila.setTag(holder);
        } else {
            holder = (ViewHolder) fila.getTag();
        }

        holder.nombre.setText(alim.get(pos));

        return fila;
    }

    static class ViewHolder {
        TextView nombre;
        CircleImageView cimg;
    }

}
