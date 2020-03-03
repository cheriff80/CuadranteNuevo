package com.example.cuadrante.ui.home;

import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import clases.Usuario;

public class HomeViewModel extends ViewModel {

    private Usuario usuario;
    private static final String TAG = "EmailPassword" ;
    private MutableLiveData<TextView> tvNombre;

    //FireBase Cloud Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseAuth fa = FirebaseAuth.getInstance();










    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public MutableLiveData<TextView> getTvNombre() {
        return tvNombre;
    }

    public void setTvNombre(MutableLiveData<TextView> tvNombre) {
        this.tvNombre = tvNombre;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }

    public FirebaseAuth getFa() {
        return fa;
    }

    public void setFa(FirebaseAuth fa) {
        this.fa = fa;
    }

    /**
     * Entra en Cloud Firestore y obtiene el usuario a partid de su UId
     * @return
     */

    public void obtenerUsuario() {


        //inicio la conexión con la BBDD
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Obtengo el UId del Usuario de la FireBase Auth
        FirebaseAuth fa = FirebaseAuth.getInstance();
        String idUsuario = fa.getCurrentUser().getUid();

        //Obtengo la referencia al documento
        DocumentReference docRef = db.collection("users").document(idUsuario);
        Log.d(TAG, "db obtenido");

        if(docRef.get().isSuccessful()){
            DocumentSnapshot documentSnapshot = docRef.get().getResult();
            obtenerDatosUsu(documentSnapshot.toObject(Usuario.class));
        }



    }

    public void obtenerDatosUsu (Usuario usu){

        this.usuario = usu;
    }
}