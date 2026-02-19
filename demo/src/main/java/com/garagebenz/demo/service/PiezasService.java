package com.garagebenz.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.garagebenz.demo.dto.PiezaCrearDTO;
import com.garagebenz.demo.models.Piezas;
import com.garagebenz.demo.models.Stock;
import com.garagebenz.demo.repository.PiezasRepository;
import com.garagebenz.demo.repository.StockRepository;

@Service
public class PiezasService implements IPiezasService {

    @Autowired
    private PiezasRepository piezasRepo;
    @Autowired
    private StockRepository stockRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Piezas> listarTodas() {
        return piezasRepo.findAll();
    }

    @Override
    @Transactional
    public Piezas guardar(Piezas pieza) {
        return piezasRepo.save(pieza);
    }

    @Override
    @Transactional(readOnly = true)
    public Piezas buscarPorId(UUID id) {
        return piezasRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        piezasRepo.deleteById(id);
    }

    @Transactional
    public Piezas crearPiezaConStock(PiezaCrearDTO dto) {
        // 1. Crear y guardar la pieza
        Piezas nuevaPieza = new Piezas();
        nuevaPieza.setNombre(dto.nombre);
        nuevaPieza.setDescripcion(dto.descripcion);
        nuevaPieza.setPrecio(dto.precio);
        Piezas piezaGuardada = piezasRepo.save(nuevaPieza);

        // 2. Crear el registro de stock vinculado
        Stock nuevoStock = new Stock();
        nuevoStock.setIdStock(UUID.randomUUID());
        nuevoStock.setPieza(piezaGuardada);
        nuevoStock.setCantidad(dto.cantidadInicial);
        stockRepo.save(nuevoStock);

        return piezaGuardada;
    }
}
