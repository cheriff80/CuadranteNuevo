package com.example.cuadrante.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuadrante.R;

import java.util.List;

import adaptadores.AdaptadorCompis;
import clases.Companiero;
import clases.Usuario;
import viewModel.UserViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Usuario usuario;
    private static final String TAG = "Cuadrante" ;

    //parte UserViewModel
    private UserViewModel userViewModel;



    //parte recyclerView
    private RecyclerView recyclerView;
    private List<Companiero> listaCompis;
    private AdaptadorCompis adaptadorCompis;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

       //  userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);

    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        usuario = userViewModel.getUser();

        if(usuario != null){
            if(usuario.getListaCompas().size() != 0){

                listaCompis = usuario.getListaCompas();
                recyclerView = getActivity().findViewById(R.id.listaCompas);
                adaptadorCompis = new AdaptadorCompis(listaCompis) {
                    @Override
                    public void onBindViewHolder(@NonNull CompisViewHolder holder, int position) {

                    }
                };

                recyclerView.setAdapter(adaptadorCompis);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL,false));
            }
        }

    }



    @Override
    public void onStart() {
        super.onStart();




    }

    @Override
    public void onResume() {
        super.onResume();

    }



}
