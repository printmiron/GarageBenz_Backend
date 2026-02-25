package com.garagebenz.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

// http://localhost:3000/api/auth/register?Content-Type=application/json
// {
//     "nombre": "admin",
//     "apellido1": "apellido1",
//     "apellido2": "apellido2",
//     "usuario": "admin",
//     "contrasena": "123456",
//     "correo": "ad@example.com",
//     "id_rol": "e3b0c442-98fc-11ee-b9d1-0242ac120004"
// }
