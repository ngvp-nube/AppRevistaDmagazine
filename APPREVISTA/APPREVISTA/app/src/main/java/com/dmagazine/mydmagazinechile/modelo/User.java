package com.dmagazine.mydmagazinechile.modelo;

public class User {

    private String usuario, nombre, apellido, correo, idUsuario,pass;

    public User(){}



    public User(String usuario, String nombre, String apellido, String correo, String idUsuario, String pass) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.idUsuario = idUsuario;
        this.pass = pass;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getUsuario(){
        return usuario;
    }
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido(){
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo(){
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "usuario='" + usuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

}
