package com.garagebenz.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, UUID> {
    Optional<Rol> findByNombreRol(Rol.NombreRol nombreRol);
}
