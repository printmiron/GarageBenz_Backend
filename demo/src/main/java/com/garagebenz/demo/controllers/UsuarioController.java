package com.garagebenz.demo.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garagebenz.demo.models.Cliente;
import com.garagebenz.demo.models.Trabajador;
import com.garagebenz.demo.service.IUsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/clientes")
    public List<Cliente> getClientes() {
        return usuarioService.listarClientes();
    }

    @GetMapping("/trabajadores")
    public List<Trabajador> getTrabajadores() {
        return usuarioService.listarTrabajadores();
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable UUID id) {
        usuarioService.eliminarCliente(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/trabajadores/{id}")
    public ResponseEntity<?> eliminarTrabajador(@PathVariable UUID id) {
        usuarioService.eliminarTrabajador(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/trabajadores")
    public ResponseEntity<Trabajador> crearTrabajador(@RequestBody Trabajador trabajador) {
        return ResponseEntity.ok(usuarioService.guardarTrabajador(trabajador));
    }
}