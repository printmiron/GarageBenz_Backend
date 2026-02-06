package com.garagebenz.demo.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cliente")
public class Cliente {
    @Id
    @Column(name = "id_cliente", columnDefinition = "CHAR(36)")
    private UUID idCliente;
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido1;

    @Column(nullable = false)
    private String apellido2;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(name = "contraseña", nullable = false)
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @PrePersist
    public void prePersist() {
        if (idCliente == null) {
            idCliente = UUID.randomUUID();
        }
    }

}
