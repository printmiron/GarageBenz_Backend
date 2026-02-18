package com.garagebenz.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.garagebenz.demo.models.Piezas;
import com.garagebenz.demo.repository.PiezasRepository;

@Service
public class PiezasService implements IPiezasService {

    @Autowired
    private PiezasRepository piezasRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Piezas> listarTodas() {
        return piezasRepository.findAll();
    }

    @Override
    @Transactional
    public Piezas guardar(Piezas pieza) {
        return piezasRepository.save(pieza);
    }

    @Override
    @Transactional(readOnly = true)
    public Piezas buscarPorId(UUID id) {
        return piezasRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        piezasRepository.deleteById(id);
    }
}