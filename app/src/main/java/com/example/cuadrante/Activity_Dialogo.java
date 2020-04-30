package com.example.cuadrante;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import clases.Companiero;
import clases.DiaUsuario;
import clases.Usuario;
import viewModel.UserViewModel;

public class Activity_Dialogo extends AppCompatActivity implements View.OnClickListener {

    private EditText et_activityDialogo_fechaIntro,et_activityDialogo_mensaje;
    private Button bt_solicitar,bt_cancelar;
    private DatePickerDialog picker;
    private Usuario user;

    //viewModel
    private UserViewModel userViewModel;

    //compañero que recibo del intent del adaptador pedirDia
    private Companiero companiero;

    //Dia
    private DiaUsuario dia;

    //FireBase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    private final String TAG = "Cuadrante";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__dialogo);
        setTitle(null);
        setActionBar(null);

        //instancio los elementos del layout
        et_activityDialogo_fechaIntro = findViewById(R.id.et_activityDialogo_fechaIntroducida);
        et_activityDialogo_mensaje = findViewById(R.id.et_activityDialogo_comentarioIn);
        bt_solicitar = findViewById(R.id.bt_activityDialogo_solicitar);
        bt_cancelar = findViewById(R.id.bt_activityDialogo_cancelar);


        //implemento el et para que no se pueda escribir en él
        et_activityDialogo_fechaIntro.setInputType(InputType.TYPE_NULL);

        //Onclick del texto fecha
        et_activityDialogo_fechaIntro.setOnClickListener(this);

        //obtengo el usuario del intent
        user = (Usuario) getIntent().getSerializableExtra("usuario");

        //obtengo el companiero del intent
        companiero = (Companiero) getIntent().getSerializableExtra("compi");

        //FireBase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        //OnClick boton solicitar
        bt_solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                solicitarDia();
            }
        });

        //OnClick botón cancelar
        bt_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        int i = v.getId();

        if(i == R.id.et_activityDialogo_fechaIntroducida){
            recogerFechaDatePicker();
        }
        if(i == R.id.bt_activityDialogo_solicitar ){

        }
        if(i == R.id.bt_activityDialogo_cancelar){
            onBackPressed();
        }
    }

    /**
     * Método para solicitar un día. Añade un día a la lista de días de compañero y lo añade en
     * FireBase
     */

    private void solicitarDia() {

        if(verficarCampos()){

            dia = new DiaUsuario();
            dia.setFecha(et_activityDialogo_fechaIntro.getText().toString());
            dia.setMensage(et_activityDialogo_mensaje.getText().toString());
            dia.setNumTelefonoAcepta(companiero.getNumTelefono());
            dia.setNumTelefonoSolicita(user.getNumTelefono());



            //añadimos el día a la lista de días del compañero en programa
            companiero.pedir_dias(dia);


            //añadimos el día a la lista de días del usuario
            user.pedir_dias(dia, user);


            user.getListaCompas().remove(buscarCompanieroLista());

            user.getListaCompas().add(companiero);


            //añadimos el día en la base de datos
            aniadirDiaCompiUsuario();
            aniadirDiaCompiFireBase(dia);
        }


    }

    /**
     * Inserta el día en la lista de días del Usuario Companiero en Firestore
     */

    private void aniadirDiaCompiUsuario(){

        DocumentReference docRef = firebaseFirestore.collection("users")
                .document(companiero.getId());

        docRef.update("listaDias", dia).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                //log y toast
                Log.d(TAG, "día usuario solicitado");
                Toast.makeText(getApplicationContext(), "día solicitado", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "error al solicitar el día");
                Toast.makeText(getApplicationContext(), "No se puede solicitar día", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });


    }

    /**
     * Inserta el día en la lista de días de la lista de companieros
     * @param dia
     */

    private void aniadirDiaCompiFireBase(DiaUsuario dia) {

        //buscarCompanieroLista();

        DocumentReference documentReference = firebaseFirestore.collection("users")
                .document(firebaseUser.getUid());
        documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                //log y toast
                Log.d(TAG, "día solicitado");
                Toast.makeText(getApplicationContext(), "día solicitado", Toast.LENGTH_LONG).show();

                //Vuelvo a la página principal
                Intent intent = new Intent(getBaseContext(),Pagina_principal.class);
                intent.putExtra("usuario",user);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "error al solicitar el día");
                Toast.makeText(getApplicationContext(), "No se puede solicitar día", Toast.LENGTH_LONG).show();

                onBackPressed();
            }
        });
    }

    /**
     * Devuelve el índice del companiero en la lista de companieros
     * @return
     */

    private int buscarCompanieroLista() {

        int id = -1;

        for(Companiero compi: user.getListaCompas() ){

            if(compi.getId().equals(companiero.getId())){
                id = user.getListaCompas().indexOf(compi);
            }
        }
          return  id;

    }

    /**
     * Verifica que los campos de texto de fecha y comentario no estén vacíos
     */

    private boolean verficarCampos() {

        String met_fechaIntro = et_activityDialogo_fechaIntro.toString();
        String met_mensaje = et_activityDialogo_mensaje.toString();
        boolean error = true;


        if(TextUtils.isEmpty(met_fechaIntro)){
            et_activityDialogo_fechaIntro.setError("campo requerido");
            error = false;
        }else{
            et_activityDialogo_fechaIntro.setError(null);
        }
        if(TextUtils.isEmpty(met_mensaje)){
            et_activityDialogo_mensaje.setError("campo requerido");
            error = false;
        }else{
            et_activityDialogo_mensaje.setError(null);
        }



        return error;

    }

    /**
     * Método para mostrar el diálogo para seleccionar la fecha
     */

    public void recogerFechaDatePicker(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(Activity_Dialogo.this,
                new DatePickerDialog.OnDateSetListener() {
                    /**
                     * @param view       the picker associated with the dialog
                     * @param year       the selected year
                     * @param month      the selected month (0-11 for compatibility with
                     *                   {@link Calendar#MONTH})
                     * @param dayOfMonth th selected day of the month (1-31, depending on
                     */
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        et_activityDialogo_fechaIntro.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }

                },year,month,day);
        picker.show();
    }





}

