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

    public List<DiaUsuario> getListaDias() {
        return listaDias;
    }

    public void setListaDias(List<DiaUsuario> listaDias) {
        this.listaDias = listaDias;
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

    /**
     * Al pedir el día incluyo otro usuario
     * @param diaUsuario
     * @param usuario
     */

    public void pedir_dias(DiaUsuario diaUsuario, Usuario usuario){

        //añado a la lista de días el día
        usuario.getListaDias().add(diaUsuario);

        /**
         * En caso de que el número de días pendientes supere diez borro el primer día solicitado
         * de la lista
         */
        if(usuario.getListaDias().size() >= 10){
            usuario.getListaDias().remove(usuario.getListaDias().size());
        }

    }
}
