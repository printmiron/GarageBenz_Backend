package com.garagebenz.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.OrdenReparacion;

@Repository
public interface OrdenReparacionRepository extends JpaRepository<OrdenReparacion, UUID> {

}
