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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.davdes.proyectoalimentacion.Objetos.Alimento;

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
    private TextView tvFragment, tvtitlep, tvtitleg;
    private OnFragmentInteractionListener mListener;
    private int posicion;
    private ArrayList<String> alim;
    ListView lv;
    int TIMES = 0;
    boolean anima = false;

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
        String[] cat = view.getResources().getStringArray(R.array.planets_array);
        String[] alimentos = view.getResources().getStringArray(R.array.tablas);
        final String tablaactual = alimentos[posicion];
        lv = (ListView) view.findViewById(R.id.listalim);
        tvtitleg = (TextView) view.findViewById(R.id.tvtitleg);
        tvtitlep = (TextView) view.findViewById(R.id.tvtitlep);
        tvtitleg.setText(cat[posicion]);
        tvtitlep.setText(cat[posicion]);
        DBHelper helper = new DBHelper(view.getContext());
        final SQLiteDatabase al = helper.getReadableDatabase();
        String[] campos = {"Alimento"};
        alim = new ArrayList<>(0);
        Cursor c;
        String s = "";
        c = al.rawQuery("SELECT * FROM '" + tablaactual + "'", null);

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
                anima = false;
                String alimento = alim.get(position);
                String[] campos = {alimento};
                Cursor c = al.query("'" + tablaactual + "'", null, "Alimento='" + alimento + "'", null, null, null, null);

                final Dialog fbDialogue = new Dialog(view.getContext(), android.R.style.Theme_Black_NoTitleBar);
                fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.dialogo_comida);

                fbDialogue.setCancelable(true);
                fbDialogue.show();
                c.moveToNext();
                ((TextView) fbDialogue.findViewById(R.id.tvnombredialog)).setText(String.valueOf(c.getString(0)));

                float az = 0f;
                float gr = 0f;
                float sod = 0f;
                int err = 0;
                try {
                    az = Float.parseFloat(c.getString(1));
                } catch (Exception e) {
                    err = 1;
                }
                if (err == 1) {
                    try {
                        az = Float.parseFloat(c.getString(1).replace(',', '.'));
                    } catch (Exception e) {

                    }
                }
                try {
                    gr = Float.parseFloat(c.getString(2));
                } catch (Exception e) {
                    err = 1;
                }
                if (err == 1) {
                    try {
                        gr = Float.parseFloat(c.getString(2).replace(',', '.'));
                    } catch (Exception e) {

                    }
                }
                try {
                    sod = Float.parseFloat(c.getString(3));
                } catch (Exception e) {
                    err = 1;
                }
                if (err == 1) {
                    try {
                        sod = Float.parseFloat(c.getString(3).replace(',', '.'));
                    }catch(Exception e){

                    }
                }
                ((TextView) fbDialogue.findViewById(R.id.tvaz)).setText(String.valueOf(az) + " g");
                ((TextView) fbDialogue.findViewById(R.id.tvgs)).setText(String.valueOf(gr) + " g");
                ((TextView) fbDialogue.findViewById(R.id.tvs)).setText(String.valueOf(sod) + " mg");
                float tot = 100f;
                float peraz = tot * 0.1f;
                final Alimento al = new Alimento(az,gr,sod,c.getString(0));
                if(az > peraz){
                    ((TextView) fbDialogue.findViewById(R.id.tvresultado)).setBackground(getResources().getDrawable(R.drawable.incorrecto));
                }else{
                    ((TextView) fbDialogue.findViewById(R.id.tvresultado)).setBackground(getResources().getDrawable(R.drawable.correcto));
                }
                ((Button) fbDialogue.findViewById(R.id.btnaddmenu)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }


        });
        final View v = view;
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;
            private int firstVisibleItem, visibleItemCount, totalItemCount;
            protected AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
            protected AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Animation slide = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_anim);
                if (firstVisibleItem == 0) {
                    // check if we reached the top or bottom of the list
                    View v = lv.getChildAt(0);

                    int offset = (v == null) ? 0 : v.getTop();
                    if (offset == 0) {

                        tvtitlep.setVisibility(View.GONE);
                        /*if (TIMES !=0) {
                            Log.i("ANIM",String.valueOf(TIMES));
                            tvtitleg.startAnimation(slide);
                        }*/

                        tvtitleg.setVisibility(View.VISIBLE);
                        anima = false;

                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Animation slide = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_anim);
                if (firstVisibleItem == 0) {
                    // check if we reached the top or bottom of the list
                    View v = lv.getChildAt(0);

                    int offset = (v == null) ? 0 : v.getTop();
                    if (offset == 0) {

                        tvtitlep.setVisibility(View.GONE);
                        if (anima) {
                            Log.i("ANIM", String.valueOf(TIMES));
                            tvtitleg.startAnimation(fadeIn);
                            fadeIn.setDuration(1200);
                            fadeIn.setStartOffset(fadeIn.getStartOffset());
                        }

                        tvtitleg.setVisibility(View.VISIBLE);


                    }
                }
                this.firstVisibleItem = firstVisibleItem;
                this.visibleItemCount = visibleItemCount;
                this.totalItemCount = totalItemCount;
                if (mLastFirstVisibleItem < firstVisibleItem) {
                    Log.i("SCROLLING DOWN", "TRUE");
                    if (tvtitlep.getVisibility() != View.VISIBLE) {
                        tvtitleg.setVisibility(View.GONE);
                        tvtitleg.startAnimation(fadeOut);

                        fadeOut.setDuration(800);
                        //fadeOut.setFillAfter(true);
                        tvtitlep.startAnimation(fadeIn);
                        tvtitlep.setVisibility(View.VISIBLE);
                        fadeIn.setDuration(700);
                        //fadeIn.setFillAfter(true);

                        fadeOut.setStartOffset(fadeIn.getStartOffset() - 4000);
                    }
                    anima = true;

                }
                if (mLastFirstVisibleItem > firstVisibleItem) {
                    Log.i("SCROLLING UP", "TRUE");
                }
                mLastFirstVisibleItem = firstVisibleItem;
            }

            private boolean listIsAtTop() {
                if (lv.getChildCount() == 0) return true;
                return lv.getChildAt(0).getTop() == 0;
            }
        });
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
