package com.example.citas;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//El controller sirve para gestionar las peticiones REST a recursos 

@RestController
public class CitasController {
	
	Connection conn = null; 
	
	public CitasController () {
		try {
			conn = AccesoBaseDatosCitas.getConexion(); 
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	

	@GetMapping("/obtenerListaCitas")
    public List<Cita> obtenerListaCitas() {
        List<Cita> citas = null; 
        try {
            citas =  AccesoBaseDatosCitas.obtenerCitas(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return citas; 
    }

    @GetMapping("/obtenerListaCitasMedico")
    
    public List<Cita> obtenerListaCitasMedico(@RequestParam (value = "dniMedico") String dniMedico) {
        List<Cita> citas = null;
        try {
            citas = AccesoBaseDatosCitas.citasMedico(conn,dniMedico);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return citas;
    }
    

    @GetMapping("/citasReservadas")
    public List<Cita> citasReservadas(
    @RequestParam( value = "dniPaciente") String dniPaciente
    ) {
        List<Cita> citas = null; 
        try {
            citas =  AccesoBaseDatosCitas.citasReservadas(conn, dniPaciente);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return citas; 
    }

    @GetMapping("/citasDisponibles")
    public List<Cita> citasDisponibles(
        @RequestParam( value = "dniPaciente") String dniPaciente
    ) {
        List<Cita> citas = null; 
        try {
            citas =  AccesoBaseDatosCitas.citasDisponibles(conn, dniPaciente);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return citas; 
    }

	@GetMapping("/reservarCita")
    public Boolean reservarCita(
		@RequestParam (value = "dniPaciente") String dniPaciente, 
		@RequestParam (value = "idCita") String idCita) {
        Boolean haSidoModificado = false; 
        try {
            haSidoModificado = AccesoBaseDatosCitas.reservaCita(conn, dniPaciente, idCita);
        } catch (Exception e) {
            System.out.println(e);
        }

        return haSidoModificado;
    }

	@GetMapping("/anularCita")
    public Boolean anularCita(
		@RequestParam (value = "dniPaciente") String dniPaciente, 
		@RequestParam (value = "idCita") String idCita
	) {
        Boolean haSidoModificado = false; 
        try {
            haSidoModificado = AccesoBaseDatosCitas.anularCita(conn, dniPaciente, idCita);
        } catch (Exception e) {
            System.out.println(e);
        } 
        return haSidoModificado;
    }

	@GetMapping("/agregarCita")
    public Boolean agregarCita( 
		@RequestParam(value = "fechaInicio") String fechaInicio, 
		@RequestParam(value = "fechaFin")String fechaFin, 
		@RequestParam(value = "consulta") String consulta, 
		@RequestParam(value = "dniMedico") String dniMedico) {
        Boolean esAgregado = false; 

		Date inicio = stringtoDate(fechaInicio); 
		Date fin = stringtoDate(fechaFin);

        try {
            esAgregado = AccesoBaseDatosCitas.agregarCita(conn, inicio, fin, consulta, dniMedico);
        } catch (Exception e) {
            System.out.println(e);
        } 
        return esAgregado;
    }
	

	@GetMapping("/eliminarCita")
    public Boolean eliminarCita(
        @RequestParam(value = "idCita") String idCita) {
        Boolean esEliminado = false; 
        try {
            esEliminado = AccesoBaseDatosCitas.eliminarCita(conn, idCita);
        } catch (Exception e) {
            System.out.println(e);
        } 
        return esEliminado;
    }

	@GetMapping("/obtenerCita")
    public Cita obtenerCita(
		@RequestParam(value = "idCita") String id_cita) {
        Cita cita = null; 
        try {
            cita =  AccesoBaseDatosCitas.obtenerCita(conn, id_cita);
        } catch (Exception e) {
            System.out.println(e);
        }
        return cita; 
    }

	private Date stringtoDate (String fecha){
		Date date = null; 
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(fecha);
		} catch (ParseException e) {
			System.out.println(e);
			e.printStackTrace();
		}  
		return date; 
	}
}
