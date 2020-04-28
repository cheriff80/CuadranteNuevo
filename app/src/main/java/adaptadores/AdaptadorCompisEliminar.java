package adaptadores;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuadrante.R;

import java.util.List;

import clases.Companiero;

public abstract class AdaptadorCompisEliminar extends RecyclerView.Adapter<AdaptadorCompisEliminar.CompisEliminarViewHolder>
implements View.OnClickListener{

    private List<Companiero> listaCompanieros;
    private int indice_fila;
    private View.OnClickListener listener;


    public AdaptadorCompisEliminar (List<Companiero> listaCompanieros){
        this.listaCompanieros = listaCompanieros;

    }

    public static class CompisEliminarViewHolder extends RecyclerView.ViewHolder{

        private TextView mtv_eliminar_nombre,mtv_eliminar_apellidos,mtv_eliminar_telefono;
        private LinearLayout linearLayout;

        public CompisEliminarViewHolder(@NonNull View itemView) {
            super(itemView);

            mtv_eliminar_nombre = itemView.findViewById(R.id.tv_eliminar_nombre);
            mtv_eliminar_apellidos = itemView.findViewById(R.id.tv_eliminar_apellidos);
            mtv_eliminar_telefono = itemView.findViewById(R.id.tv_eliminar_telefono);
            linearLayout = itemView.findViewById(R.id.fila_layout);

        }

        public void bindCompa(Companiero compa){

            mtv_eliminar_nombre.setText(compa.getNombre());
            mtv_eliminar_apellidos.setText(compa.getAlias());
            mtv_eliminar_telefono.setText(compa.getNumTelefono());

        }

    }
    @NonNull
    @Override
    public AdaptadorCompisEliminar.CompisEliminarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.eliminar_compis_list
                ,parent,false);

        itemView.setOnClickListener(this);

        AdaptadorCompisEliminar.CompisEliminarViewHolder compisViewHolder = new AdaptadorCompisEliminar.CompisEliminarViewHolder(itemView);
        return  compisViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CompisEliminarViewHolder holder, final int position
            , @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        Companiero companiero = listaCompanieros.get(position);
        holder.bindCompa(companiero);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                indice_fila = position;
                notifyDataSetChanged();
            }
        });
        if(indice_fila == position){

            holder.linearLayout.setBackgroundColor(Color.GRAY);

        }else{
            holder.linearLayout.setBackgroundColor(Color.WHITE);
        }
    }

    public int getIndice_fila() {
        return indice_fila;
    }

    public void setIndice_fila(int indice_fila) {
        this.indice_fila = indice_fila;
    }

    @Override
    public int getItemCount() {
        return listaCompanieros.size();
    }



    @Override
    public void onClick(View v) {
    if(listener!=null){
        listener.onClick(v);
    }
    }
}
