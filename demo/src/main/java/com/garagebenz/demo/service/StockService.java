package com.garagebenz.demo.service;

import java.util.List;
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
public class StockService implements IStockService { // <--- IMPORTANTE: Implementar la interfaz

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrdenesPiezaRepository ordenesPiezaRepository;

    @Override
    @Transactional
    public void consumirPieza(UUID idOr, UUID idPieza, int cantidad) {
        Stock stock = stockRepository.findByPiezaIdPieza(idPieza)
                .orElseThrow(() -> new RuntimeException("No existe stock para la pieza"));

        if (stock.getCantidad() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        stock.setCantidad(stock.getCantidad() - cantidad);
        stockRepository.save(stock);

        OrdenesPieza detalle = new OrdenesPieza();
        OrdenesPiezaId idCompuesto = new OrdenesPiezaId(idOr, idPieza);
        detalle.setId(idCompuesto);
        detalle.setCantidadUsada(cantidad);

        ordenesPiezaRepository.save(detalle);
    }

    @Override
    @Transactional
    public void sumarStock(UUID idPieza, Integer cantidad) {
        // Buscamos el registro de stock por el ID de la pieza
        Stock stock = stockRepository.findByPiezaIdPieza(idPieza)
                .orElseThrow(() -> new RuntimeException("Error: No se encontró registro de stock para esta pieza"));

        // Sumamos la nueva cantidad
        stock.setCantidad(stock.getCantidad() + cantidad);
        
        // Guardamos los cambios
        stockRepository.save(stock);
    }

    @Override
    public List<Stock> listarStock() {
        return stockRepository.findAll();
    }
}