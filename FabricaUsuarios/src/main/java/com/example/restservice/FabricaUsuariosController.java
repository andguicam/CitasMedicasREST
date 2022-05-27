package com.example.restservice;



import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class FabricaUsuariosController {
    private FabricaUsuarios fu;
    public FabricaUsuariosController(){
        try {
            this.fu = (FabricaUsuarios) new FabricaUsuariosImpl();
        } catch (RemoteException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @GetMapping("/doLogin")
    public Usuario doLogin(
            @RequestParam(value = "id") String id, @RequestParam(value="password") String pass, @RequestParam(value="tipo") String tipo) throws RemoteException {
        
        Usuario user=fu.doLogin(id, pass, tipo);
        return user;
    }
    
    @GetMapping("/agregarUsuario")
    public Boolean agregarUsuario(
            @RequestParam(value = "nombre") String nombre, @RequestParam(value = "apellidos") String apell,  @RequestParam(value = "dni") String dni,
            @RequestParam(value = "password") String pass,@RequestParam(value = "fecha") String fecha,@RequestParam(value = "direccion") String direccion, @RequestParam(value = "email") String email,
            @RequestParam(value = "tipo") String tipo) {
        
        Date fechaNac;
        try {
            fechaNac = new SimpleDateFormat("yyyy-MM-DD").parse(fecha);
            Boolean result=this.fu.agregarUsuario(nombre, apell, dni, pass, fechaNac, direccion, tipo, email);

            return result;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    
    @GetMapping("/eliminarUsuario")
    //TODO: Aqui se espera recibir un usuario, como solventamos eso? Pasamos el DNI y el tipo?
    public Boolean eliminarUsuario(
            @RequestParam(value = "dni") String dni, @RequestParam(value = "tipo") String tipo) {
        try {
            Usuario user;
            user = this.fu.getUsuario(dni, tipo);
            Boolean result=this.fu.eliminarUsuario(user);
            return result;

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
            
    }

    @GetMapping("/getUsuario")
    public Usuario getUsuario(
        @RequestParam(value = "dni") String dni, @RequestParam(value = "tipo") String tipo) {
        
        Usuario user;
        try {
            user = this.fu.getUsuario(dni, tipo);
            return user;
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @GetMapping("/modificarUsuario")
    //TODO: Aqui se espera el usuario antiguo y el nuevo. 
    //Para solventarlo propongo recibir el DNI a modificar (el antiguo) y el tipo, seguido de los nuevos parametros
    public Boolean modificarUsuario(
            @RequestParam(value = "dni_ant") String dni_ant, @RequestParam(value = "tipo_ant") String tipo_ant, @RequestParam(value = "dni_nuevo") String dni_nuevo,
            @RequestParam(value = "nombre") String nombre, @RequestParam(value = "apellidos") String apellidos,
            @RequestParam(value = "password") String password, @RequestParam(value = "fecha") String fecha,
            @RequestParam(value = "direccion") String direccion, @RequestParam(value = "tipo") String tipo,
            @RequestParam(value = "email") String email ) {

        try {
            Usuario user_ant = this.fu.getUsuario(dni_ant, tipo_ant);
            Date fechaNac = new SimpleDateFormat("yyyy-MM-DD").parse(fecha);
            Usuario user_nuevo = new Usuario(nombre, apellidos, dni_nuevo,email, password, fechaNac, direccion, tipo);

            Boolean result = this.fu.modificarUsuario(user_ant,user_nuevo);
            return result;

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/obtenerUsuarios")
    public List<Usuario> obtenerUsuarios(){
        try {
            List<Usuario> lista= this.fu.obtenerUsuarios();
            System.out.println("Consulta realizada");
            return lista;
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
 