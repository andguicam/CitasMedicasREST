package com.example.restservice;

import java.rmi.RemoteException;
import java.util.Date;

public class Administrador extends Usuario {

    private FabricaUsuarios fu;
    
    Administrador (String nom, String ap, String d, String pass, Date fech, String dir, String tipo) throws RemoteException{
        super(nom, ap, d, pass, fech, dir, tipo);

    }

    // Devuelve: True si ha tenido exito, False si no
    public Boolean agregarUsuario(String nombre, String apellidos, String dni, String password, Date fechaDeNacimiento, String direccion , String tipo) throws RemoteException{
        return fu.agregarUsuario(nombre, apellidos, dni, password, fechaDeNacimiento, direccion, tipo);
    }
    // Devuelve: True si ha tenido exito, False si no
    public Boolean eliminarUsuario(Usuario usuario) throws RemoteException{
        return fu.eliminarUsuario(usuario);

    }
    // Devuelve: True si ha tenido exito, False si no
    public Boolean modificarUsuario(Usuario user_antiguo, Usuario user_nuevo) throws RemoteException{
        return fu.modificarUsuario(user_antiguo, user_nuevo);
    }
    
    public void setFabricaUsuario (FabricaUsuarios fu) {
    	this.fu = fu; 
    }
}
