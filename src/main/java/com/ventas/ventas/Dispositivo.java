package com.ventas.ventas;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Clase dispositiva, utilizada para modelar terminales </p>
 */
public class Dispositivo {

    private String _id;

    public String get_id() {
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
    
    private String marca;

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    } 


    private int memoria;

    public int getMemoria() {
        return this.memoria;
    }

    public void setMemoria(int memoria) {
        this.memoria = memoria;
    }


    private String modelo;

    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    } 

    private String numeroSerie;

    public String getNumeroSerie() {
        return this.numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    } 

    private String procesador;

    public String getProcesador() {
        return this.procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    } 

    private String resolucion;

    public String getResolucion() {
        return this.resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    private String tipo;

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    private String precio;

    public String getPrecio() {
        return this.precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


    private String __v;

    public String get__v() {
        return this.__v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    private List<Dispositivo> dispo;
    
    public Dispositivo(){
       dispo = new ArrayList<>();

    }

    private int origen;

	public int getOrigen() {
		return this.origen;
	}

	public void setOrigen(int origen) {
		this.origen = origen;
	}
 

    private int id_estados; 


	public int getId_estados() {
		return this.id_estados;
	}

	public void setId_estados(int id_estados) {
		this.id_estados = id_estados;
	}

    private String estadof; 

	public String getEstadof() {
		return this.estadof;
	}

	public void setEstadof(String estadof) {
		this.estadof = estadof;
	}


    private int id_fabrica; 

	public int getId_fabrica() {
		return this.id_fabrica;
	}

	public void setId_fabrica(int id_fabrica) {
		this.id_fabrica = id_fabrica;
	}


    private String nombref; 

	public String getNombref() {
		return this.nombref;
	}

	public void setNombref(String nombref) {
		this.nombref = nombref;
	}

  


    
}