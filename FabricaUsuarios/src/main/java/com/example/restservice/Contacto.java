// package com.example.restservice;

// public class Contacto {
//     private final long id;
//     private final String apellido;
//     public Contacto(int id, String apellido) {
//         this.id=id;
//         this.apellido=apellido;
//         //consulta y guardar resultado como String?
//     }
    
//     public String getUser() {
//         return content;
//     }
// }
package com.example.restservice;

public class Contacto {

    private String id;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;

    public Contacto(String id, String nombre, String apellidos, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return String.format(
                "Contacto[id=%d, Nombre='%s', Apellidos='%s', Email=%s, Telefono=%s]",
                id, nombre, apellidos, email, telefono);
    }

    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}