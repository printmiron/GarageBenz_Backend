package com.garagebenz.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garagebenz.demo.models.OrdenesPieza;
import com.garagebenz.demo.models.OrdenesPiezaId;
import com.garagebenz.demo.models.Stock;
import com.garagebenz.demo.repository.OrdenesPiezaRepository;
import com.garagebenz.demo.repository.StockRepository;

import jakarta.transaction.Transactional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrdenesPiezaRepository ordenesPiezaRepository;

    @Transactional
    public void consumirPieza(UUID idOr, UUID idPieza, int cantidad) {
        // 1. Buscar stock (usando el método corregido del repositorio anterior)
        Stock stock = stockRepository.findByPiezaIdPieza(idPieza)
                .orElseThrow(() -> new RuntimeException("No existe stock para la pieza"));

        if (stock.getCantidad() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        // 2. Restar stock
        stock.setCantidad(stock.getCantidad() - cantidad);
        stockRepository.save(stock);

        // 3. Registrar en la tabla intermedia (CORRECCIÓN AQUÍ)
        OrdenesPieza detalle = new OrdenesPieza();

        // Creamos el ID compuesto con los dos UUIDs
        OrdenesPiezaId idCompuesto = new OrdenesPiezaId(idOr, idPieza);
        detalle.setId(idCompuesto); // Ahora usamos setId() con el objeto ID

        detalle.setCantidadUsada(cantidad);

        ordenesPiezaRepository.save(detalle);
    }
}
