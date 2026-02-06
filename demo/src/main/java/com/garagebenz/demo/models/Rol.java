package com.garagebenz.demo.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Rol")
public class Rol {

    @Id
    @Column(name = "id_rol", columnDefinition = "CHAR(36)")
    private UUID idRol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NombreRol nombreRol;

    public enum NombreRol {
        Cliente,
        Trabajador,
        Administrador
    }

    @PrePersist
    public void prePersist() {
        if (idRol == null) {
            idRol = UUID.randomUUID();
        }
    }
    
}
