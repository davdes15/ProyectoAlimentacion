package com.example.davdes.proyectoalimentacion;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.davdes.proyectoalimentacion.Objetos.Alimento;
import com.google.android.material.navigation.NavigationView;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private String[] footypes;
    private DrawerLayout mDrawerLayout;
    ArrayList<Alimento> seleccionados = new ArrayList<>(0) ;
    //private ListView mDrawerList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
                switch (id){
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
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.content_pane, fragment).commit();


    }
    public void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new FragmentoVistaAlimentos();
        Bundle args = new Bundle();
        args.putInt("posicion", position);
        args.putSerializable("seleccionados",seleccionados);
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.content_pane, fragment).addToBackStack("fragmentini").commit();


        // Highlight the selected item, update the title, and close the drawer
        //mDrawerList.setItemChecked(position, true);
        setTitle(footypes[position]);
        //mDrawerLayout.closeDrawer(mDrawerList);
        Log.i("INFO_ALIMENTOS", "Posicion: " + position);
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


