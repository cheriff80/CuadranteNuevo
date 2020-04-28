package fragmentos_navegacion.solicitar_dia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuadrante.R;

import java.util.List;

import adaptadores.AdaptadorPedirDia;
import clases.Companiero;
import clases.Usuario;
import viewModel.TelefonoView;
import viewModel.UserViewModel;

public class Solicitar_dia extends Fragment {

    private SolicitarDiaViewModel mViewModel;

    private Usuario usuario;
    private Companiero companiero;
    private List<Companiero> companieroList;

    //ViewModel
    private UserViewModel userViewModel;
    private TelefonoView telefonoView;

    //RecyclerView
    private RecyclerView recyclerView;
    private AdaptadorPedirDia adaptadorPedirDia;

    //Diálogo

    private FragmentManager fragmentManager;



    public static Solicitar_dia newInstance() {
        return new Solicitar_dia();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.solicitar_dia_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SolicitarDiaViewModel.class);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        telefonoView = ViewModelProviders.of(getActivity()).get(TelefonoView.class);

        usuario= userViewModel.getUser();
        companieroList = usuario.getListaCompas();





        //RecyclerView
        recyclerView = getActivity().findViewById(R.id.pedir_dia_lista);


         adaptadorPedirDia = new AdaptadorPedirDia(companieroList);
         recyclerView.setAdapter(adaptadorPedirDia);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()
                ,LinearLayoutManager.VERTICAL,false));



    }





    @Override
    public void onStart() {
        super.onStart();

    }


}
