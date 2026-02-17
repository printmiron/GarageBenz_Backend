package com.garagebenz.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.garagebenz.demo.dto.AuthResponseDTO;
import com.garagebenz.demo.dto.LoginRequest;
import com.garagebenz.demo.dto.RegisterRequest;
import com.garagebenz.demo.models.Administrador;
import com.garagebenz.demo.models.Cliente;
import com.garagebenz.demo.models.Rol;
import com.garagebenz.demo.models.Trabajador;
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
            Cliente c = clienteOpt.get();
            if (passwordEncoder.matches(passwordEnviada, c.getContrasena().trim())) {
                String rolReal = c.getRol().getNombreRol().name();
                // PASAMOS EL OBJETO 'c' COMPLETO
                return buildResponse(c, username, rolReal);
            }
        }

        // 2. Buscar en Trabajador
        var trabajadorOpt = trabajadorRepo.findByUsuario(username);
        if (trabajadorOpt.isPresent()) {
            Trabajador t = trabajadorOpt.get();
            if (passwordEncoder.matches(passwordEnviada, t.getContrasena().trim())) {
                String rolReal = t.getRol().getNombreRol().name();
                // PASAMOS EL OBJETO 't' COMPLETO
                return buildResponse(t, username, rolReal);
            }
        }

        // 3. Buscar en Administrador
        var adminOpt = adminRepo.findByUsuario(username);
        if (adminOpt.isPresent()) {
            Administrador a = adminOpt.get();
            if (passwordEncoder.matches(passwordEnviada, a.getContrasena().trim())) {
                String rolReal = a.getRol().getNombreRol().name();
                // PASAMOS EL OBJETO 'a' COMPLETO
                return buildResponse(a, username, rolReal);
            }
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

    public String registrarCliente(RegisterRequest request) {
        // 1. Buscamos el objeto Rol primero para saber qué estamos registrando
        UUID uuidRol = UUID.fromString(request.getId_rol());
        Rol rol = rolRepo.findById(uuidRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        String nombreRol = rol.getNombreRol().name(); // Esto devolverá 'Cliente', 'Trabajador' o 'Administrador'
        String passwordHaseada = passwordEncoder.encode(request.getContrasena());
        UUID nuevoId = UUID.randomUUID();

        // 2. Lógica de guardado según el nombre del rol
        if (nombreRol.equalsIgnoreCase("Trabajador")) {
            com.garagebenz.demo.models.Trabajador t = new com.garagebenz.demo.models.Trabajador();
            t.setIdTrabajador(nuevoId);
            t.setNombre(request.getNombre());
            t.setApellido1(request.getApellido1());
            t.setApellido2(request.getApellido2());
            t.setUsuario(request.getUsuario());
            t.setCorreo(request.getCorreo());
            t.setContrasena(passwordHaseada);
            t.setRol(rol);
            trabajadorRepo.save(t);
            return "Trabajador registrado con éxito";

        } else if (nombreRol.equalsIgnoreCase("Administrador")) {
            com.garagebenz.demo.models.Administrador a = new com.garagebenz.demo.models.Administrador();
            a.setIdAdmin(nuevoId);
            a.setNombre(request.getNombre());
            a.setApellido1(request.getApellido1());
            a.setApellido2(request.getApellido2());
            a.setUsuario(request.getUsuario());
            a.setCorreo(request.getCorreo());
            a.setContrasena(passwordHaseada);
            a.setRol(rol);
            adminRepo.save(a);
            return "Administrador registrado con éxito";

        } else {
            // Por defecto o si es Cliente
            Cliente c = new Cliente();
            c.setIdCliente(nuevoId);
            c.setNombre(request.getNombre());
            c.setApellido1(request.getApellido1());
            c.setApellido2(request.getApellido2());
            c.setUsuario(request.getUsuario());
            c.setCorreo(request.getCorreo());
            c.setContrasena(passwordHaseada);
            c.setRol(rol);
            clienteRepo.save(c);
            return "Cliente registrado con éxito";
        }
    }

    // Tu método buildResponse que ya tienes está perfecto, solo asegúrate de que use Object
    private AuthResponseDTO buildResponse(Object userEntity, String usuario, String rol) {
        String token = jwtService.generateToken(usuario, rol);
        AuthResponseDTO dto = new AuthResponseDTO();
        dto.setToken(token);
        dto.setRol(rol.toLowerCase());

        Map<String, Object> userData = new HashMap<>();

        if (userEntity instanceof Cliente) {
            Cliente c = (Cliente) userEntity;
            userData.put("id", c.getIdCliente());
            userData.put("nombre", c.getNombre());
            userData.put("apellido1", c.getApellido1());
            userData.put("apellido2", c.getApellido2());
            userData.put("correo", c.getCorreo());
        } else if (userEntity instanceof Trabajador) {
            Trabajador t = (Trabajador) userEntity;
            userData.put("id", t.getIdTrabajador());
            userData.put("nombre", t.getNombre());
            userData.put("apellido1", t.getApellido1());
            userData.put("apellido2", t.getApellido2());
            userData.put("correo", t.getCorreo());
        } else if (userEntity instanceof Administrador) {
            Administrador a = (Administrador) userEntity;
            userData.put("id", a.getIdAdmin());
            userData.put("nombre", a.getNombre());
            userData.put("apellido1", a.getApellido1());
            userData.put("apellido2", a.getApellido2());
            userData.put("correo", a.getCorreo());
        }

        userData.put("usuario", usuario);
        userData.put("rol", rol.toLowerCase());

        dto.setUser(userData); // Asegúrate de que el DTO tenga este campo
        dto.setId(userData.get("id").toString()); // Para no romper lo que ya tenías
        dto.setNombreUsuario(usuario);

        return dto;
    }

}
