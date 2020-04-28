package clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Companiero extends Usuario implements Serializable {


    private int sumatorio_dias;
    private List<DiaUsuario> listaDias;

    public Companiero(String alias, String apellidos, String id, String nombre, String numTelefono) {
        super(alias, apellidos, id, nombre, numTelefono);
        super.setListaCompas(null);
        listaDias = new ArrayList<>();

        sumatorio_dias = 0;

    }

    public Companiero(){
        sumatorio_dias = 0;
        listaDias = new ArrayList<>();
    }

    public List<DiaUsuario> getListaDias() {
        return listaDias;
    }

    public void setListaDias(List<DiaUsuario> listaDias) {
        this.listaDias = listaDias;
    }

    public int getSumatorio_dias() {
        return sumatorio_dias;
    }

    public void setSumatorio_dias(int sumatorio_dias) {
        this.sumatorio_dias = sumatorio_dias;
    }

    public void pedir_dias(DiaUsuario diaUsuario){

        //añado a la lista de días el día
        listaDias.add(diaUsuario);

        /**
         * En caso de que el número de días pendientes supere diez borro el primer día solicitado
         * de la lista
         */
        if(listaDias.size() >= 10){
            listaDias.remove(listaDias.size());
        }

        sumatorio_dias ++;

    }

    public void devolver_dias (DiaUsuario diaUsuario){

        sumatorio_dias --;
    }
}
