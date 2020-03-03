package clases;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nombre;
    private String apellidos;
    private String password;
    private String email;
    private String numTelefono;
    private String alias;
    private String id;
    private List<Companiero> listaCompis;


    public Usuario( String alias, String apellidos, String id,String nombre, String numTelefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numTelefono = numTelefono;
        this.alias = alias;
        this.id = id;
        this.listaCompis = new ArrayList<>();
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

    public List<Companiero> getListaCompis() {
        return listaCompis;
    }

    public void setListaCompis(List<Companiero> listaCompis) {
        this.listaCompis = listaCompis;
    }
}
