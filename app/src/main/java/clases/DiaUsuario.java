package clases;

import java.io.Serializable;

public class DiaUsuario implements Serializable {

    private String mensage;
    private String fecha;
    private String numTelefonoSolicita;
    private String numTelefonoAcepta;
    private boolean solicitado;
    private boolean devuelto;
    private boolean aceptado;


    public DiaUsuario(){
        solicitado = true;
        devuelto = false;
        aceptado = false;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
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

    public String getNumTelefonoSolicita() {
        return numTelefonoSolicita;
    }

    public void setNumTelefonoSolicita(String numTelefonoSolicita) {
        this.numTelefonoSolicita = numTelefonoSolicita;
    }

    public String getNumTelefonoAcepta() {
        return numTelefonoAcepta;
    }

    public void setNumTelefonoAcepta(String numTelefonoAcepta) {
        this.numTelefonoAcepta = numTelefonoAcepta;
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
