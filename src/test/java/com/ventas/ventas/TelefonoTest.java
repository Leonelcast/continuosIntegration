package com.ventas.ventas;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.RedirectView;

@AutoConfigureMockMvc
public class TelefonoTest extends VentasApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
    @Autowired(required = true)
    private Dao dao; 
    
    @Autowired(required = true)
    Dao mockdao = mock(Dao.class); 
    
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        dao = mock(Dao.class); 
	}

   


    @Test
    public void crearTelefonoTest() throws Exception
    {
                
       Telefono tel = new Telefono(); 
        tel.setId_marca(1);
        tel.setNombret("PruebaTelefno");
        tel.setCodigo_modelo("xxx");
        tel.setAlmacenamiento(80);
        tel.setColor("morado");
        tel.setExistencia(10);
        dao.save(tel);        
        verify(dao, times(1)).save(tel);
        
         
   
    }

    @Test
    public void updateTelefonoTest() throws IOException
    {
        
        Telefono tel = mockdao.get(22);        
            tel.setNombret("AAAA");        
        mockdao.updateT(tel);         
        assertSame(tel.getNombret(),"AAAA" ); 
    }
  
/*
    @Test
    public void mostrarTelefonoTest() throws IOException
    {
        List<Telefono> tel;  
        tel  = mockdao.list();
        assert((tel).size() > 1) ;
      
    }
	*/

}
