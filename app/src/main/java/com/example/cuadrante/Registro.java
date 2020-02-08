package com.example.cuadrante;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Registro extends AppCompatActivity {

    //Vistas
    private EditText etNombre,etApellidos,etEmail,etPassword,etNumTelefono,EtAlias;
    private Button registro;

    //FireBase Autenticathion
    FirebaseAuth mAuth;

    //FireBase Cloud Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }
}
