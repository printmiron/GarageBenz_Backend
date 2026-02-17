package com.garagebenz.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, UUID> {
    
    
    List<Vehiculo> findByCliente_IdCliente(UUID idCliente);

    Vehiculo findByMatricula(String matricula);

 
    default Optional<Vehiculo> findByIdString(String id) {
        return findById(UUID.fromString(id));
    }

    default void deleteByIdString(String id) {
        deleteById(UUID.fromString(id));
    }
}