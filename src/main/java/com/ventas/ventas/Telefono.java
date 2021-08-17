package com.ventas.ventas;

import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.sql.Blob;
import java.util.Base64;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.Lob;


/**
  * <p>Clase utilizada para manejar las terminales, modelar las terminales</p>
 */
@Entity
public class Telefono {

    private int id_dispositivo;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id_bodega;

    private int id_marca;

    private String buscar;

    public String getBuscar() {
        return this.buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public int getId_marca() {
        return this.id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public int getId_bodega() {
        return this.id_bodega;
    }

    public void setId_bodega(int id_bodega) {
        this.id_bodega = id_bodega;
    }

    private String foto1;

	public String getFoto1() {
		return this.foto1;
	}

	public void setFoto1(String foto1) {
		this.foto1 = foto1;
	}


    private String foto2;

    public String getFoto2() {
        return this.foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    private String foto3;

    public String getFoto3() {
        return this.foto3;
    }

    public void setFoto3(String foto3) {
        this.foto3 = foto3;
    }

   




    @Column
    private String numero_serie;

    @Column
    private String nombre_marca;

    @Column
    private String nombret;

    @Column
    private String descripcion;

    @Column
    private int existencia;

    public int getExistencia() {
        return this.existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    @Column
    private float precio_lista;

    public float getPrecio_lista() {
        return this.precio_lista;
    }

    public void setPrecio_lista(float precio_lista) {
        this.precio_lista = precio_lista;
    }

    @Column
    private String codigo_modelo;

    public String getCodigo_modelo() {
        return this.codigo_modelo;
    }

    public void setCodigo_modelo(String codigo_modelo) {
        this.codigo_modelo = codigo_modelo;
    }

    @Column
    private int ram;

    public int getRam() {
        return this.ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    @Column
    private int almacenamiento;

    public int getAlmacenamiento() {
        return this.almacenamiento;
    }

    public void setAlmacenamiento(int almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    @Column
    private String procesador;

    public String getProcesador() {
        return this.procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    @Column
    private int numero_cores;

    public int getNumero_cores() {
        return this.numero_cores;
    }

    public void setNumero_cores(int numero_cores) {
        this.numero_cores = numero_cores;
    }

    @Column
    private String color;

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // Constructor
    public  Telefono() {
       
         

    }
    // Seters y geters

    public int getId_dispositivo() {
        return id_dispositivo;
    }

    public void setId_dispositivo(int id_dispositivo) {
        this.id_dispositivo = id_dispositivo;
    }

  

    public String getNumero_serie() {
        return numero_serie;
    }

    public void setNumero_serie(String numero_serie) {
        this.numero_serie = numero_serie;
    }

    public String getNombre_marca() {
        return nombre_marca;
    }

    public void setNombre_marca(String nombre_marca) {
        this.nombre_marca = nombre_marca;
    }

    public String getNombret() {
        return nombret;
    }

    public void setNombret(String nombret) {
        this.nombret = nombret;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

       //Rest 

       private String _id;

       public String get_id() {
           return this._id;
       }

       public void set_id(String _id) {
           this._id = _id;
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

       private String modelo;

       public String getModelo() {
           return this.modelo;
       }

       public void setModelo(String modelo) {
           this.modelo = modelo;
       }

       private String marca;

       public String getMarca() {
           return this.marca;
       }

       public void setMarca(String marca) {
           this.marca = marca;
       }

       public float precio;

       public float getPrecio() {
           return this.precio;
       }

       public void setPrecio(float precio) {
           this.precio = precio;
       }

       private int cantidad; 

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

    private String estado;

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
//Para los numeros de serie
    private String num_series;

    public String getNum_series() {
        return this.num_series;
    }

    public void setNum_series(String num_series) {
        this.num_series = num_series;
    }

  

    private int id_num; 

	public int getId_num() {
		return this.id_num;
	}

	public void setId_num(int id_num) {
		this.id_num = id_num;
	}

    private String estadov;

    public String getEstadov() {
        return this.estadov;
    }

    public void setEstadov(String estadov) {
        this.estadov = estadov;
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

    private String nombre; 

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


    
    
    



    





    






}