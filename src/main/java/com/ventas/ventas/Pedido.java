package com.ventas.ventas;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <p>Clase para modelar los pedidos</p>
 */
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id_pedido;


    public int getId_pedido() {
        return this.id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    private int id_cliente;

    public int getId_cliente() {
        return this.id_cliente;
        
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    private int id_dispositivo;

    public int getId_dispositivo() {
        return this.id_dispositivo;
    }

    public void setId_dispositivo(int id_dispositivo) {
        this.id_dispositivo = id_dispositivo;
    }

    private int cantidadp;

    public int getCantidadp() {
        return this.cantidadp;
    }

    private int id_bodega;

    public int getId_bodega() {
        return this.id_bodega;
    }

    public void setId_bodega(int id_bodega) {
        this.id_bodega = id_bodega;
    }

    public void setCantidadp(int cantidadp) {
        this.cantidadp = cantidadp;
    }

    private String nombre;

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    

    private String apellido;

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    private int nit;

    public int getNit() {
        return this.nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    private float precio_lista;

    public float getPrecio_lista() {
        return this.precio_lista;
    }

    public void setPrecio_lista(float precio_lista) {
        this.precio_lista = precio_lista;
    }

    private float precio_total;

    public float getPrecio_total() {
        return this.precio_total;
    }

    public void setPrecio_total(float precio_total) {
        this.precio_total = precio_total;
    } 


    private Date vencimiento_suscripcion;

    public Date getVencimiento_suscripcion() {
        return this.vencimiento_suscripcion;
    }

    public void setVencimiento_suscripcion(Date vencimiento_suscripcion) {
        this.vencimiento_suscripcion = vencimiento_suscripcion;
    }

    private String nombret;

    public String getNombret() {
        return this.nombret;
    }

    public void setNombret(String nombret) {
        this.nombret = nombret;
    }

    public int id_tipo_cliente;

    public int getId_tipo_cliente() {
        return this.id_tipo_cliente;
    }

    public void setId_tipo_cliente(int id_tipo_cliente) {
        this.id_tipo_cliente = id_tipo_cliente;
    }

    private float totales;

    public float getTotales() {
        return this.totales;
    }

    public void setTotales(float totales) {
        this.totales = totales;
    }

    private int cantidadp_a;

    public int getCantidadp_a() {
        return this.cantidadp_a;
    }

    public void setCantidadp_a(int cantidadp_a) {
        this.cantidadp_a = cantidadp_a;
    }

    private int credito;

	public int getCredito() {
		return this.credito;
	}

	public void setCredito(int credito) {
		this.credito = credito;
    }
    
    private String correo;

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
    }
    
    private int cantidad;

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    private String nombre_marca;

    public String getNombre_marca() {
        return this.nombre_marca;
    }

    public void setNombre_marca(String nombre_marca) {
        this.nombre_marca = nombre_marca;
    }

    private float total_c;

    public float getTotal_c() {
        return this.total_c;
    }

    public void setTotal_c(float total_c) {
        this.total_c = total_c;
    }

    private String nombref; 

	public String getNombref() {
		return this.nombref;
	}

	public void setNombref(String nombref) {
		this.nombref = nombref;
	}




 
    
    


    








    
    
}