package com.ventas.ventas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class UsuarioSetterGetterTest extends VentasApplicationTests{

    public Usuarios user = new Usuarios(); 

    @Test
    public void testNombreUsuario(){
        user.setContraseña("aaa");
        user.setId_tipo_usuario(2);
        user.setId_usuario(1);
        user.setUsuario("Nombre-Usuario-Prueba");

        assertEquals("aaa", user.getContraseña());
        assertEquals(2, user.getId_tipo_usuario());
        assertEquals(1, user.getId_usuario());
        assertEquals("Nombre-Usuario-Prueba", user.getUsuario());
    }

    
}
