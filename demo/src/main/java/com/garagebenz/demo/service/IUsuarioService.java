package com.garagebenz.demo.service;

import java.util.List;
import java.util.UUID;

import com.garagebenz.demo.models.Cliente;
import com.garagebenz.demo.models.Trabajador;

public interface IUsuarioService {
    List<Cliente> listarClientes();
    List<Trabajador> listarTrabajadores();
    void eliminarCliente(UUID id);
    void eliminarTrabajador(UUID id);
    Trabajador guardarTrabajador(Trabajador trabajador);
}