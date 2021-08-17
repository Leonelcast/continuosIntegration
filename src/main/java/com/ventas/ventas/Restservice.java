package com.ventas.ventas;


/**
 * @deprecated el controlador de @restcontroller solo devuelve json o xml 
 * <p>Clase utilizada para probar los controladores rest</p>
 */
public class Restservice{

	private final long id;
	private final String content;

	public Restservice(long id, String content) {
		this.id = id;
		this.content = content;
		
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}