package com.garagebenz.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.Trabajador;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, UUID> {
    Optional<Trabajador> findByUsuario(String usuario);
}
