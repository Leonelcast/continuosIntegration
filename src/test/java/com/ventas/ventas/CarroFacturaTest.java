package com.ventas.ventas;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;






import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@AutoConfigureMockMvc
public class CarroFacturaTest extends VentasApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
    
  
    
    @Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    
	}

    @Test
	public void irAcarroTest() throws Exception {
		mockMvc.perform(post("/carro")).andExpect(status().isOk());
					

	}

    @Test
	public void irAfacturaTest() throws Exception {
		mockMvc.perform(post("/factura")).andExpect(status().isBadRequest());
					

	}


    

   

	



}
