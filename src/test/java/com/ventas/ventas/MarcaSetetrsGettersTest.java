package com.ventas.ventas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;


public class MarcaSetetrsGettersTest extends VentasApplicationTests{

    public Marca marca = new Marca(); 
    
    @Test
	public void Testnombre_marca() throws Exception {
        
            marca.setNombre_marca("NombrePrueba");
            assertEquals("NombrePrueba", marca.getNombre_marca());
            assertNotNull(marca.getNombre_marca());
		
					

	}

    @Test
	public void Testid_marca () throws Exception {

        marca.setId_marca(1);
        assertEquals(1, marca.getId_marca());
        assertNotNull(marca.getId_marca());
	

	}
}
