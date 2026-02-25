package com.garagebenz.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, UUID> {
    Optional<Administrador> findByUsuario(String usuario);
}
