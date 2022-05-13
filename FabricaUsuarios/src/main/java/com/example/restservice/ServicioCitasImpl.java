package com.example.restservice;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ServicioCitasImpl extends UnicastRemoteObject implements ServicioCitas {
    List<Cita> l;
    Connection conn; 

    public ServicioCitasImpl() throws RemoteException, SQLException {
        try {
            conn = AccesoBaseDatos.getConexion();
        } catch (Exception e) {
            System.out.println(e);
        } 
    }

    @Override
    public List<Cita> obtenerListaCitas() throws RemoteException {
        List<Cita> citas = null; 
        try {
            citas =  AccesoBaseDatos.obtenerCitas(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return citas; 
    }

    @Override
    public Boolean reservarCita(Usuario user, Cita cita) throws RemoteException {
        Boolean haSidoModificado = false; 
        try {
            AccesoBaseDatos.resrvaCita(conn, user, cita);
        } catch (Exception e) {
            System.out.println(e);
        } 
        return haSidoModificado;
    }

    @Override
    public Boolean anularCita(Usuario user, Cita cita) throws RemoteException {
        Boolean haSidoModificado = false; 
        try {
            AccesoBaseDatos.anularCita(conn, user, cita);
        } catch (Exception e) {
            System.out.println(e);
        } 
        return haSidoModificado;
    }

    @Override
    public Boolean agregarCita(String id, Date fechaInicio, Date fechaFin, String consulta, Medico medicoResponsable) throws RemoteException {
        Boolean esAgregado = false; 
        try {
            esAgregado = AccesoBaseDatos.agregarCita(conn, id, fechaInicio, fechaFin, consulta, medicoResponsable);
        } catch (Exception e) {
            System.out.println(e);
        } 
        return esAgregado;
    }

    @Override
    public Boolean eliminarCita(Cita cita) throws RemoteException {
        Boolean esEliminado = false; 
        try {
            esEliminado = AccesoBaseDatos.eliminarCita(conn, cita);
        } catch (Exception e) {
            System.out.println(e);
        } 
        return esEliminado;
    }

    @Override
    public Cita obtenerCita(String id_cita) throws RemoteException {
        Cita cita = null; 
        try {
            cita =  AccesoBaseDatos.obtenerCita(conn, id_cita);
        } catch (Exception e) {
            System.out.println(e);
        }
        return cita; 
    }

    
}
