package adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuadrante.R;

import java.util.List;

import clases.Companiero;
import clases.DiaUsuario;
import clases.Usuario;
import viewModel.UserViewModel;

public abstract   class AdaptadorCompis extends RecyclerView.Adapter<AdaptadorCompis.CompisViewHolder> {

    private List<Companiero> listaCompanieros;




    public AdaptadorCompis (List<Companiero> mlistaCompanieros){
        this.listaCompanieros = mlistaCompanieros;
    }

    public static class CompisViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_nombre_compi,tv_nombre_alias,tv_tlfono_compi,tv_dias;
        private ImageView iv_home_dia_solicitado, iv_home_dia_pendiente;
        private LinearLayout linearLayout;
        //UserViewModel
        private UserViewModel userViewModel;
        private Usuario user;

        public CompisViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nombre_compi = itemView.findViewById(R.id.tvNombreCompi);
            tv_nombre_alias = itemView.findViewById(R.id.tvAliasCompi);
            tv_tlfono_compi = itemView.findViewById(R.id.tvNumTlfnoCompi);

            //Imagen view informativos
            iv_home_dia_solicitado= itemView.findViewById(R.id.iv_home_dia_solicitado);
            iv_home_dia_pendiente =  itemView.findViewById(R.id.iv_home_dia_pendiente);

            //linear
            linearLayout = itemView.findViewById(R.id.linear_home_foto);

            //obtener el userViewModel
            userViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(UserViewModel.class);
            user = userViewModel.getUser();




            tv_dias = itemView.findViewById(R.id.tvDias);

        }

        public void bindCompi(Companiero compi){

            //rellenamos los views
            tv_nombre_compi.setText(compi.getNombre());
            tv_nombre_alias.setText(compi.getAlias());
            tv_tlfono_compi.setText(compi.getNumTelefono());
            tv_dias.setText(String.valueOf(compi.getSumatorio_dias()));

            iv_home_dia_pendiente.setVisibility(View.INVISIBLE);
            iv_home_dia_solicitado.setVisibility(View.INVISIBLE);

            //comprobamos la lista de dias
            comprobarListaDias(compi,user);


        }

        /**
         * Recorre la lista de días del compañero para ver si hay días pendientes
         * @param compi
         */

        private void comprobarListaDias(Companiero compi, Usuario user) {

            for(DiaUsuario diaUsuario : compi.getListaDias()){

                if(diaUsuario.isSolicitado()){
                    iv_home_dia_pendiente.setVisibility(View.VISIBLE);
                    linearLayout.setClickable(true);
                }
            }for(DiaUsuario diaUsuario: user.getListaDias()){
                if(diaUsuario.isSolicitado()){
                    iv_home_dia_solicitado.setVisibility(View.VISIBLE);
                }
            }
        }
    }



    @NonNull
    @Override
    public CompisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.compis_list
        ,parent,false);


        CompisViewHolder compisViewHolder = new CompisViewHolder(itemView);
        return  compisViewHolder;

    }




    @Override
    public void onBindViewHolder(@NonNull CompisViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        Companiero companiero = listaCompanieros.get(position);
        holder.bindCompi(companiero);
    }

    @Override
    public int getItemCount() {
        return listaCompanieros.size();
    }


}
