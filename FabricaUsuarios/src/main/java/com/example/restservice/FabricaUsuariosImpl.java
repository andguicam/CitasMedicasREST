package com.example.restservice;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class FabricaUsuariosImpl extends UnicastRemoteObject implements FabricaUsuarios{
    // Variable donde se guarda la conexion que se establece con la base de datos
    private Connection conn;

     FabricaUsuariosImpl() throws RemoteException, SQLException {
        // Se establece una conexion cuando se inicializa la clase
        try{
            conn = AccesoBaseDatos.getConexion(); 
        }
        catch (Exception e){
            System.out.println(e); 
        }
    }

    @Override
    public Usuario doLogin(String user, String password, String t) throws RemoteException {

        Usuario usuario = null; 
        Usuario rol = null; 
        try{
            usuario = AccesoBaseDatos.verificarUsuario(conn, user, password, t);
        }
        catch (Exception e){
            System.out.println(e);
            //cerrar conexion?
        }

        if (usuario != null){

            String tipo = usuario.getTipo_usuario().toLowerCase(); 
            String nom = usuario.getNombre(); 
            String ap = usuario.getApellidos(); 
            String d = usuario.getDni(); 
            String pass = usuario.getPassword(); 
            Date fech = usuario.getFechaDeNacimiento(); 
            String dir = usuario.getDireccion(); 
        
            if ("paciente".equals(tipo)) {
                rol = new Paciente(nom, ap, d, pass, fech, dir, tipo);
            }
            else if ("medico".equals(tipo)) {
                rol = new Medico(nom, ap, d, pass, fech, dir, tipo);
            } 
            else if("administrador".equals(tipo)){
                rol = new Administrador(nom, ap, d, pass, fech, dir, tipo);
            }
            
        }
        return rol; 
    }

    @Override
    public Boolean agregarUsuario(String nombre, String apellidos, String dni, String password, Date fechaDeNacimiento, String direccion, String tipo) throws RemoteException {
        Boolean esAgregado = false;
        try {
            esAgregado = AccesoBaseDatos.setUsuario(conn, nombre, apellidos, dni, password, fechaDeNacimiento, direccion, tipo );
        } catch (Exception e) {
            System.out.println(e); 
        }
        return esAgregado; 

    }

    @Override
    public Boolean eliminarUsuario(Usuario usuario) throws RemoteException {


        Boolean esEliminado = false; 
        try {
            esEliminado = AccesoBaseDatos.eliminarUsuario(conn, usuario);
        } catch (Exception e) {

            System.out.println(e); 
        }

        return esEliminado;
    }

    @Override
    public Usuario getUsuario(String dni, String tipo) throws RemoteException {

        Usuario user = null;
        try{
            user = AccesoBaseDatos.getUsuario(conn, dni, tipo); 
        }
         catch (Exception e) {
            System.out.println(e); 
        }
        return user;
    }

    @Override
    public Boolean modificarUsuario(Usuario user_antiguo, Usuario user_nuevo) throws RemoteException {
        Boolean haSidoModificado = false; 
        try {
            haSidoModificado = AccesoBaseDatos.modificarUsuario(conn, user_antiguo, user_nuevo);
        } catch (Exception e) {
            System.out.println(e); 
        }
        return haSidoModificado;
    }

}
