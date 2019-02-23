package com.example.davdes.proyectoalimentacion;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.davdes.proyectoalimentacion.Objetos.Alimento;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterAlimentosMenu extends ArrayAdapter{
    private Activity context;
    private ArrayList<Alimento> alim;

    public AdapterAlimentosMenu(@NonNull Activity context, @NonNull ArrayList<Alimento> alim) {
        super(context, R.layout.layout_lista_alim, alim);
        this.context = context;
        this.alim = alim;
    }

    public View getView(int pos, View view, ViewGroup parent) {

        View fila = view;
        AdapterAlimentos.ViewHolder holder;
        if (fila == null) {
            LayoutInflater li = context.getLayoutInflater();
            fila = li.inflate(R.layout.layout_lista_alim, null);

            holder = new AdapterAlimentos.ViewHolder();
            holder.nombre = (TextView) fila.findViewById(R.id.tvlista);
            holder.cimg = (CircleImageView) fila.findViewById(R.id.imglista);
            fila.setTag(holder);
        } else {
            holder = (AdapterAlimentos.ViewHolder) fila.getTag();
        }

        holder.nombre.setText(alim.get(pos).getNombre());

        return fila;
    }

    static class ViewHolder {
        TextView nombre;
        CircleImageView cimg;
    }
}
