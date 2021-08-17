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



@Entity
public class Numserie {

@Id
 private int id_num;

	public int getId_num() {
		return this.id_num;
	}

	public void setId_num(int id_num) {
		this.id_num = id_num;
	}



    private int num_serie;

    public int getNum_serie() {
        return this.num_serie;
    }

    public void setNum_serie(int num_serie) {
        this.num_serie = num_serie;
    }

    private int estadov;

    public int getEstadov() {
        return this.estadov;
    }

    public void setEstadov(int estadov) {
        this.estadov = estadov;
    }

    private int dueño;

    public int getDueñO() {
        return this.dueño;
    }

    public void setDueñO(int dueño) {
        this.dueño = dueño;
    }

    private int modelo;

    public int getModelo() {
        return this.modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    private int id_bodega;

    public int getId_bodega() {
        return this.id_bodega;
    }

    public void setId_bodega(int id_bodega) {
        this.id_bodega = id_bodega;
    }

 

 

}