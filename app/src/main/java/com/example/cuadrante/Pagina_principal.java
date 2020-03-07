package com.example.cuadrante;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cuadrante.ui.home.HomeFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import clases.Usuario;

public class Pagina_principal extends AppCompatActivity implements InterfaceComunnication {

    private AppBarConfiguration mAppBarConfiguration;
    private HomeFragment homeFragment;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        obtenerUsuario();

        //método que incluye la navegación
        barraNavegacion();





    }

    @Override
    protected void onStart() {
        super.onStart();

        pasarUsuarioHome(user);

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

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    @Override
    public Usuario pasarUsuarioHome(Usuario user) {

        return user;
    }

    public void obtenerUsuario() {

        //Obtengo el UId del Usuario de la FireBase Auth
        FirebaseAuth fa = FirebaseAuth.getInstance();
        String idUsuario = fa.getCurrentUser().getUid();

        //inicio la conexión con la BBDD
        FirebaseFirestore.getInstance()
                .collection("users").document(idUsuario).get().continueWithTask(new Continuation<DocumentSnapshot, Task<DocumentSnapshot>>() {
            @Override
            public Task<DocumentSnapshot> then(@NonNull Task<DocumentSnapshot> task) throws Exception {

                Task<DocumentSnapshot> busqueda = task;
                if(task.isComplete()){
                    return task;}
                else{
                    task.getException();
                }
                return task;

            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {



                Usuario user = documentSnapshot.toObject(Usuario.class);

                obtenerDatosUsu(user);



                pasarUsuarioHome(user);


            }
        });



    }
    public void obtenerDatosUsu (Usuario usu){

        this.user = usu;

    }
}
