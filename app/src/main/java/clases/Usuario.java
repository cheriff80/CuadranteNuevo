package clases;

public class Usuario {

    private String nombre;
    private String password;
    private String email;
    private String numTelefono;
    private String alias;
    private int id;


    public Usuario() {
    }

    public Usuario(String nombre, String password, String email, String numTelefono, String alias, int id) {
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.numTelefono = numTelefono;
        this.alias = alias;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
