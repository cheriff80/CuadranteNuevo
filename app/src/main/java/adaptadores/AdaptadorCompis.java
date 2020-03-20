package adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuadrante.R;

import java.util.List;

import clases.Companiero;

public abstract   class AdaptadorCompis extends RecyclerView.Adapter<AdaptadorCompis.CompisViewHolder> {

    List<Companiero> listaCompanieros;

    public AdaptadorCompis (List<Companiero> mlistaCompanieros){
        this.listaCompanieros = mlistaCompanieros;
    }

    public static class CompisViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_nombre_compi,tv_nombre_alias,tv_tlfono_compi,tv_dias;

        public CompisViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nombre_compi = itemView.findViewById(R.id.tvNombreCompi);
            tv_nombre_alias = itemView.findViewById(R.id.tvAliasCompi);
            tv_tlfono_compi = itemView.findViewById(R.id.tvNumTlfnoCompi);
            tv_dias = itemView.findViewById(R.id.tvDias);

        }

        public void bindCompi(Companiero compi){


            tv_nombre_compi.setText(compi.getNombre());
            tv_nombre_alias.setText(compi.getAlias());
            tv_tlfono_compi.setText(compi.getNumTelefono());
            tv_dias.setText(String.valueOf(compi.getSumatorio_dias()));
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
