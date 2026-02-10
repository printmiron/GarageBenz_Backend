package com.garagebenz.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.garagebenz.demo.dto.AuthResponseDTO;
import com.garagebenz.demo.dto.LoginRequest;
import com.garagebenz.demo.repository.AdministradorRepository;
import com.garagebenz.demo.repository.ClienteRepository;
import com.garagebenz.demo.repository.TrabajadorRepository;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepo;
    @Autowired
    private TrabajadorRepository trabajadorRepo;
    @Autowired
    private AdministradorRepository adminRepo;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponseDTO login(LoginRequest request) {

        // 1. Buscamos en la tabla Cliente
        var cliente = clienteRepo.findByUsuario(request.getUsuario());
        if (cliente.isPresent() && passwordEncoder.matches(request.getContraseña(), cliente.get().getContraseña())) {
            return buildResponse(cliente.get().getIdCliente(), cliente.get().getUsuario(), "cliente", cliente.get().getNombre());
        }

        // 2. Si no, buscamos en la tabla Trabajador
        var trabajador = trabajadorRepo.findByUsuario(request.getUsuario());
        if (trabajador.isPresent() && passwordEncoder.matches(request.getContraseña(), trabajador.get().getContraseña())) {
            return buildResponse(trabajador.get().getIdTrabajador(), trabajador.get().getUsuario(), "trabajador", trabajador.get().getNombre());
        }

        // 3. Si no, buscamos en la tabla Administrador
        var admin = adminRepo.findByUsuario(request.getUsuario());
        if (admin.isPresent() && passwordEncoder.matches(request.getContraseña(), admin.get().getContraseña())) {
            return buildResponse(admin.get().getIdAdmin(), admin.get().getUsuario(), "administrador", admin.get().getNombre());
        }

        throw new RuntimeException("Credenciales inválidas");
    }

    private AuthResponseDTO buildResponse(String id, String usuario, String rol, String nombre) {
        String token = jwtService.generateToken(usuario, rol); // Generamos el JWT

        AuthResponseDTO dto = new AuthResponseDTO();
        dto.setToken(token);
        dto.setId(id);
        dto.setRol(rol); // Esto es lo que Angular recibe y pone en minúsculas
        dto.setNombreUsuario(nombre);
        return dto;
    }
}
