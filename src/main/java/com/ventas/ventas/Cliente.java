

package com.ventas.ventas;

import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.sql.Blob;
import java.util.Base64;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.dialect.Database;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.Lob;
/**
 * <p>Clase cliente sirve para describir y guardar la información de los clientes dentro de la base de datos</p>
 */

public class Cliente {
    @Id
    private int id_cliente;

    @Column
    private int id_tipo_cliente;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String nit;

    @Column
    private String correo;
    
    @Column
    private String tipo_suscripcion;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inicio_suscripcion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vencimiento_suscripcion;

    private String tipo_cliente;

    public String getTipo_cliente() {
        return this.tipo_cliente;
    }

    public void setTipo_cliente(String tipo_cliente) {
        this.tipo_cliente = tipo_cliente;
    }

    public Cliente() {

    }




    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_tipo_cliente() {
        return id_tipo_cliente;
    }

    public void setId_tipo_cliente(int id_tipo_cliente) {
        this.id_tipo_cliente = id_tipo_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

   

    public String getTipo_suscripcion() {
        return tipo_suscripcion;
    }

    public void setTipo_suscripcion(String tipo_suscripcion) {
        this.tipo_suscripcion = tipo_suscripcion;
    }

    public Date getInicio_suscripcion() {
        return inicio_suscripcion;
    }

    public void setInicio_suscripcion(Date inicio_suscripcion) {
        this.inicio_suscripcion = inicio_suscripcion;
    }

    public Date getVencimiento_suscripcion() {
        return vencimiento_suscripcion;
    }

    public void setVencimiento_suscripcion(Date vencimiento_suscripcion) {
        this.vencimiento_suscripcion = vencimiento_suscripcion;
    }


    private String patente_comercio;

    public String getPatente_comercio() {
        return this.patente_comercio;
    }

    public void setPatente_comercio(String patente_comercio) {
        this.patente_comercio = patente_comercio;
    }

    private String _id; 

	public String get_id() {
		return this._id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}


    private String usuario;

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
