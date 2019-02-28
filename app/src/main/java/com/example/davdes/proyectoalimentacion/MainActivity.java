package com.example.davdes.proyectoalimentacion;



import android.os.Bundle;
import android.util.Log;

import android.view.MenuItem;

import android.view.Window;


import com.example.davdes.proyectoalimentacion.Objetos.Alimento;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private String[] footypes;
    private DrawerLayout mDrawerLayout;
    ArrayList<Alimento> seleccionados = new ArrayList<>(0);
    //private ListView mDrawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
      /*  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_main);
        footypes = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navview = (NavigationView) findViewById(R.id.nav_view);
        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                int id = menuItem.getItemId();
                int pos = -1;
                switch (id) {
                    case R.id.nav_beb:
                        pos = 0;
                        break;
                    case R.id.nav_cer:
                        pos = 1;
                        break;
                    case R.id.nav_fr:
                        pos = 2;
                        break;
                    case R.id.nav_lac:
                        pos = 3;
                        break;
                    case R.id.nav_leg:
                        pos = 4;
                        break;
                    case R.id.nav_ov:
                        pos = 5;
                        break;
                    case R.id.nav_sem:
                        pos = 6;
                        break;
                    case R.id.nav_verd:
                        pos = 7;
                        break;
                    case R.id.nav_main:
                        pos = -1;
                        break;
                    case R.id.nav_imc:
                        pos = 8;
                        break;
                    case R.id.nav_met:
                        pos = 9;
                        break;
                }
                selectItem(pos);

                return true;
            }
        });
        // mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        //  mDrawerList.setAdapter(new ArrayAdapter<>(this,
        //         android.R.layout.simple_list_item_1, footypes));
        // Set the list's click listener
        //  mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        Fragment fragment = new Fragment_main();
        Bundle args = new Bundle();

        args.putSerializable("seleccionados", seleccionados);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.content_pane, fragment).commit();


    }

    public void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        switch (position){
            case -1:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Fragment fragment = new Fragment_main();
                Bundle args = new Bundle();

                args.putSerializable("seleccionados", seleccionados);
                fragment.setArguments(args);
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_pane, fragment).commit();
                break;
            case 8:
                Fragment fragmentimc = new IMC();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_pane,fragmentimc).addToBackStack("ini").commit();
                break;
            case 9:
                break;
            default:
                Fragment fragmentm = new FragmentoVistaAlimentos();
                Bundle argss = new Bundle();
                argss.putInt("posicion", position);
                argss.putSerializable("seleccionados", seleccionados);
                fragmentm.setArguments(argss);

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager1 = getSupportFragmentManager();

                fragmentManager1.beginTransaction()
                        .replace(R.id.content_pane, fragmentm).addToBackStack("inicial").commit();


                // Highlight the selected item, update the title, and close the drawer
                //mDrawerList.setItemChecked(position, true);
                setTitle(footypes[position]);
                //mDrawerLayout.closeDrawer(mDrawerList);
                Log.i("INFO_ALIMENTOS", "Posicion: " + position);
                break;
        }

    }

    // con este metodo puedo gestionar lo que hacer si le doy a back, si hay algun fragment en el backstack vuelvo al main
    // cuando vuelvo al main limpio el backstack
    // si estoy en el main, el backstack estará vacío, saldra de la app
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }else {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Fragment fragment = new Fragment_main();
            Bundle args = new Bundle();

            args.putSerializable("seleccionados", seleccionados);
            fragment.setArguments(args);
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.content_pane, fragment).commit();

        }
    }

    /*public class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

        public void selectItem(int position) {
            // Create a new fragment and specify the planet to show based on position
            Fragment fragment = new FragmentoVistaAlimentos();
            Bundle args = new Bundle();
            args.putInt("posicion", position);
            fragment.setArguments(args);

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.content_pane, fragment).addToBackStack("fragmentini").commit();


            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(footypes[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            Log.i("INFO_ALIMENTOS", "Posicion: " + position);
        }


    }*/
}


