package com.example.sauld.appconocemipais.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sauld.appconocemipais.BottomNavigationViewHelper;
import com.example.sauld.appconocemipais.Fragments.Home_Recy_Fragment;
import com.example.sauld.appconocemipais.Fragments.UsuariosFragment;
import com.example.sauld.appconocemipais.R;

public class MainActivity extends AppCompatActivity implements
        Home_Recy_Fragment.OnFragmentInteractionListener,
        UsuariosFragment.OnFragmentInteractionListener {

    private Home_Recy_Fragment home_recy_fragment;
    private UsuariosFragment usuariosFragment;
    private FragmentTransaction fragmentTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home_recy_fragment =Home_Recy_Fragment.getInstance();
        usuariosFragment = UsuariosFragment.getInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedorPanel, home_recy_fragment).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigatio);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()) {
                    case R.id.homeNav:
                        item.setChecked(true);
                        fragmentTransaction.replace(R.id.contenedorPanel, home_recy_fragment);


                        break;
                    case R.id.userNav:
                        item.setChecked(true);
                        fragmentTransaction.replace(R.id.contenedorPanel, usuariosFragment);
                        break;

                }
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void mandarBundel(Bundle bundle) {

    }


}
