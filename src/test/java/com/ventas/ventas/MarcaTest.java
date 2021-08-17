package com.ventas.ventas;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

public class MarcaTest extends VentasApplicationTests {

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
	public void testMarca() throws Exception {
		mockMvc.perform(head("/addMarca")).andExpect(status().isOk());

				

	}

	@Test
	public void testMarcaInsert() throws Exception {
		Marca ma = new Marca(); 
		ma.setNombre_marca("Nombre-marca-prueba");
		dao.saveMarca(ma);
		verify(dao, times(1)).saveMarca(ma); 
				

	}

	@Test
	public void testMarcaUpdate() throws Exception {
		Marca ma = new Marca(); 
		ma.setNombre_marca("Nombre-marca-prueba-update");
		dao.updateMarca(ma);
		verify(dao, times(1)).updateMarca(ma); 
				

	}

}