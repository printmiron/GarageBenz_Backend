package com.garagebenz.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByUsuario(String usuario);
}
