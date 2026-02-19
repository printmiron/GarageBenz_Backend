package com.garagebenz.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {

    // Si Cliente usa UUID, esto está bien, pero si usa String, cámbialo también
    List<Vehiculo> findByCliente_IdCliente(UUID idCliente);

    Vehiculo findByMatricula(String matricula);

    // Estos métodos ya no necesitan UUID.fromString porque ahora TODO es String
    default Optional<Vehiculo> findByIdString(String id) {
        return findById(id);
    }

    default void deleteByIdString(String id) {
        deleteById(id);
    }
}
