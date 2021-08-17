package com.ventas.ventas;


/**
  * <p>Clase utilizada para manejar a los <b>Clientes</b></p>
 */
public class Usuarios {

   

    private int id_usuario;

    public int getId_usuario() {
        return this.id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    private String usuario;

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    private String contraseña;

    public String getContraseña() {
        return this.contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }


    private int id_tipo_usuario;

    public int getId_tipo_usuario() {
        return this.id_tipo_usuario;
    }

    public void setId_tipo_usuario(int id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
    }



    private String tipo_usuario;


    public String getTipo_usuario() {
        return this.tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
    }
    
}
