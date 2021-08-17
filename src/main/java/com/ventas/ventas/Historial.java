package com.ventas.ventas;

import java.sql.Date;


/**
 * <p>Clase para manejar el historial de cambios</p>
 */
public class Historial {

    private String usuario;

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    } 



    private String cambio;

    public String getCambio() {
        return this.cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    private Date fecha_cambio;

    public Date getFecha_cambio() {
        return this.fecha_cambio;
    }

    public void setFecha_cambio(Date fecha_cambio) {
        this.fecha_cambio = fecha_cambio;
    }

    private String tabla;

    public String getTabla() {
        return this.tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }


    
}