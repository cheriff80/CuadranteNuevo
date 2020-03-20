package com.example.cuadrante;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import clases.Usuario;
import viewModel.UserViewModel;

public class PantallaAutenticacion extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    //Elementos de la pantalla de Autenticación
    private EditText mEmail;
    private EditText mPassword;
    private Button mInicio;
    private Usuario baseUsuario;

    //UserViewModel
    private UserViewModel userViewModel;

    //Inicio autenticación
    private FirebaseAuth mAuth;
    private FirebaseUser usuarioActual;

    //progressbar
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Vistas
        mEmail = findViewById(R.id.etEmail);
        mPassword = findViewById(R.id.etPass);

        findViewById(R.id.btInicio).setOnClickListener(this);//método para los botones

        progressBar = findViewById(R.id.pb_pantalla_inicio);

        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setMax(100);


    }

    @Override
    protected void onStart() {
        super.onStart();


        mAuth = FirebaseAuth.getInstance();
 }

    @Override
    protected void onRestart() {
        super.onRestart();



    }

    @Override
    protected void onPause() {
        super.onPause();

        mEmail.setText("");
        mPassword.setText("");
        baseUsuario = null;
        usuarioActual = null;
        mAuth = null;

    }


    /**
     * Inicia la aplicación con el nombre del usuario y el password
     *
     * @param email
     * @param password
     */

    private void inicio(String email, String password) {

        Log.d(TAG, "signIn: " + email);
        if (!validarFormulario()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();

        //Comenzamos la autenticación con email y password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()) {

                            progressBar.setProgress(50);
                            //usuario autenticado y cargamos la info del usuario
                            Log.d(TAG, "signInWithEmail: success");
                            usuarioActual = mAuth.getCurrentUser();
                            obtenerUsuario(usuarioActual.getUid());

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //si falla la autenticación mostramos mensaje al usuario
                Log.w(TAG, "signInWithEmail failure", e.getCause());
                Toast.makeText(PantallaAutenticacion.this, "Fallo autenticación",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Método para validar el formulario
     *
     * @return
     */
    private boolean validarFormulario() {

        boolean valido = true;

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Campo requerido.");
            valido = false;
        } else {
            mEmail.setError(null);
        }

        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Campo requerido.");
            valido = false;
        } else {
            mPassword.setError(null);

        }

        return valido;

    }


    public void obtenerUsuario(String idUsuario) {

    //inicio la conexión con la BBDD

        FirebaseFirestore.getInstance()
                .collection("users").document(idUsuario)
                .get()

                       .continueWithTask(new Continuation<DocumentSnapshot, Task<DocumentSnapshot>>() {
                            @Override
                            public Task<DocumentSnapshot> then(@NonNull Task<DocumentSnapshot> task) throws Exception {
                                 if(task.isSuccessful()){
                                  Log.d(TAG, "Task succesfull");
                                      return task;
                                 }else{
                                      Toast.makeText(PantallaAutenticacion.this,
                                              "Imposible conectar con Base de Datos",Toast.LENGTH_LONG).show();
                                      task.getException();

                                  }
                                 return task;
                              }
                         })

         .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                 progressBar.setProgress(100);
                 Log.d(TAG, "onSuccess listener sin base usuario");
                 baseUsuario =  task.getResult().toObject(Usuario.class);
                 Log.d(TAG, "onSucess con base usuario");
                 Intent intent = new Intent(PantallaAutenticacion.this, Pagina_principal.class);
                 if (baseUsuario != null) {
                     intent.putExtra("usuario", baseUsuario);
                     startActivity(intent);
                     //mAuth.signOut();
                     progressBar.setVisibility(View.INVISIBLE);
                 }

             }
         }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //si falla la autenticación mostramos mensaje al usuario
                Log.w(TAG, "authentication failure", e.getCause());
                Toast.makeText(PantallaAutenticacion.this, "Fallo usuario",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(PantallaAutenticacion.this,Pantalla_inicio.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        int i = v.getId(); //obtengo el id de la vista

        if (i == R.id.btInicio) {
            inicio(mEmail.getText().toString(), mPassword.getText().toString());
        }

    }

}
