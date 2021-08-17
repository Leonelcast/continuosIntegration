package com.ventas.ventas;
/**
 * <p>Clase para modelar la información de las fabricas que traemos en rest</p>
 */
public class Fabrica {

    private int id_fabrica;

    public int getId_fabrica() {
        return this.id_fabrica;
    }

    public void setId_fabrica(int id_fabrica) {
        this.id_fabrica = id_fabrica;
    }

    private String ip;

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    private String puerto;

    public String getPuerto() {
        return this.puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    private String nombref;

    public String getNombref() {
        return this.nombref;
    }

    public void setNombref(String nombref) {
        this.nombref = nombref;
    } 


    private int id_marca;

    public int getId_marca() {
        return this.id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    
}
