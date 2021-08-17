package com.ventas.ventas;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.RedirectView;

public class TestLogin extends VentasApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	
	
	@Test
	public void logCorrecto() throws Exception {
		mockMvc.perform(post("/log")
        .param("usuario","Empleado01")
        .param("contraseña","aaa"))
        .andExpect(redirectedUrl("/"));
					

	}

    @Test
	public void logIncorrecto_contraseña() throws Exception {
		mockMvc.perform(post("/log")
        .param("usuario","Empleado01")
        .param("contraseña","aaaa"))
        .andExpect(redirectedUrl("/login?error=true"));

				

	}

    @Test
	public void logIncorrecto_usuario() throws Exception {
		mockMvc.perform(post("/log")
        .param("usuario","Empleado01x")
        .param("contraseña","aaa"))
        .andExpect(redirectedUrl("/login?error=true"));
	

	}

    @Test
	public void logIncorrecto_usuario_contraseña() throws Exception {
		mockMvc.perform(post("/log")
        .param("usuario","Empleado01x")
        .param("contraseña","aaaa"))
        .andExpect(redirectedUrl("/login?error=true"));
	

	}


}
