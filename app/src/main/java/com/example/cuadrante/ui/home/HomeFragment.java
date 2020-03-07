package com.example.cuadrante.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cuadrante.InterfaceComunnication;
import com.example.cuadrante.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import clases.Usuario;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Usuario user;
    private static final String TAG = "EmailPassword" ;
    private InterfaceComunnication comunnication;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        comunnication = (InterfaceComunnication) context;




    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);





        obtenerUsuario();




    }



    @Override
    public void onStart() {
        super.onStart();


        this.user = comunnication.pasarUsuarioHome(user);


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
                .collection("users").document(idUsuario)
                .collection("listaCompis").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {



                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){


                }
            }
        });



    }

    public void nuevoUsuario (String alias, String apellidos, String nombre, String id, String numTelefono){




    }


    public void obtenerDatosUsu (Usuario usu){



    }

    public void log ( String mensaje){

        Log.d(TAG,mensaje);
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
