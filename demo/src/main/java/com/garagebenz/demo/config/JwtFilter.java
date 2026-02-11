package com.garagebenz.demo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private com.garagebenz.demo.service.JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Obtenemos el token del header "Authorization"
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        String path = request.getRequestURI();

        // Log temporal para depurar (mira si esto sale en tu consola de Spring ahora)
        System.out.println("Filtro interceptando ruta: " + path);

        // Si no hay token o la ruta es de login, dejamos pasar sin validar
        if (path.contains("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Como en Angular envíamos el token directo (sin "Bearer "), lo tomamos tal cual
        jwt = authHeader;
        username = jwtService.extractUsername(jwt);

        // 2. Si hay usuario y no está ya autenticado en esta petición
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 3. Si el token es válido, le damos permiso
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecemos la seguridad para esta petición
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 4. Continuar con el siguiente filtro
        filterChain.doFilter(request, response);
    }
}
