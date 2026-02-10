package com.garagebenz.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.Piezas;

@Repository
public interface PiezasRepository extends JpaRepository<Piezas, UUID> {
}