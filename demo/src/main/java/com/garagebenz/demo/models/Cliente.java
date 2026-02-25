package com.garagebenz.demo.models;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente") 
public class Cliente implements UserDetails {

    @Id
    @JdbcTypeCode(java.sql.Types.VARCHAR)
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

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "cliente-cita")
    private List<Cita> citas;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "cliente-vehiculo")
    private List<Vehiculo> vehiculos;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @PrePersist
    public void prePersist() {
        if (idCliente == null) {
            idCliente = UUID.randomUUID();
        }
    }

  
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        try {
            if (this.rol != null && this.rol.getNombreRol() != null) {
                
                String authority = "ROLE_" + this.rol.getNombreRol().name();
                return List.of(new SimpleGrantedAuthority(authority));
            }
        } catch (Exception e) {
            System.out.println("Error obteniendo roles: " + e.getMessage());
        }
      
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.contrasena;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    @Override
    public String getUsername() {
        return this.usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // La cuenta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // La cuenta no se bloquea
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Las credenciales no expiran
    }

    @Override
    public boolean isEnabled() {
        return true; // El usuario está activo
    }

    // --- GETTERS Y SETTERS ESTÁNDAR ---
    public UUID getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(UUID idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
