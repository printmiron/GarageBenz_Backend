package com.garagebenz.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.garagebenz.demo.models.Cliente;
import com.garagebenz.demo.models.Rol;
import com.garagebenz.demo.models.Trabajador;
import com.garagebenz.demo.repository.ClienteRepository;
import com.garagebenz.demo.repository.RolRepository;
import com.garagebenz.demo.repository.TrabajadorRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private TrabajadorRepository trabajadorRepo;

    @Autowired
    private RolRepository rolRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepo.findAll();
    }

    @Override
    public List<Trabajador> listarTrabajadores() {
        return trabajadorRepo.findAll();
    }

    @Override
    public void eliminarCliente(UUID id) {
        clienteRepo.deleteById(id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void eliminarTrabajador(UUID id) {
        Trabajador trabajador = trabajadorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado: " + id));

    
        trabajadorRepo.delete(trabajador);
    }

    @Override
    public Trabajador guardarTrabajador(Trabajador trabajador) {

        Rol rol = rolRepo.findByNombreRol(Rol.NombreRol.Trabajador)
                .orElseThrow(() -> new RuntimeException("Error: Rol TRABAJADOR no encontrado"));

        trabajador.setRol(rol);

        trabajador.setContrasena(passwordEncoder.encode(trabajador.getContrasena()));

        return trabajadorRepo.save(trabajador);
    }
}
