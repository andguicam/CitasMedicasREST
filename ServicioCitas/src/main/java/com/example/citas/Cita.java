package com.example.citas; 

import java.io.Serializable;
import java.util.Date;

public class Cita implements Serializable{
    private static final long serialVersionUID = 1L;
	private String id;
    private Date fechaInicio;
    private Date fechaFin;
    private String consulta;
    private Usuario medicoResponsable;
    
    public Cita(String id, Date fechaInicio, Date fechaFin, String consulta, Usuario medicoResponsable) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.consulta = consulta;
        this.medicoResponsable = medicoResponsable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public Usuario getMedicoResponsable() {
        return medicoResponsable;
    }

    public void setMedicoResponsable(Usuario medicoResponsable) {
        this.medicoResponsable = medicoResponsable;
    }
    
    public String toString () {
    	
    	return "ID: " + this.id + 
    			" | Fecha de inicio: "+ this.fechaInicio + 
    			" | Fecha de fin: "+ this.fechaFin + 
    			" | Consulta: "+ this.consulta + 
    			" | Medico Responsable: " + this.medicoResponsable.getNombre() + " " + this.medicoResponsable.getApellidos(); 
    }
}
