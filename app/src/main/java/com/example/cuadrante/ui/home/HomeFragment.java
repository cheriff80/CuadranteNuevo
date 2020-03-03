package com.example.cuadrante.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cuadrante.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import clases.Usuario;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView tvNombre,tvNumTelefono;
    private Usuario usuario;
    private static final String TAG = "EmailPassword" ;





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);






    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        //con la actividad creada busco el nombre
        tvNombre = getActivity().findViewById(R.id.tvUsuario);
        tvNumTelefono = getActivity().findViewById(R.id.tvnumRelefono);

        obtenerUsuario();


    }

    @Override
    public void onStart() {
        super.onStart();



    }

    @Override
    public void onResume() {
        super.onResume();


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

                Usuario usuario = documentSnapshot.toObject(Usuario.class);

                HomeFragment.this.setUsuario(usuario);


                obtenerDatosUsu(usuario);

                obtenerDatosUsu(documentSnapshot.toObject(Usuario.class));
            }
        });


    }


    public void obtenerDatosUsu (Usuario usu){


    }

    public void log ( String mensaje){

        Log.d(TAG,mensaje);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
