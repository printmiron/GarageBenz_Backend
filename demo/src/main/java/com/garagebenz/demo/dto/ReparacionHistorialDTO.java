package com.garagebenz.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ReparacionHistorialDTO {
    String getIdOr();
    String getMatricula();
    String getModelo();
    String getDiagnostico();
    LocalDate getFechaFin();
    BigDecimal getHoras();
    String getNombreTrabajador();
}