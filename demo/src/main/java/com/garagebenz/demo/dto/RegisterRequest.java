package com.garagebenz.demo.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String nombre;
    private String apellido1;
    private String apellido2;
    private String usuario;
    private String contrasena;
    private String correo;
    private String id_rol;
}
