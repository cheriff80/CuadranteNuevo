package com.example.cuadrante;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import clases.Usuario;

public class PantallaAutenticacion extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    //Elementos de la pantalla de Autenticación
    private EditText mEmail;
    private EditText mPassword;
    private Button mInicio;
    private Button mRegistro;
    private Usuario baseUsuario;

    //Inicio autenticación
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Vistas
        mEmail = findViewById(R.id.etEmail);
        mPassword = findViewById(R.id.etPass);
        findViewById(R.id.btInicio).setOnClickListener(this);//método para los botones
        findViewById(R.id.btRegistro).setOnClickListener(this);

        //[START inicio_autenticacion]
        mAuth = FirebaseAuth.getInstance();
        //[END autenticacion]
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Chequeamos si el usuario está reconocido y actualizamos la UI de acuerdo a ésto
        FirebaseUser usuarioActual = mAuth.getCurrentUser();
        updateUI(usuarioActual);
    }


    /**
     * Método que actualiza los datos de la pantalla
     * @param usuarioActual
     */
    private void updateUI(FirebaseUser usuarioActual){

        if(usuarioActual != null)
        {

        }


    }



    /**
     * Inicia la aplicación con el nombre del usuario y el password
     * @param email
     * @param password
     */

    private void inicio(String email, String password) {

        Log.d(TAG, "signIn: " +email);
        if(!validarFormulario()){
            return;
        }

        //Comenzamos la autenticación con email y password
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //usuario autenticado y cargamos la info del usuario
                    Log.d(TAG, "signInWithEmail: success");
                    FirebaseUser usuario = mAuth.getCurrentUser();
                    startActivity(new Intent(PantallaAutenticacion.this, pagina_principal.class));

                }else{
                    //si falla la autenticación mostramos mensaje al usuario
                    Log.w(TAG, "signInWithEmail failure", task.getException());
                    Toast.makeText(PantallaAutenticacion.this, "Fallo autenticación.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });


    }

    /**
     * Método para validar el formulario
     * @return
     */
    private boolean validarFormulario() {

        boolean valido = true;

        String email = mEmail.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            mEmail.setError("Campo requerido.");
            valido = false;
        }else
        {
            mEmail.setError(null);
        }

        String password = mPassword.getText().toString();
        if(TextUtils.isEmpty(password))
        {
            mPassword.setError("Campo requerido.");
            valido = false;
        }else{
            mPassword.setError(null);

        }

        return valido;

    }

    @Override
    public void onClick(View v) {

        int i = v.getId(); //obtengo el id de la vista

        if(i == R.id.btInicio){
            inicio(mEmail.getText().toString(), mPassword.getText().toString());
            Intent intent = new Intent(PantallaAutenticacion.this,pagina_principal.class);
            startActivity(intent);
        }

        //acceso a la pantalla registro
        if( i == R.id.btRegistro){
            Intent intent = new Intent(PantallaAutenticacion.this, Registro.class);
            startActivity(intent);
        }

    }
}
