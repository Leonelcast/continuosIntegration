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
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties.Identityprovider.Verification;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.RedirectView;


@AutoConfigureMockMvc
public class ClienteTest extends VentasApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

    @Autowired(required = true)
    private Dao dao; 

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        dao = mock(Dao.class);  
      
	}
   

    public Cliente cli = new Cliente(); 
    public int id = 0; 

    @Test
    public void crearCliente() throws Exception
    {
                
      
       cli.setNombre("Prueba-nombre");
       cli.setApellido("Prueba-apellido");
       cli.setCorreo("Prueba-correo");
       cli.setId_tipo_cliente(2);
       cli.setPatente_comercio("https://es.logodownload.org/wp-content/uploads/2018/12/tigo-logo-11.png");
       dao.saveCliente(cli); 
        id += cli.getId_cliente(); 

       assertSame(cli.getNombre(),"Prueba-nombre" ); 

       assertSame(cli.getApellido(), "Prueba-apellido");
       assertSame(cli.getCorreo(),"Prueba-correo");
       assertSame(cli.getId_tipo_cliente(),2);
       assertSame(cli.getPatente_comercio(),"https://es.logodownload.org/wp-content/uploads/2018/12/tigo-logo-11.png");
       
 
        verify(dao, times(1)).saveCliente(cli);
         
   
    }

   

   


}
