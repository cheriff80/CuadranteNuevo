package fragmentos_navegacion.aniadir_companiero;

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

import com.example.cuadrante.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import clases.Companiero;

public class Aniadir_companiero extends Fragment {

    private TextView tvNombreCompa,tvEmailCompa,tvErrorTlfno;
    private EditText numTlfnoCompa;
    private ImageButton telefono;
    private Button ibAniadir;

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

        //creo la función cuandno apreta al botón del teléfono
        telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numTlfnoCompa.getText().length() != 8 ){

                    //busco con el número introducido un documento en Fibase con ese número
                    comprobarTelefono(numTlfnoCompa.getText().toString());


                }else{

                    errorNumTlfno("Debe incluir un número de teléfono");
                    Toast.makeText(getActivity(), "Introduzca un número de teléfono", Toast.LENGTH_LONG).show();

                }
            }
        });







    }

    public void comprobarTelefono(String numTelefono){

        //inicio la conexión con la BBDD
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        //ejecuto la búsqueda que me devuelva el nombre y correo del compañero
        firebaseFirestore.collection("users")
                .whereEqualTo("numTelefono", numTelefono).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documento : task.getResult()){

                        Companiero compi = documento.toObject(Companiero.class);

                        rellenarCamposNombreEmail(compi.getNombre(),compi.getAlias());

                    }
                }else{

                    errorNumTlfno("El número de teléfono móvil no está en nuestra base de datos");
                    Toast.makeText(getActivity(),"No se puede completar la búsqueda",Toast.LENGTH_LONG).show();
                    try {
                        this.wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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

    public void rellenarCamposNombreEmail (String nombre, String email){

        tvNombreCompa.setText(nombre);
        tvEmailCompa.setText(email);
    }



}


