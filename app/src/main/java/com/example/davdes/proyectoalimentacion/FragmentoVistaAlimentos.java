package com.example.davdes.proyectoalimentacion;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentoVistaAlimentos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentoVistaAlimentos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoVistaAlimentos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvFragment;
    private OnFragmentInteractionListener mListener;
    private int posicion;
    private ArrayList<String> alim;

    public FragmentoVistaAlimentos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoVistaAlimentos.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoVistaAlimentos newInstance(String param1, String param2) {
        FragmentoVistaAlimentos fragment = new FragmentoVistaAlimentos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle i = getArguments();
        posicion = i.getInt("posicion");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragmento_vista_alimentos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //tvFragment = (TextView) view.findViewById(R.id.tvFragment);
        // tvFragment.setText("Posicion: " + posicion);
        final String ud = posicion == 0 ? "l" : "g";
        String[] alimentos = view.getResources().getStringArray(R.array.tablas);
        final String tablaactual = alimentos[posicion];
        ListView lv = (ListView) view.findViewById(R.id.listalim);
        DBHelper helper = new DBHelper(view.getContext());
        final SQLiteDatabase al = helper.getReadableDatabase();
        String[] campos = {"Alimento"};
        alim = new ArrayList<>(0);
        Cursor c;
        String s = "";
        c = al.rawQuery("SELECT * FROM '"+tablaactual+"'",null);

        while (c.moveToNext()) {
            s = c.getString(0);
            //Log.i("ALIMENTO",s);
            alim.add(s);
        }
        lv.setAdapter(new AdapterAlimentos(getActivity(), alim));
        c.close();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String alimento = alim.get(position);
                String[] campos = {alimento};
                Cursor c = al.query("'"+tablaactual+"'", null, "Alimento=", campos, null, null, null);

                final Dialog fbDialogue = new Dialog(view.getContext(), android.R.style.Theme_Black_NoTitleBar);
                fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.dialogo_comida);

                fbDialogue.setCancelable(true);
                fbDialogue.show();
                c.moveToNext();
                ((TextView) fbDialogue.findViewById(R.id.tvnombredialog)).setText(String.valueOf(c.getString(0)));
                ((TextView) fbDialogue.findViewById(R.id.tvaz)).setText(String.valueOf(c.getString(1) + "g"));
                ((TextView) fbDialogue.findViewById(R.id.tvgs)).setText(String.valueOf(c.getString(2) + "g"));
                ((TextView) fbDialogue.findViewById(R.id.tvs)).setText(String.valueOf(c.getString(3) + "mg"));
            }


        });
        final View v = view;
        /*((Button) view.findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog fbDialogue = new Dialog(v.getContext(), android.R.style.Theme_Black_NoTitleBar);
                fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.dialogo_comida);
                fbDialogue.setCancelable(true);
                fbDialogue.show();
            }
        });*/
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
