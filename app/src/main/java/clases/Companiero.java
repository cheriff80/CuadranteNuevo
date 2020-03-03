package clases;

import java.util.List;

public class Companiero extends Usuario {

    List<DiaUsuario> listaDias;

    public Companiero(String alias, String apellidos, String id, String nombre, String numTelefono) {
        super(alias, apellidos, id, nombre, numTelefono);
    }

    public Companiero(){}


    public List<DiaUsuario> getListaDias() {
        return listaDias;
    }

    public void setListaDias(List<DiaUsuario> listaDias) {
        this.listaDias = listaDias;
    }


}
