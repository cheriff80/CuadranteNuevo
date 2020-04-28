package fragmentos_navegacion.eliminar_companiero;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuadrante.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import adaptadores.AdaptadorCompisEliminar;
import clases.Companiero;
import clases.Usuario;
import viewModel.UserViewModel;

public class eliminar_companiero extends Fragment implements View.OnClickListener {


    private Usuario usuario;
    private List<Companiero> listaCompis;

    //Views
    private Button bt_eliminar_compi;

    //ViewModel
    private EliminarCompanieroViewModel mViewModel;
    private UserViewModel userViewModel;

    //RecyclerView
    private RecyclerView recyclerView;
    private AdaptadorCompisEliminar adaptadorCompisEliminar;

    private int indice_compi_eliminar;

    //Firebase
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;







    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.eliminar_companiero_fragment, container, false);


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EliminarCompanieroViewModel.class);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        usuario = userViewModel.getUser();
        listaCompis = usuario.getListaCompas();

        bt_eliminar_compi = getActivity().findViewById(R.id.bt_eliminar);


        //Llenamos el RecyclerView
        recyclerView = getActivity().findViewById(R.id.lista_compas_eliminar);


        adaptadorCompisEliminar = new AdaptadorCompisEliminar(listaCompis) {
            @Override
            public void onBindViewHolder(@NonNull CompisEliminarViewHolder holder, int position) {

            }
        };

        recyclerView.setAdapter(adaptadorCompisEliminar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()
                ,LinearLayoutManager.VERTICAL,false));





    }

    @Override
    public void onStart() {
        super.onStart();

        bt_eliminar_compi.setOnClickListener(this);



        //inicio la conexíon con firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();



        //inicio la conexión con la BBDD
        firebaseFirestore = FirebaseFirestore.getInstance();

        String idUsuario = firebaseAuth.getUid();

        firebaseUser = firebaseAuth.getCurrentUser();
    }

    @Override
    public void onClick(View v) {

        //borro el usuario que esta en la posición
        usuario.getListaCompas().remove(adaptadorCompisEliminar.getIndice_fila());
        adaptadorCompisEliminar.notifyItemRemoved(adaptadorCompisEliminar.getIndice_fila());

        //cambio los datos en el UserViewModel
        userViewModel.loadUser(usuario);



        //cambio los datos en la base de datos

        firebaseFirestore.collection("users").document(firebaseUser.getUid())
                .set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                       @Override
                                                       public void onSuccess(Void aVoid) {
                                                           Log.d("Cuadrante","usuario eliminado");
                                                           FragmentManager fragmentManager = getParentFragmentManager();
                                                           fragmentManager.beginTransaction();
                                                           fragmentManager.popBackStack();
                                                       }
                                                   }
        );

    }
}
