package com.ventas.ventas;


/**
  * <p>Clase utilizada para manejar a los usuarios del sistema de ventas</p>
 */
public class User {

    private String email; 

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    
    private String password;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}