package com.garagebenz.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.garagebenz.demo.dto.AuthResponseDTO;
import com.garagebenz.demo.dto.LoginRequest;
import com.garagebenz.demo.dto.RegisterRequest;
import com.garagebenz.demo.models.Cliente;
import com.garagebenz.demo.models.Rol;
import com.garagebenz.demo.repository.AdministradorRepository;
import com.garagebenz.demo.repository.ClienteRepository;
import com.garagebenz.demo.repository.RolRepository;
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
    @Autowired 
    private RolRepository rolRepo;

    public AuthResponseDTO login(LoginRequest request) {
        String username = request.getUsuario();
        String passwordEnviada = request.getContrasena();

        // 1. Buscar en Clientes
        var clienteOpt = clienteRepo.findByUsuario(username);
        if (clienteOpt.isPresent()) {
            String hashDB = clienteOpt.get().getContrasena().trim(); // .trim() por seguridad
            if (passwordEncoder.matches(passwordEnviada, hashDB)) {
                return buildResponse(clienteOpt.get().getIdCliente().toString(), username, "cliente", clienteOpt.get().getNombre());

            }
            System.out.println("DEBUG -> Password enviada: " + passwordEnviada);
            System.out.println("DEBUG -> Hash en DB: " + hashDB);
            // Dentro de if (clienteOpt.isPresent())
            System.out.println("Usuario encontrado en DB: " + clienteOpt.get().getUsuario());
            System.out.println("Hash en DB: [" + hashDB + "]");
            boolean coincide = passwordEncoder.matches(passwordEnviada, hashDB);
            System.out.println("¿La contraseña coincide?: " + coincide);
            // Sustituye temporalmente tu if por esto para probar:
            boolean pruebaManual = passwordEncoder.matches("12345", "$2a$10$8.UnVuG9HHgffUDAlk8q6Ou5HEMFYvYZpuOTjDG7p8EDLpSOfS5S2");
            System.out.println("PRUEBA HARDCODED: " + pruebaManual);
        }

        // 2. Buscar en Trabajador
        var trabajadorOpt = trabajadorRepo.findByUsuario(username);
        if (trabajadorOpt.isPresent()) {
            String hashDB = trabajadorOpt.get().getContrasena().trim();
            if (passwordEncoder.matches(passwordEnviada, hashDB)) {
                return buildResponse(trabajadorOpt.get().getIdTrabajador().toString(), username, "trabajador", trabajadorOpt.get().getNombre());
            }
        }

        // 3. Buscar en Administrador
        var adminOpt = adminRepo.findByUsuario(username);
        if (adminOpt.isPresent()) {
            String hashDB = adminOpt.get().getContrasena().trim();
            if (passwordEncoder.matches(passwordEnviada, hashDB)) {
                return buildResponse(adminOpt.get().getIdAdmin().toString(), username, "administrador", adminOpt.get().getNombre());
            }
        }

        // Si llegó aquí es que nada coincidió
        System.out.println("Fallo de login para: " + username);
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

    public String registrarCliente(RegisterRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setApellido1(request.getApellido1());
        cliente.setApellido2(request.getApellido2());
        cliente.setUsuario(request.getUsuario());
        cliente.setCorreo(request.getCorreo());
        cliente.setContrasena(passwordEncoder.encode(request.getContrasena()));

        // BUSCAMOS EL OBJETO ROL POR EL UUID QUE VIENE DE POSTMAN
        UUID uuidRol = UUID.fromString(request.getId_rol());
        Rol rol = rolRepo.findById(uuidRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        cliente.setRol(rol); // Ahora sí, pasamos el objeto completo

        clienteRepo.save(cliente);
        return "Cliente registrado con éxito";
    }

}
