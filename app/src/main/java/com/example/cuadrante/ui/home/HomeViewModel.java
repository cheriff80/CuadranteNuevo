package com.example.cuadrante.ui.home;

import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
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


}