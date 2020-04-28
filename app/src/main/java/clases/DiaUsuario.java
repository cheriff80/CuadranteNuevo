package clases;

import java.io.Serializable;

public class DiaUsuario implements Serializable {

    private String mensage;
    private String fecha;
    private boolean solicitado;
    private boolean devuelto;


    public DiaUsuario(){
        solicitado = true;
        devuelto = false;
    }

    public String getMensage() {
        return mensage;
    }

    public void setMensage(String mensage) {
        this.mensage = mensage;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }



    public boolean isSolicitado() {
        return solicitado;
    }

    public void setSolicitado(boolean solicitado) {
        this.solicitado = solicitado;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }
}
