package com.garagebenz.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garagebenz.demo.models.Vehiculo;
import com.garagebenz.demo.repository.VehiculoRepository;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> findById(String id) {
        // Convertimos el String a UUID para el repositorio
        return vehiculoRepository.findById(UUID.fromString(id));
    }

    public List<Vehiculo> findByClienteId(String idCliente) {
        // Usamos el método de navegación: Cliente -> idCliente
        return vehiculoRepository.findByCliente_IdCliente(UUID.fromString(idCliente));
    }

    public Vehiculo save(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public void deleteById(String id) {
        vehiculoRepository.deleteById(UUID.fromString(id));
    }
}