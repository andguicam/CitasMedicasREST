package com.example.restservice;
import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
	private String nombre;
    private String apellidos;
    private String dni;
    private String password;
    private Date fechaDeNacimiento;
    private String direccion;
    private String tipo_usuario;

    public Usuario(String nom, String ap, String d, String p, Date fech, String dir,String tipo) {
        nombre = nom;
        apellidos = ap;
        dni = d;
        password = p; 
        fechaDeNacimiento = fech;
        direccion = dir;
        tipo_usuario=tipo;
    }
    
    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDni() {
        return dni;
    }
    public String getPassword(){
        return password; 
    }
    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }
    
    public String getTipo_usuario() {
        return tipo_usuario;
    }
    //Setters

    public void setNombre(String nombre) {
        this.nombre=nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos=apellidos;
    }
    
    public void setDni(String dni) {
        this.dni=dni;
    }

    public void setPassword(String password){
        this.password = password; 
    }

    public void setFechaDeNacimiento(Date fecha) {
       this.fechaDeNacimiento=fecha;
    }

    public void setDireccion(String direccion) {
        this.direccion=direccion;
    }
    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
    public String toString() {
		return "Nombre: "+ this.nombre + 
			 "\nApellidos: " + this.apellidos + 
			 "\nDNI: " +this.dni +
			 "\nFecha de nacimiento: "+ this.fechaDeNacimiento + 
			 "\nDirección: "+ this.direccion+
			 "\nTipo de usuario: " + this.tipo_usuario;
    	
    }
}