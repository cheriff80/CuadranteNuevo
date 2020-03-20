package com.example.cuadrante;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import clases.Usuario;
import viewModel.UserViewModel;

public class Pagina_principal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Usuario user;
    private TextView tv_NavNombre, tv_NavTelefono;

    private UserViewModel userViewModel;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        user = (Usuario) getIntent().getSerializableExtra("usuario");


        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);



        userViewModel.loadUser(user);


        //método que incluye la navegación
        barraNavegacion();

    }

    @Override
    protected void onStart() {


        super.onStart();

        user = (Usuario) getIntent().getSerializableExtra("usuario");


        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userViewModel.loadUser(user);


        if(user != null){

            //cambio los Text Views de al barra de navegación
            NavigationView navigationView = findViewById(R.id.nav_view);
            tv_NavNombre = navigationView.getHeaderView(0).findViewById(R.id.tvNavNombre);
            tv_NavTelefono = navigationView.getHeaderView(0).findViewById(R.id.tvNavTelefono);
            //String nombre_apellidos = user.getNombre()+" "+user.getApellidos();
            tv_NavNombre.setText(user.getNombre()+" "+user.getApellidos());
            tv_NavTelefono.setText(user.getNumTelefono());
        }

    }




    /**
     * Infla el menu
     * @param menu
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pagina_principal, menu);
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void barraNavegacion(){

        //Navegación lateral
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.aniadir_companiero, R.id.eliminar_companiero,
                R.id.devolver_dia, R.id.solicitar_dia)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }






}
