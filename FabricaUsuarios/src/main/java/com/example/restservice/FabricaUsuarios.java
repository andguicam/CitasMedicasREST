package com.example.restservice;

import java.rmi.*;
import java.util.*;

public interface FabricaUsuarios extends Remote {
    Usuario doLogin(String user, String pass, String tipo) throws RemoteException;
    Boolean agregarUsuario(String nombre, String apellidos, String dni, String password, Date fechaDeNacimiento, String direccion, String tipo) throws RemoteException;
    Boolean eliminarUsuario(Usuario usuario) throws RemoteException;
    Usuario getUsuario(String DNI, String tipo) throws RemoteException;
    Boolean modificarUsuario(Usuario user_antiguo, Usuario user_nuevo) throws RemoteException;
}
