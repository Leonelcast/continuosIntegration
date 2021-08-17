package com.ventas.ventas;

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

import javax.persistence.Id;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.RedirectView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UsuarioTest extends VentasApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
    
    @Autowired(required = true)
    private Dao dao;   
    
    @BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        dao = mock(Dao.class); 
	}

   
    
    
    @Test
    public void createUsuarioTest() throws IOException
    {
        Usuarios userT = new Usuarios();
        userT.setUsuario("Usuario-Prueba"); 
        userT.setContraseña("aaa");
        userT.setId_tipo_usuario(1);        
        dao.crearU(userT); 

       verify(dao, times(1)).crearU(userT);
      
         
        
    }

    
    @Test
    public void updateUsuarioTest() throws IOException
    {
        
        Usuarios userT = new Usuarios();
        int id = 0; 
        final List<Usuarios> listaU = dao.listUsuarios("Usuario-Prueba", "aaa"); 
       for (Usuarios usuarios : listaU) {
           id += usuarios.getId_usuario(); 
       }
     
    
        userT.setId_usuario(id);
        userT.setId_tipo_usuario(2);
        userT.setUsuario("Empleado-prueba-update");
        
        dao.updateU(userT);   
        verify(dao, times(1)).updateU(userT); 

       
        
    }

    @Test
    public void deleteUsuarioTest() throws IOException
    {
        
      

        int id = 0; 
        final List<Usuarios> listaU = dao.listUsuarios("Empleado-prueba-update", "aaa"); 
       for (Usuarios usuarios : listaU) {
           id += usuarios.getId_usuario();
           
       }
  
        dao.deleteUsuario(id);
        verify(dao, times(1)).deleteUsuario(id);
   


        
         

       
        
    }

    

   

	



}
