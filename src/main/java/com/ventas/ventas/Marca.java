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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.Lob;
/**
 * @deprecated las marcas las pasamos a trabajar con las fabricas 
 * <p>Clase para manejar las marcas de las terminales </p>
 */
@Entity
@Table(name = "marca_dispositivo")
public class Marca {
    @Id 
    private int id_marca;
    @Column
    private String nombre_marca;
    
    //geters y seters 
    public int getId_marca(){
        return id_marca;
    }
    public void setId_marca(int id_marca){
        this.id_marca = id_marca;
    }

    public String getNombre_marca(){
        return nombre_marca;
    }
    public void setNombre_marca(String nombre_marca){
        this.nombre_marca = nombre_marca;
    }

}