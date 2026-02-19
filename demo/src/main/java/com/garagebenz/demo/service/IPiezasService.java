package com.garagebenz.demo.service;

import java.util.List;
import java.util.UUID;

import com.garagebenz.demo.dto.PiezaCrearDTO;
import com.garagebenz.demo.models.Piezas;

public interface IPiezasService {
    List<Piezas> listarTodas();
    Piezas guardar(Piezas pieza);
    Piezas buscarPorId(UUID id);
    void eliminar(UUID id);
    Piezas crearPiezaConStock(PiezaCrearDTO dto);
}