package com.garagebenz.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.garagebenz.demo.config.UsuarioPrincipal;
import com.garagebenz.demo.repository.AdministradorRepository;
import com.garagebenz.demo.repository.ClienteRepository;
import com.garagebenz.demo.repository.TrabajadorRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private ClienteRepository clienteRepo;
    @Autowired private TrabajadorRepository trabajadorRepo;
    @Autowired private AdministradorRepository adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 1. Intentar buscar en Clientes
        var cliente = clienteRepo.findByUsuario(username);
        if (cliente.isPresent()) {
            return new UsuarioPrincipal(cliente.get().getUsuario(), cliente.get().getContrasena(), "CLIENTE");
        }

        // 2. Intentar buscar en Trabajadores
        var trabajador = trabajadorRepo.findByUsuario(username);
        if (trabajador.isPresent()) {
            return new UsuarioPrincipal(trabajador.get().getUsuario(), trabajador.get().getContrasena(), "TRABAJADOR");
        }

        // 3. Intentar buscar en Admins
        var admin = adminRepo.findByUsuario(username);
        if (admin.isPresent()) {
            return new UsuarioPrincipal(admin.get().getUsuario(), admin.get().getContrasena(), "ADMINISTRADOR");
        }

        throw new UsernameNotFoundException("Usuario no encontrado en ninguna tabla: " + username);
    }
}
