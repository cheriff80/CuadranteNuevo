package com.example.cuadrante;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import clases.Usuario;

public class Registro extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "EmailPassword" ;
    //Vistas
    private EditText etNombre,etApellidos,etEmail,etPassword,etNumTelefono,etAlias,etPasswordRepe;
    private TextView tvPassRepe;
    private Button btRegistro,btInicio;

    //ProgressDialog
    private ProgressBar barraProgreso;

    //FireBase Autenticathion
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //FireBase Cloud Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Usuario
    private Usuario usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Asocia las vistas
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etNumTelefono = findViewById(R.id.etTelefono);
        etAlias = findViewById(R.id.etAlias);
        etPasswordRepe = findViewById(R.id.etRepitePassword);

        tvPassRepe = findViewById(R.id.tvRepitePassword);

        btRegistro=findViewById(R.id.btRegistro);
        btInicio=findViewById(R.id.btInicio);

        btRegistro.setOnClickListener(this);


        barraProgreso = findViewById(R.id.progressBar);

        //[START inicio FireBase Auth]
         mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }

    /**
     * Crea una cuenta con el mail y el password
     * @param email
     * @param password
     */

    private void crearCuenta (String email, String password){

        Log.d(TAG,"crearCuenta:"+email);
        if(!validarFormulario()){
            Log.d(TAG,"formulario no validado");
            return;
        }
        Log.d(TAG,"formulario  validado");
        barraProgreso.setVisibility(View.VISIBLE);


        //[START registro con email y pass]
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            barraProgreso.setVisibility(View.INVISIBLE);
                            Log.d(TAG,"creadoUsuario");

                            //Obtengo el usuario de Firebase
                            FirebaseUser usuario = mAuth.getCurrentUser();
                            emailVerificacion(usuario);

                            //updateUI(usuario);
                            barraProgreso.setVisibility(View.INVISIBLE);
                            aniadirUsuario(etAlias.getText().toString(),
                                    etApellidos.getText().toString(),
                                    usuario.getUid(),etNombre.getText().toString(),
                                    etNumTelefono.getText().toString(),
                                    db,usuario);
                            //db.collection("usuarios").add(user);
                            Toast.makeText(Registro.this, "Usuario creado, verifique" +
                                    " correo enviado.",Toast.LENGTH_LONG);
                            startActivity(new Intent(Registro.this, PantallaAutenticacion.class));
                        }else{
                            Log.w(TAG,"entrada con email: fallo", task.getException());
                            Toast.makeText(Registro.this, "Error en la ejecución",Toast.LENGTH_LONG);
                            return;
                        }

                    }
                });
   }

    /**
     * Envío de email de verificación
     * @param usuario
     * @return
     */

    private Boolean[] emailVerificacion(FirebaseUser usuario){

        final Boolean[] emailValido = {true};
        Log.d(TAG,"verificandoEmail");

        usuario.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    emailValido[0] = false;
                }

            }
        });
        Log.d(TAG,"EmailVerificado");

        return emailValido;
    }



    /**
     * Método para validar el formulario
     * @return
     */
    private boolean validarFormulario() {

        boolean valido = true;

        String email = etEmail.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            etEmail.setError("Campo requerido.");
            valido = false;
        }else
        {
            etEmail.setError(null);
        }

        String password = etPassword.getText().toString();
        if(TextUtils.isEmpty(password))
        {
            etPassword.setError("Campo requerido.");
            valido = false;
        }else{
            etPassword.setError(null);
        }

        String rPassword = etPasswordRepe.getText().toString();
        if(TextUtils.isEmpty(password))
        {
            etPasswordRepe.setError("Campo requerido.");
            valido = false;
        }else{
            etPassword.setError(null);
            if(!password.equals(rPassword)){
                etPasswordRepe.setError("Las contraseñas no son iguales.");
                valido=false;
            }
        }



        return valido;

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
     * Añade usuario a Clod Firestore, lo agrego como una clase usuario y el documento para
     * distinguirlo de los demás me lo dará el número de teléfono
     * @param id
     * @param nombre
     * @param apellidos
     * @param numTelefono
     * @param alias
     */
    public void aniadirUsuario(String alias, String apellidos, String id, String nombre
    , String numTelefono, FirebaseFirestore db,FirebaseUser user){

        usuario = new Usuario(alias, apellidos, id,nombre,numTelefono);
         final String idMetodo = id;



        /** Create a new user with a first and last name
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombre", nombre);
        usuario.put("id", id);
        usuario.put("apellidos", apellidos);
        usuario.put("numTelefono", numTelefono);
        usuario.put("alias", alias);
         **/

        // Add a new document with a generated ID
        db.collection("users").document(id)
                .set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                       @Override
                                                       public void onSuccess(Void aVoid) {
                                                        Log.d(TAG,"usuario "+idMetodo+" incluído");
                                                           Intent intent = new Intent(Registro.this,Pantalla_inicio.class);
                                                           startActivity(intent);
                                                       }
                                                   }
        );

                /**
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });**/
    }


    /**
     *
     * @param v
     */

    @Override
    public void onClick(View v) {



        int i = v.getId();
        if(i == R.id.btRegistro){
            crearCuenta(etEmail.getText().toString(),etPassword.getText().toString());

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Registro.this, Pantalla_inicio.class);
        startActivity(intent);
    }
}

