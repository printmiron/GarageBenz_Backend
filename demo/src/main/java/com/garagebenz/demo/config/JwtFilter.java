package com.garagebenz.demo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.garagebenz.demo.service.CustomUserDetailsService;
import com.garagebenz.demo.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        try {
            final String username = jwtService.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // 1. Creamos el token de autenticación
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // 2. Cargamos los detalles de la petición ANTES de setearlo
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 3. Lo metemos en el contexto (UNA SOLA VEZ)
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println(">>> AUTENTICACIÓN FIJADA para: " + username);
                }
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            System.out.println("Error en JwtFilter: " + e.getMessage());
        }

        // 4. Continuamos la cadena
        System.out.println(">>> Filtro finalizado para: "
                + (SecurityContextHolder.getContext().getAuthentication() != null ? "USUARIO IDENTIFICADO" : "NADIE"));
        filterChain.doFilter(request, response);
    }
}
