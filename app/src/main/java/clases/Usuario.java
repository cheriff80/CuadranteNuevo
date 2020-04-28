package clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {

    private String nombre;
    private String apellidos;
    private String password;
    private String email;
    private String numTelefono;
    private String alias;
    private String id;
    private List<Companiero> listaCompas;
    private List<DiaUsuario> listaDias;



    public Usuario( String alias, String apellidos, String id,String nombre, String numTelefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numTelefono = numTelefono;
        this.alias = alias;
        this.id = id;
        listaCompas = new ArrayList<>();
        listaDias = new ArrayList<>();

    }

    public Usuario() {
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Companiero> getListaCompas() {
        return listaCompas;
    }

    public void setListaCompas(List<Companiero> listaCompas) {
        this.listaCompas = listaCompas;
    }

    public void pedirDia(Companiero compi, DiaUsuario dia){

        //sumo el día a los días con el compi
        int sumatorioDias = compi.getSumatorio_dias();
        sumatorioDias++;
        compi.setSumatorio_dias(sumatorioDias);

        //paso a true el día solicitado
        dia.setSolicitado(true);

    }
}
