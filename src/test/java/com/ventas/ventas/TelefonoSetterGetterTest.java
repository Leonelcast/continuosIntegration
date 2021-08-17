package com.ventas.ventas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;


public class TelefonoSetterGetterTest extends VentasApplicationTests{

    public Telefono tel = new Telefono(); 
    
    @Test
	public void Testgetysetters() throws Exception {

       
        tel.setNombret("NombrePrueba");
        tel.setAlmacenamiento(80);
        tel.setCodigo_modelo("a7");
        tel.setColor("Rojo");
        tel.setExistencia(10);
        tel.setDescripcion("Descripcion de prueba");
        tel.setNombre_marca("Marca");
        tel.setRam(8);
        tel.setPrecio_lista(85);
        tel.setProcesador("Snap-prueba");
        tel.setId_dispositivo(1);
        tel.setNumero_serie("55AA");
        tel.setNumero_cores(8);        
   

       


        assertSame(       "NombrePrueba"     ,tel.getNombret());
        assertSame(   80   ,tel.getAlmacenamiento());
        assertSame(    "a7"  ,tel.getCodigo_modelo());
        assertSame(  "Rojo"    ,tel.getColor());
        assertSame(  10    ,tel.getExistencia());
        assertSame(  "Descripcion de prueba"    ,tel.getDescripcion());
        assertSame(  "Marca"    ,tel.getNombre_marca());
        assertSame(  8    ,tel.getRam());
        assertEquals(   85.0   ,tel.getPrecio_lista());
        assertSame(    "Snap-prueba"  ,tel.getProcesador());
        assertSame(   1 ,tel.getId_dispositivo());
        assertSame(   "55AA"     ,tel.getNumero_serie());
        assertSame(   8   ,tel.getNumero_cores());
     
        
        


    
        
        
		
					

	}

}