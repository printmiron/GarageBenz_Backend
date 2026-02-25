package com.garagebenz.demo.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garagebenz.demo.dto.LoginRequest;
import com.garagebenz.demo.dto.RegisterRequest;
import com.garagebenz.demo.models.Cliente;
import com.garagebenz.demo.repository.ClienteRepository;
import com.garagebenz.demo.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200") 
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(authService.registrarCliente(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
   
        com.garagebenz.demo.dto.AuthResponseDTO authData = authService.login(request);

        Optional<Cliente> clienteOpt = clienteRepository.findByUsuario(request.getUsuario());

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            Map<String, Object> finalResponse = new HashMap<>();

           
            finalResponse.put("token", authData.getToken());
            finalResponse.put("id", cliente.getIdCliente());
            finalResponse.put("rol", (cliente.getRol() != null) ? cliente.getRol().getNombreRol().toString() : "CLIENTE");

          
            Map<String, Object> userProfile = new HashMap<>();
            userProfile.put("id", cliente.getIdCliente());
            userProfile.put("nombre", cliente.getNombre());
            userProfile.put("apellido1", cliente.getApellido1());
            userProfile.put("apellido2", cliente.getApellido2());
            userProfile.put("correo", cliente.getCorreo());
            userProfile.put("usuario", cliente.getUsuario());
            userProfile.put("rol", finalResponse.get("rol"));

            finalResponse.put("user", userProfile);

            return ResponseEntity.ok(finalResponse);
        }

        return ResponseEntity.ok(authData);
    }
}
