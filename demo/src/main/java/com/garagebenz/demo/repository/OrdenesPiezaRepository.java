package com.garagebenz.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.OrdenesPieza;
import com.garagebenz.demo.models.OrdenesPiezaId;

@Repository
public interface OrdenesPiezaRepository extends JpaRepository<OrdenesPieza, OrdenesPiezaId> {

    

}
