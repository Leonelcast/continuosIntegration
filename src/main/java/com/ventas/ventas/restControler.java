package com.ventas.ventas;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

/**
 * @deprecated el controlador de @restcontroller solo devuelve json o xml 
 * <p>Clase utilizada para probar los controladores rest</p>
 */
@RestController
public class restControler {
	@Autowired(required = true)
	private Dao dao;
	
	

    private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
	public Restservice greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Restservice(counter.incrementAndGet(), String.format(template, name));
	}
	@GetMapping("/data")
    public List<Telefono> getData() {
        return dao.list();
	}

	@GetMapping(value = "/getReporte")
	public  List<EnviarCorreo>  mostrarReporte() 
	{
		 
				return dao.listaReporte(62);
	}


	
	/*
	@RequestMapping(value = "/getdispo",method = RequestMethod.GET)
	@ResponseBody
	public 	String getDispo(final Model model)
	{
		String uri = "http://localhost:5000/api/dispositivos";
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Dispositivo[]> response = restTemplate.getForEntity(uri,Dispositivo[].class);
	  	Dispositivo[] employees = response.getBody();	  
		model.addAttribute("listaFD", employees);  
		return "/";
	}*/





	@GetMapping(value = "/insert")
	private String insertUser(@RequestParam(value = "name", defaultValue = "World") String name)
	{
		String uri = "http://localhost:5000/api/users";
		RestTemplate restTemplate = new RestTemplate();

		Map<String, String> map = new HashMap<>();
		
		map.put("email", name);
		
		map.put("password", "John Doe");

		ResponseEntity<Void> response  = restTemplate.postForEntity(uri, map, Void.class);
		
		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Request Successful");
		} else {
			System.out.println("Request Failed");
		}
		return "redirect:/login";
	
	  
	  
	}




	
	
	

	

	






	


    
}