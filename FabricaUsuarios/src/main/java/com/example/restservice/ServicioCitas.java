package com.example.restservice;

import java.rmi.*;
import java.util.*;


public interface ServicioCitas extends Remote {
    List<Cita> obtenerListaCitas() throws RemoteException;
    Boolean reservarCita(Usuario user, Cita cita) throws RemoteException;
    Boolean anularCita(Usuario user, Cita cita) throws RemoteException;
    Boolean agregarCita(String id, Date fechaInicio, Date fechaFin, String consulta, Medico medicoResponsable) throws RemoteException;
    Boolean eliminarCita(Cita cita) throws RemoteException;
    Cita obtenerCita(String id_cita) throws RemoteException;
}
