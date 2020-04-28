package fragmentos_navegacion.aniadir_companiero;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.cuadrante.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import clases.Companiero;
import clases.Usuario;
import viewModel.UserViewModel;

public class Aniadir_companiero extends Fragment implements View.OnClickListener {

    private TextView tvNombreCompa,tvEmailCompa,tvErrorTlfno;
    private EditText numTlfnoCompa;
    private ImageButton telefono;
    private Button ibAniadir;
    private boolean compa_en_firebase= false;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Companiero companiero;
    private Usuario usuario;

    //parte UsuarioViewModel
    private UserViewModel userViewModel;



    private AniadirCompanieroViewModel mViewModel;

    private CountDownLatch countDownLatch= new CountDownLatch(1);



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);



    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.aniadir_companiero_fragment, container, false);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AniadirCompanieroViewModel.class);

        //declaro las vistas
        numTlfnoCompa = getActivity().findViewById(R.id.etNumTelefonoCompa);
        tvNombreCompa = getActivity().findViewById(R.id.tvNombreCompa);
        tvEmailCompa = getActivity().findViewById(R.id.tvEmailCompa);
        tvErrorTlfno = getActivity().findViewById(R.id.tvErrorNumTlfno);

        //botones
        ibAniadir = getActivity().findViewById(R.id.bt_ac_aniadir_compa);
        telefono = getActivity().findViewById(R.id.ibTelefono);



    }

    @Override
    public void onStart() {
        super.onStart();

        //inicio la conexíon con firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        //inicio la conexión con la BBDD
        firebaseFirestore = FirebaseFirestore.getInstance();

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        usuario = userViewModel.getUser();

        firebaseUser = firebaseAuth.getCurrentUser();

        detenerHilo(1000);

        //asigno el Onclick a los botones
        ibAniadir.setOnClickListener(this);
        telefono.setOnClickListener(this);

    }



    public void comprobarTelefono(String numTelefono){



        //ejecuto la búsqueda que me devuelva el nombre y correo del compañero
        firebaseFirestore.collection("users")
                .whereEqualTo("numTelefono", numTelefono).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documento : task.getResult()){

                        Companiero compa = documento.toObject(Companiero.class);



                        Companiero compi = new Companiero(compa.getAlias(),compa.getApellidos()
                        ,compa.getId(),compa.getNombre(),compa.getNumTelefono());

                        rellenarCamposNombreEmail(compi.getNombre(),compi.getAlias(),compi);

                        tvErrorTlfno.setText("COMPAÑERO EXISTE!!!");

                    }
                }else{

                    errorNumTlfno("El número de teléfono móvil no está en nuestra base de datos");
                    Toast.makeText(getActivity(),"No se puede completar la búsqueda",Toast.LENGTH_LONG).show();
                    return;
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(),"No se puede completar la búsqueda",Toast.LENGTH_LONG).show();

            }
        });




    }

    public void errorNumTlfno(String text){

        tvErrorTlfno.setText(text);

    }

    public void rellenarCamposNombreEmail (String nombre, String email, Companiero compa){

        companiero = compa;

        tvNombreCompa.setText(nombre);
        tvEmailCompa.setText(email);

        compa_en_firebase = true;
    }

    public void click_boton_tlfno(){



                tvErrorTlfno.setText("");

                if(!numTlfnoCompa.getText().toString().isEmpty() ){

                    //busco con el número introducido un documento en Fibase con ese número
                    comprobarTelefono(numTlfnoCompa.getText().toString());


                }else{

                    errorNumTlfno("Debe incluir un número de teléfono");
                    Toast.makeText(getActivity(), "Introduzca un número de teléfono", Toast.LENGTH_LONG).show();

                }
            }

    public void aniadir_compa(){

        final String TAG = "Cuadrante";

        //[START inicio_autenticacion]
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //[END autenticacion]




        if (companiero != null) {

            if (usuario.getListaCompas().size() == 0) {
                //añado el compañero a la lista de compañeros
                usuario.getListaCompas().add(companiero);


                //paso el usuario con la nueva lista de compañeros a la base de datos
                firebaseFirestore.collection("users").document(firebaseUser.getUid())
                        .set(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "usuario incluído");
                        Toast.makeText(getActivity(), "Compañero añadido", Toast.LENGTH_LONG).show();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        fragmentManager.beginTransaction();
                        fragmentManager.popBackStack();


                    }
                });


            } else {
                for (Companiero companiero_lista : usuario.getListaCompas()) {

                    //compruebo que no exista ya el compañero en la BBDD
                    if (!companiero_lista.getNumTelefono().equals(companiero.getNumTelefono())) {

                        //añado el compañero a la lista de compañeros
                        usuario.getListaCompas().add(companiero);

                        //lo cargo al userViewModel
                        userViewModel.loadUser(usuario);

                        //paso el usuario con la nueva lista de compañeros a la base de datos
                        firebaseFirestore.collection("users").document(firebaseUser.getUid())
                                .set(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "usuario incluído");
                                Toast.makeText(getActivity(), "Compañero añadido", Toast.LENGTH_LONG).show();
                                FragmentManager fragmentManager = getParentFragmentManager();
                                fragmentManager.beginTransaction();
                                fragmentManager.popBackStack();
                            }
                        });


                    } else {
                        Toast.makeText(getActivity(), "Usuario ya es compañero", Toast.LENGTH_LONG).show();
                    }
                }

            }

        }else{

                errorNumTlfno("Usuario no identificado");

                    }

        //vuelvo a la página home
        //Intent intent = new Intent(getContext(), Pagina_principal.class);
        //startActivity(intent);


        }

    public void detenerHilo(int milisegundos){

        try {

            countDownLatch.await(milisegundos, TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
            int n_vista = v.getId();
         if(n_vista == R.id.ibTelefono){
             click_boton_tlfno();
         }
         if(n_vista == R.id.bt_ac_aniadir_compa){
             aniadir_compa();
         }

    }
}


