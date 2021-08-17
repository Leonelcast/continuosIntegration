package com.ventas.ventas;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.RedirectView;

public class CorreoTest extends VentasApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

    @Autowired(required = true)
    @Mock
    private Correo correo ; 

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        correo = mock(Correo.class); 
	}

    @Test
    public void enviarCorreo(){

        String mensaje = "Los dispositvos comprados este mes fueron: ";
        String mensajeT = " '\n'El total de compras al credito es de: " ;  

        correo.sendMail("marcellinos777@gmail.com", "chavarria181386@unis.edu.gt" , "Detalle",mensaje + mensajeT);
        verify(correo, times(1)).sendMail("marcellinos777@gmail.com", "chavarria181386@unis.edu.gt" , "Detalle",mensaje + mensajeT); 

        

    }

    
	
	

}
