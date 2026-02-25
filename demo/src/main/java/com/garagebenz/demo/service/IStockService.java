package com.garagebenz.demo.service;

import java.util.List;
import java.util.UUID;

import com.garagebenz.demo.models.Stock;

public interface IStockService {
 
    void consumirPieza(UUID idOr, UUID idPieza, int cantidad);
    
    void sumarStock(UUID idPieza, Integer cantidad);
    
    List<Stock> listarStock();
}