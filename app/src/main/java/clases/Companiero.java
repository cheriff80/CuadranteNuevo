package clases;

import java.util.ArrayList;
import java.util.List;

public class Companiero extends Usuario {

    private List<DiaUsuario> listaDias;
    private int sumatorio_dias;

    public Companiero(String alias, String apellidos, String id, String nombre, String numTelefono) {
        super(alias, apellidos, id, nombre, numTelefono);
        super.setListaCompas(null);
        sumatorio_dias = 0;
        listaDias = new ArrayList<>();
    }

    public Companiero(){}


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

        listaDias.add(diaUsuario);
        sumatorio_dias ++;
    }

    public void devolver_dias (DiaUsuario diaUsuario){

        listaDias.remove(diaUsuario);
        sumatorio_dias --;
    }
}
