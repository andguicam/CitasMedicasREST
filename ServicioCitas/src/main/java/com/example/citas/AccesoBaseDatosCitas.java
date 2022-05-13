package com.example.citas; 

import java.util.*;
import java.sql.*;
import java.util.Date;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class AccesoBaseDatosCitas {

	public AccesoBaseDatosCitas() {
		
	}

	public static Connection getConexion () throws Exception {
		
        // crear datasource.
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("admin_sw_citas@swsqlserver");
        ds.setPassword("PacientesyMedicos.");
        ds.setServerName("swsqlserver.database.windows.net");
        ds.setPortNumber(1433);
        ds.setDatabaseName("CITAS_MEDICAS_SW");
        ds.setTrustServerCertificate(false);
        ds.setEncrypt(true);
        ds.setLoginTimeout(30);
        //ds.setSSLProtocol("TLS");
        
		Connection conn = null; 
		try {
		//conn = DriverManager.getConnection(url);
		conn = ds.getConnection();
		}
		catch (Exception e) {
			System.out.println(e);
			throw new Exception("Error al conectar", e);
			// primer error, faltaba el driver en Eclipse
		}
		return conn;
	}

	///////////////// Citas //////////////////

	public static List<Cita> obtenerCitas (Connection conn) throws Exception{
		List <Cita> citas = new LinkedList<Cita>(); 
		PreparedStatement query = conn.prepareStatement("SELECT * from [dbo].[usuario], [dbo].[citas] where userDni = dniMedico and tipo = 'medico';"); 
		ResultSet resultSet = null; 
		try {
			resultSet = query.executeQuery();
		} catch (Exception e) {
			throw new Exception("Error a la hora de obtener todas las citas"); 
		}
		while (resultSet.next()){
			String dni = resultSet.getString("userDni");
			String pass = resultSet.getString("pass");
			String nombre = resultSet.getString("nombre");
			String apellidos = resultSet.getString("apellidos");
			Date fechaDeNacimiento = new Date(resultSet.getTimestamp("fechaNacimiento").getTime());
			String direccion = resultSet.getString("direccion");
			String tipo = resultSet.getString("tipo");
			Usuario medico = new Usuario(nombre, apellidos, dni, pass, fechaDeNacimiento, direccion, tipo); 

			String id = resultSet.getString("id");
			Date inicio = new Date(resultSet.getTimestamp("fechaInicio").getTime());
			Date fin = new Date(resultSet.getTimestamp("fechaFin").getTime());
			String consulta = resultSet.getString("consulta"); 
			Cita cita = new Cita(id, inicio, fin, consulta, medico);
			citas.add(cita); 
		}

		return citas; 
	}

	public static Boolean resrvaCita (Connection conn, String dniPaciente, String idCita) throws Exception {
		Boolean haSidoModificado = false; 

		PreparedStatement updateStatement = conn.prepareStatement("UPDATE [dbo].[citas] SET dniPaciente = ? "+
		"where dniPaciente IS NULL and id = ? and dniMedico <> ? and ? in (SELECT userDni from "+
		"[dbo].[usuario] where tipo = 'paciente');"); 
		
		updateStatement.setString(1, dniPaciente);
		updateStatement.setString(2, idCita);
		updateStatement.setString(3, dniPaciente);
		updateStatement.setString(4, dniPaciente);
		try {
			updateStatement.executeUpdate();
			haSidoModificado = true; 
		} catch (Exception e) {
			throw new Exception("Error al intentar modificar el el usuario", e); 
		}

		return haSidoModificado; 
	}

	public static Boolean anularCita(Connection conn, String dniPaciente, String idCita) throws Exception{
		Boolean haSidoModificado = false; 
		PreparedStatement updateStatement = conn.prepareStatement("UPDATE [dbo].[citas] SET dniPaciente = NULL "+
		"where dniPaciente = ? and id = ? and dniMedico <> ? and ? in (SELECT userDni from "+
		"[dbo].[usuario] where tipo = 'paciente');"); 
		updateStatement.setString(1, dniPaciente);
		updateStatement.setString(2, idCita);
		updateStatement.setString(3, dniPaciente);
		updateStatement.setString(4, dniPaciente);
		try {
			updateStatement.executeUpdate();
			haSidoModificado = true; 
		} catch (Exception e) {
			throw new Exception("Error al intentar anular la cita", e); 
		}
		return haSidoModificado; 
	}

	public static Boolean agregarCita (Connection conn, Date fechaInicio, Date fechaFin, String consulta, String dniMedico) throws Exception{
		Boolean esAgregado = false; 
		String query = "INSERT INTO [dbo].[citas] ( [fechaInicio], [fechaFin], [dniMedico], [consulta]) VALUES ( ? , ? , ? , ? )"; 

		PreparedStatement insertStatement = 
			conn.prepareStatement(query);
		

		
		try{
		insertStatement.setTimestamp(1, new java.sql.Timestamp(fechaInicio.getTime()));
		insertStatement.setTimestamp(2, new java.sql.Timestamp(fechaFin.getTime()));
		insertStatement.setString(3, dniMedico);
		insertStatement.setString(4, consulta);
		insertStatement.executeUpdate();
		esAgregado = true; 
		}
		catch (Exception e)
		{
			System.out.println(e);
			throw new Exception("Error al introducir la cita", e); 
		}
		return esAgregado;
	}

	public static Boolean eliminarCita (Connection conn, String idCita) throws Exception{
		Boolean esEliminado = false; 
		try {
		PreparedStatement deleteStatement = conn.prepareStatement("DELETE FROM [dbo].[citas] WHERE id = ?");
		deleteStatement.setString(1, idCita);
		deleteStatement.executeUpdate(); 
		esEliminado = true; 
		}
		catch (Exception e)
		{
			throw new Exception("Error al intentar leminar el usuario", e); 
		}
		return esEliminado;
	}

	public static Cita obtenerCita (Connection conn, String cita_id) throws Exception{
		Cita cita = null; 
		PreparedStatement query = conn.prepareStatement( "SELECT * from [dbo].[usuario], [dbo].[citas] where userDni = dniMedico and tipo = 'medico' and id = ?;"); 
		query.setString(1, cita_id);
		ResultSet resultSet = null; 
		try {
			resultSet = query.executeQuery();
		} catch (Exception e) {
			throw new Exception("Error a la hora de intentar obtener la cita"); 
		}
		

		if (resultSet.next()) {
			String dni = resultSet.getString("userDni");
			String pass = resultSet.getString("pass");
			String nombre = resultSet.getString("nombre");
			String apellidos = resultSet.getString("apellidos");
			Date fechaDeNacimiento = new Date(resultSet.getTimestamp("fechaNacimiento").getTime());
			String direccion = resultSet.getString("direccion");
			String tipo = resultSet.getString("tipo");
			
			Usuario medico = new Usuario(nombre, apellidos, dni, pass, fechaDeNacimiento, direccion, tipo); 
	
			String id = resultSet.getString("id");
			Date inicio = new Date(resultSet.getTimestamp("fechaInicio").getTime());
			Date fin = new Date(resultSet.getTimestamp("fechaFin").getTime());
			String consulta = resultSet.getString("consulta"); 
			cita = new Cita(id, inicio, fin, consulta, medico);
		}
		return cita; 
	}
}