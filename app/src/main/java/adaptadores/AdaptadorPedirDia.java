package adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuadrante.Activity_Dialogo;
import com.example.cuadrante.R;

import java.util.List;

import clases.Companiero;
import clases.Usuario;
import viewModel.TelefonoView;
import viewModel.UserViewModel;

public class AdaptadorPedirDia extends RecyclerView.Adapter<AdaptadorPedirDia.CompisPedirDiaViewHolder> implements View.OnClickListener {

    private List<Companiero> companieroList;
    private String  numeroTelefono;
    private ImageButton ib_pedir_dia;
    private FragmentManager fragmentManager;
    private View.OnClickListener listener;
    private TelefonoView telefonoView;
    private Context contexto;
    private Usuario user;
    private UserViewModel userViewModel;

    public AdaptadorPedirDia(List<Companiero> companieroList) {
        this.companieroList = companieroList;


    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public ImageButton getIb_pedir_dia() {
        return ib_pedir_dia;
    }

    public void setIb_pedir_dia(ImageButton ib_pedir_dia) {
        this.ib_pedir_dia = ib_pedir_dia;
    }

    public static class CompisPedirDiaViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_pedir_dia_nombre, tv_pedir_dia_telefono, tv_pedir_dia_dia;
        private ImageButton ib_pedir_dia_solicitar;
        private ConstraintLayout constraintLayout;
        private Context context;
        private FragmentManager fragmentManager;
        private String  numeroTelefono;
        private TelefonoView telefonoView;


        public CompisPedirDiaViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_pedir_dia_nombre = itemView.findViewById(R.id.pedir_dia_nombre);
            tv_pedir_dia_dia = itemView.findViewById(R.id.pedir_dia_dias);
            tv_pedir_dia_telefono = itemView.findViewById(R.id.pedir_dia_telefono);
            ib_pedir_dia_solicitar = itemView.findViewById(R.id.pedir_dia_iv_dialogo);

            constraintLayout = itemView.findViewById(R.id.pedir_dia_layout);

            context = itemView.getContext();

            telefonoView = ViewModelProviders.of((AppCompatActivity)context).get(TelefonoView.class);



        }

        public void bindCompa(Companiero companiero) {

            tv_pedir_dia_nombre.setText(companiero.getNombre());
            tv_pedir_dia_telefono.setText(companiero.getNumTelefono());
            tv_pedir_dia_dia.setText(String.valueOf(companiero.getSumatorio_dias()));

            numeroTelefono = companiero.getNumTelefono();

            fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();

        }
    }

    @NonNull
    @Override
    public AdaptadorPedirDia.CompisPedirDiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pedir_dia
                ,parent,false);

        contexto = parent.getContext();



        AdaptadorPedirDia.CompisPedirDiaViewHolder compisPedirDiaViewHolder =
                new AdaptadorPedirDia.CompisPedirDiaViewHolder(itemView);
        return  compisPedirDiaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CompisPedirDiaViewHolder holder, int position) {

        final Companiero companiero = companieroList.get(position);
        holder.bindCompa(companiero);




        ib_pedir_dia = holder.ib_pedir_dia_solicitar;
        fragmentManager = holder.fragmentManager;
        telefonoView = holder.telefonoView;
        contexto = holder.context;

        userViewModel = ViewModelProviders.of((AppCompatActivity)contexto).get(UserViewModel.class);
        user = userViewModel.getUser();





        ib_pedir_dia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroTelefono = holder.numeroTelefono;

                telefonoView.loadNumTelefono(numeroTelefono);

                Intent intent = new Intent(contexto.getApplicationContext(),Activity_Dialogo.class);
                intent.putExtra("compi",companiero);
                intent.putExtra("usuario", user);
                contexto.startActivity(intent);

            }


        });

    }

    @Override
    public void onClick(View v) {

        if(listener!=null){
            listener.onClick(v);
        }




    }

    @Override
    public int getItemCount() {
        return companieroList.size();
    }


}
