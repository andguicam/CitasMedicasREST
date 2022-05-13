package com.example.restservice;

import java.util.*;
import java.sql.*;
import java.util.Date;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class AccesoBaseDatos {

	public AccesoBaseDatos() {
		
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

	public static Usuario verificarUsuario(Connection conn, String user, String pass, String t) throws Exception{
		String query = "SELECT * FROM [dbo].[usuario] WHERE userDni = ? and pass = ? and tipo = ?;";
		PreparedStatement readStatement = conn.prepareStatement(query);
		readStatement.setString(1, user);
		readStatement.setString(2, pass);
		readStatement.setString(3, t);
		ResultSet resultSet = readStatement.executeQuery();
		if (!resultSet.next()) {
			System.out.println("No hay datos en la base de datos");
			return null;
		}
		String dni = resultSet.getString("userDni");
		String nombre = resultSet.getString("nombre");
		String apellidos = resultSet.getString("apellidos");
		String password = resultSet.getString("pass");
		Date fechaDeNacimiento = new Date(resultSet.getTimestamp("fechaNacimiento").getTime());
		String direccion = resultSet.getString("direccion");
		String tipo = resultSet.getString("tipo");
		Usuario usuario = new Usuario(nombre, apellidos, dni, password, fechaDeNacimiento, direccion, tipo); 
		//PRUEBA
		System.out.println(usuario);
		//
		
		
		return usuario;
	}

	public static Boolean setUsuario (Connection conn, String nombre, String apellidos, String dni, String password, Date fechaDeNacimiento, String direccion, String tipo) throws Exception{
		Boolean esAgregado = false; 
		PreparedStatement insertStatement = 
			conn.prepareStatement("INSERT INTO [dbo].[usuario] (userDni, pass, nombre, apellidos, fechaNacimiento, direccion, tipo) VALUES (? , ? , ? , ? , ? , ? , ?)");
		try{
		insertStatement.setString(1, dni);
		insertStatement.setString(2, password);
		insertStatement.setString(3, nombre);
		insertStatement.setString(4, apellidos);
		insertStatement.setTimestamp(5, new java.sql.Timestamp( fechaDeNacimiento.getTime()));
		insertStatement.setString(6, direccion);
		insertStatement.setString(7, tipo);
		//Prueba
		System.out.println("Query creado ");
		//
		
		insertStatement.executeUpdate();
		//Prueba
		System.out.println("Query ejecutado: " + insertStatement);
		//
		esAgregado = true; 
		}
		catch (Exception e)
		{
			System.out.println(e);
			throw new Exception("Error al introducir el usuario", e); 
		}
		return esAgregado;
	}

	public static Boolean eliminarUsuario (Connection conn, Usuario usuario) throws Exception{
		Boolean esEliminado = false; 
		try{
			String query = "DELETE FROM [dbo].[usuario] WHERE userDni = ? and tipo = ?;";
			PreparedStatement deleteStatement = conn.prepareStatement(query);
			//Prueba
			System.out.println(usuario);
			//
			
			deleteStatement.setString(1, usuario.getDni());
			deleteStatement.setString(2, usuario.getTipo_usuario());
			deleteStatement.executeUpdate();
			esEliminado = true; 
		}
		catch (Exception e){
			System.out.println(e);
			throw new Exception("Error al eliminar el usuario", e); 
		}
		return esEliminado;
	}

	public static Usuario getUsuario (Connection conn, String user, String tipoUsuario) throws Exception{
		String query = "SELECT * FROM [dbo].[usuario] WHERE userDni = ? AND tipo = ?;";
		PreparedStatement readStatement = conn.prepareStatement(query);
		readStatement.setString(1, user);
		readStatement.setString(2, tipoUsuario);
		ResultSet resultSet = null; 
		try{
			resultSet = readStatement.executeQuery();
		}
		catch (Exception e)
		{
			throw new Exception("Error al obtener el usuario", e); 
		}

		if (!resultSet.next()) {
			System.out.println("No hay datos en la base de datos!");
			return null;
		}
		String dni = resultSet.getString("userDni");
		String pass = resultSet.getString("pass");
		String nombre = resultSet.getString("nombre");
		String apellidos = resultSet.getString("apellidos");
		Date fechaDeNacimiento = new Date(resultSet.getTimestamp("fechaNacimiento").getTime());
		String direccion = resultSet.getString("direccion");
		String tipo = resultSet.getString("tipo");
		// Se podria hacer un query con el user, tipo y contrasenia, asi un user puede pertenecer a distintos roles.

		Usuario usuario = new Usuario(nombre, apellidos, dni, pass, fechaDeNacimiento, direccion, tipo); 

		return usuario; 
	}

	public static Boolean modificarUsuario (Connection conn, Usuario usuarioAntiguo, Usuario usuarioNuevo) throws Exception{
		Boolean haSidoModificado = false; 

		PreparedStatement updateStatement = 
			conn.prepareStatement("UPDATE [dbo].[usuario] SET userDni = ?, nombre = ?, pass = ?, apellidos = ?, fechaNacimiento = ?,"
			 + "direccion = ?, tipo = ? WHERE userDni = ? and tipo = ?;");

			updateStatement.setString(1, usuarioNuevo.getDni());
			updateStatement.setString(2, usuarioNuevo.getNombre());
			updateStatement.setString(3, usuarioNuevo.getPassword());	
			updateStatement.setString(4, usuarioNuevo.getApellidos());
			updateStatement.setTimestamp(5, new java.sql.Timestamp( usuarioNuevo.getFechaDeNacimiento().getTime()));
			updateStatement.setString(6, usuarioNuevo.getDireccion());
			updateStatement.setString(7, usuarioNuevo.getTipo_usuario());
			updateStatement.setString(8, usuarioAntiguo.getDni());
			updateStatement.setString(9, usuarioAntiguo.getTipo_usuario());	
			try {
				updateStatement.executeUpdate();
				haSidoModificado = true; 
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Error al intentar modificar el usuario:", e); 
			}
		return haSidoModificado; 
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
			Medico medico = new Medico(nombre, apellidos, dni, pass, fechaDeNacimiento, direccion, tipo); 

			String id = resultSet.getString("id");
			Date inicio = new Date(resultSet.getTimestamp("fechaInicio").getTime());
			Date fin = new Date(resultSet.getTimestamp("fechaFin").getTime());
			String consulta = resultSet.getString("consulta"); 
			Cita cita = new Cita(id, inicio, fin, consulta, medico);
			citas.add(cita); 
		}

		return citas; 
	}

	public static Boolean resrvaCita (Connection conn, Usuario user, Cita cita) throws Exception {
		Boolean haSidoModificado = false; 
		PreparedStatement updateStatement = conn.prepareStatement("UPDATE [dbo].[citas] SET dniPaciente = ? WHERE dniPaciente IS NULL and id = ? and fechaInicio = ? and fechaFin = ? and consulta = ? and dniMedico = ?;"); 
		updateStatement.setString(1, user.getDni());
		updateStatement.setString(2, cita.getId());
		updateStatement.setTimestamp(3, new java.sql.Timestamp(cita.getFechaInicio().getTime()));
		updateStatement.setTimestamp(4, new java.sql.Timestamp(cita.getFechaFin().getTime()));
		updateStatement.setString(5, cita.getConsulta());
		updateStatement.setString(6, cita.getMedicoResponsable().getDni());
		try {
			updateStatement.executeUpdate();
			haSidoModificado = true; 
		} catch (Exception e) {
			throw new Exception("Error al intentar modificar el el usuario", e); 
		}

		return haSidoModificado; 
	}

	public static Boolean anularCita(Connection conn, Usuario user, Cita cita) throws Exception{
		Boolean haSidoModificado = false; 
		PreparedStatement updateStatement = conn.prepareStatement("UPDATE [dbo].[citas] SET dniPaciente = NULL WHERE dniPaciente = ? and id = ? and fechaInicio = ? and fechaFin = ? and consulta = ? and dniMedico = ?;"); 
		updateStatement.setString(1, user.getDni());
		updateStatement.setString(2, cita.getId());
		updateStatement.setTimestamp(3, new java.sql.Timestamp(cita.getFechaInicio().getTime()));
		updateStatement.setTimestamp(4, new java.sql.Timestamp(cita.getFechaFin().getTime()));
		updateStatement.setString(5, cita.getConsulta());
		updateStatement.setString(6, cita.getMedicoResponsable().getDni());
		try {
			updateStatement.executeUpdate();
			haSidoModificado = true; 
		} catch (Exception e) {
			throw new Exception("Error al intentar anular la cita", e); 
		}
		return haSidoModificado; 
	}

	public static Boolean agregarCita (Connection conn, String id, Date fechaInicio, Date fechaFin, String consulta, Medico medico) throws Exception{
		Boolean esAgregado = false; 
		String query = "INSERT INTO [dbo].[citas] ( [id], [fechaInicio], [fechaFin], [dniMedico], [consulta]) VALUES ( ? , ? , ? , ? , ? )"; 

		PreparedStatement insertStatement = 
			conn.prepareStatement(query);
		

		
		try{
		insertStatement.setString(1, id);
		insertStatement.setTimestamp(2, new java.sql.Timestamp(fechaInicio.getTime()));
		insertStatement.setTimestamp(3, new java.sql.Timestamp(fechaFin.getTime()));
		insertStatement.setString(4, medico.getDni());
		insertStatement.setString(5, consulta);
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

	public static Boolean eliminarCita (Connection conn, Cita cita) throws Exception{
		Boolean esEliminado = false; 
		try {
		PreparedStatement deleteStatement = conn.prepareStatement("DELETE FROM [dbo].[citas] WHERE id = ? and fechaInicio = ? and fechaFin = ? and consulta = ? and dniMedico = ?;");
		deleteStatement.setString(1, cita.getId());
		deleteStatement.setTimestamp(2, new java.sql.Timestamp(cita.getFechaInicio().getTime()));
		deleteStatement.setTimestamp(3, new java.sql.Timestamp(cita.getFechaFin().getTime()));
		deleteStatement.setString(4, cita.getConsulta());
		deleteStatement.setString(5, cita.getMedicoResponsable().getDni());
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
			
			Medico medico = new Medico(nombre, apellidos, dni, pass, fechaDeNacimiento, direccion, tipo); 
	
			String id = resultSet.getString("id");
			Date inicio = new Date(resultSet.getTimestamp("fechaInicio").getTime());
			Date fin = new Date(resultSet.getTimestamp("fechaFin").getTime());
			String consulta = resultSet.getString("consulta"); 
			cita = new Cita(id, inicio, fin, consulta, medico);
		}
		return cita; 
	}
}