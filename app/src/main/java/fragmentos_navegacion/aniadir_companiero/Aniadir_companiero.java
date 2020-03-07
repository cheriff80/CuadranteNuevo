package fragmentos_navegacion.aniadir_companiero;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.lifecycle.ViewModelProviders;

import com.example.cuadrante.Pagina_principal;
import com.example.cuadrante.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import clases.Companiero;

public class Aniadir_companiero extends Fragment {

    private TextView tvNombreCompa,tvEmailCompa,tvErrorTlfno;
    private EditText numTlfnoCompa;
    private ImageButton telefono;
    private Button ibAniadir;
    private boolean compa_en_firebase= false;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private Companiero companiero;

    private AniadirCompanieroViewModel mViewModel;

    public static Aniadir_companiero newInstance() {
        return new Aniadir_companiero();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.aniadir_companiero_fragment, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AniadirCompanieroViewModel.class);

        //declaro las vistas
        numTlfnoCompa = (EditText) getActivity().findViewById(R.id.etNumTelefonoCompa);
        tvNombreCompa = (TextView) getActivity().findViewById(R.id.tvNombreCompa);
        tvEmailCompa = (TextView) getActivity().findViewById(R.id.tvEmailCompa);
        tvErrorTlfno = (TextView) getActivity().findViewById(R.id.tvErrorNumTlfno);

        //botones
        ibAniadir = (Button) getActivity().findViewById(R.id.btAniadirCompa);
        telefono = (ImageButton) getActivity().findViewById(R.id.ibTelefono);

        //inicio la conexíon con firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        //inicio la conexión con la BBDD
        firebaseFirestore = FirebaseFirestore.getInstance();

        //creo la función cuandno apreta al botón del teléfono
        telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvErrorTlfno.setText("");

                if(!numTlfnoCompa.getText().toString().isEmpty() ){

                    //busco con el número introducido un documento en Fibase con ese número
                    comprobarTelefono(numTlfnoCompa.getText().toString());


                }else{

                    errorNumTlfno("Debe incluir un número de teléfono");
                    Toast.makeText(getActivity(), "Introduzca un número de teléfono", Toast.LENGTH_LONG).show();

                }
            }
        });

        //función para añadir el compañero a Firestore
        ibAniadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (compa_en_firebase) {

                    firebaseFirestore.collection("users")
                            .document(firebaseAuth.getCurrentUser().getUid())
                            .collection("listaCompis").add(companiero).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Toast.makeText(getActivity(), "Compañero añadido"
                                    , Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getContext(), Pagina_principal.class);
                            startActivity(intent);
                        }
                    });
                }
                else{

                    Toast.makeText(getActivity(), "No se puede añadir compañero"
                            , Toast.LENGTH_SHORT).show();
                }


            }

        });
    }

    public void comprobarTelefono(String numTelefono){



        //ejecuto la búsqueda que me devuelva el nombre y correo del compañero
        firebaseFirestore.collection("users")
                .whereEqualTo("numTelefono", numTelefono).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documento : task.getResult()){

                        Companiero compi = documento.toObject(Companiero.class);


                        rellenarCamposNombreEmail(compi.getNombre(),compi.getAlias(),compi);

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

        this.companiero = compa;

        tvNombreCompa.setText(nombre);
        tvEmailCompa.setText(email);

        compa_en_firebase = true;
    }



}


