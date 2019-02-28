package com.example.davdes.proyectoalimentacion;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.davdes.proyectoalimentacion.Objetos.Alimento;
import com.example.davdes.proyectoalimentacion.metodos.Calculos;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Menu.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Menu extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Alimento> sel;
    ListView lv;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public Fragment_Menu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Menu.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Menu newInstance(String param1, String param2) {
        Fragment_Menu fragment = new Fragment_Menu();
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
        sel = (ArrayList<Alimento>) i.getSerializable("seleccionados");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment__menu, container, false);
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final View vista = view;
        lv = (ListView) view.findViewById(R.id.lista_menu);
        lv.setAdapter(new AdapterAlimentosMenu(getActivity(), sel));
        if (!sel.isEmpty()) {
            ((Button) view.findViewById(R.id.btnmenu)).setVisibility(View.VISIBLE);
            ((Button) view.findViewById(R.id.btnmenu)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sel.clear();
                    getFragmentManager().popBackStack();
                    v.setEnabled(false);
                }
            });
            int valoracion = cambiocolor();

            switch (valoracion) {
                case Calculos.VERDE:
                    ((LinearLayout) view.findViewById(R.id.res_menu)).setBackground(getResources().getDrawable(R.drawable.menu_correcto));
                    break;
                case Calculos.ROJO:
                    ((LinearLayout) view.findViewById(R.id.res_menu)).setBackground(getResources().getDrawable(R.drawable.menu_incorrecto));
                    break;
                case Calculos.NARANJA:
                    ((LinearLayout) view.findViewById(R.id.res_menu)).setBackground(getResources().getDrawable(R.drawable.menu_medio));
                    break;
            }
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Alimento a = sel.get(position);


                final Dialog fbDialogue = new Dialog(view.getContext(), android.R.style.Theme_Black_NoTitleBar);
                fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.dialogo_comida);

                fbDialogue.setCancelable(true);
                fbDialogue.show();
                float cortevaz = a.isLiquido() ? 100 * 0.025f : 100 * 0.05f;
                float corteraz = a.isLiquido() ? 100 * 0.075f : 100 * 0.1f;
                float cortevgr = 100 * 0.015f;
                float cortergr = 100 * 0.05f;
                float cortevsod = 100 * 0.0012f;
                float cortersod = 100 * 0.006f;
                int vaz,vgr,vsod;
                int valoracion;
                if(!a.isFruta()) {
                    if (a.getAz() > cortevaz) {
                        if (a.getAz() < corteraz) {
                            vaz = Calculos.NARANJA;
                        } else {
                            vaz = Calculos.ROJO;
                        }
//                    ((TextView) fbDialogue.findViewById(R.id.tvresultado)).setBackground(getResources().getDrawable(R.drawable.incorrecto));
                    } else {
                        vaz = Calculos.VERDE;
                        //    ((TextView) fbDialogue.findViewById(R.id.tvresultado)).setBackground(getResources().getDrawable(R.drawable.correcto));
                    }
                    if (a.getGr() > cortevgr) {
                        if (a.getGr() < cortergr) {
                            vgr = Calculos.NARANJA;
                        } else {
                            vgr = Calculos.ROJO;
                        }
                    } else {
                        vgr = Calculos.VERDE;
                    }
                    if (a.getSod() > cortevsod) {
                        if (a.getSod() < cortersod) {
                            vsod = Calculos.NARANJA;
                        } else {
                            vsod = Calculos.ROJO;
                        }
                    } else {
                        vsod = Calculos.VERDE;
                    }
                    int[] comb = {vaz, vgr, vsod};
                valoracion =Calculos.valoracion(comb);
                }else{
                    valoracion=Calculos.VERDE;
                }
                switch (valoracion) {
                    case Calculos.VERDE:
                        ((TextView) fbDialogue.findViewById(R.id.tvresultado)).setBackground(getResources().getDrawable(R.drawable.correcto));
                        break;
                    case Calculos.ROJO:
                        ((TextView) fbDialogue.findViewById(R.id.tvresultado)).setBackground(getResources().getDrawable(R.drawable.incorrecto));
                        break;
                    case Calculos.NARANJA:
                        ((TextView) fbDialogue.findViewById(R.id.tvresultado)).setBackground(getResources().getDrawable(R.drawable.medio));
                        break;
                }
                ((TextView) fbDialogue.findViewById(R.id.tvnombredialog)).setText(a.getNombre());
                ((TextView) fbDialogue.findViewById(R.id.tvaz)).setText(String.valueOf(a.getAz()));
                ((TextView) fbDialogue.findViewById(R.id.tvgs)).setText(String.valueOf(a.getGr()));
                ((TextView) fbDialogue.findViewById(R.id.tvs)).setText(String.valueOf(a.getSod()));
                ((Button) fbDialogue.findViewById(R.id.btnaddmenu)).setText("Eliminar");
                ((Button) fbDialogue.findViewById(R.id.btnaddmenu)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sel.remove(a);

                        lv.invalidateViews();

                        fbDialogue.cancel();
                        if(sel.size()==0){
                            getFragmentManager().popBackStack();
                        }
                        int valoracion = cambiocolor();
                        switch (valoracion) {
                            case Calculos.VERDE:
                                ((LinearLayout) vista.findViewById(R.id.res_menu)).setBackground(getResources().getDrawable(R.drawable.menu_correcto));
                                break;
                            case Calculos.ROJO:
                                ((LinearLayout) vista.findViewById(R.id.res_menu)).setBackground(getResources().getDrawable(R.drawable.menu_incorrecto));
                                break;
                            case Calculos.NARANJA:
                                ((LinearLayout) vista.findViewById(R.id.res_menu)).setBackground(getResources().getDrawable(R.drawable.menu_medio));
                                break;
                        }

                    }
                });


            }
        });



    }
    public int cambiocolor(){
        float totaz = 0, totgr = 0, totsod = 0;
        float totgram = 0;
        for (Alimento a : sel) {
            totaz += a.getAz();
            totgr += a.getGr();
            totsod += a.getSod();
            totgram += 100f;
        }
        int vaz, vgr, vsod;
        float cortevaz = totgram * 0.05f;
        float corteraz = totgram * 0.1f;
        float cortevgr = totgram * 0.015f;
        float cortergr = totgram * 0.05f;
        float cortevsod = totgram * 0.0012f;
        float cortersod = totgram * 0.006f;

        if (totaz > cortevaz) {
            if (totaz < corteraz) {
                vaz = Calculos.NARANJA;
            } else {
                vaz = Calculos.ROJO;
            }
//                    ((TextView) fbDialogue.findViewById(R.id.tvresultado)).setBackground(getResources().getDrawable(R.drawable.incorrecto));
        } else {
            vaz = Calculos.VERDE;
            //    ((TextView) fbDialogue.findViewById(R.id.tvresultado)).setBackground(getResources().getDrawable(R.drawable.correcto));
        }
        if (totgr > cortevgr) {
            if (totgr < cortergr) {
                vgr = Calculos.NARANJA;
            } else {
                vgr = Calculos.ROJO;
            }
        } else {
            vgr = Calculos.VERDE;
        }
        if (totsod > cortevsod) {
            if (totsod < cortersod) {
                vsod = Calculos.NARANJA;
            } else {
                vsod = Calculos.ROJO;
            }
        } else {
            vsod = Calculos.VERDE;
        }
        int[] comb = {vaz, vgr, vsod};
        return Calculos.valoracion(comb);
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
