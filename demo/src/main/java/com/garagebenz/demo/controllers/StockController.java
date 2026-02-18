package com.garagebenz.demo.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garagebenz.demo.models.Stock;
import com.garagebenz.demo.repository.StockRepository;
import com.garagebenz.demo.service.StockService;

@RestController
@RequestMapping("/stock")
@CrossOrigin(origins = "http://localhost:4200")
public class StockController {

    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private StockService stockService;

    @GetMapping
    public List<Stock> getAllStock() {
        return stockRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Stock> guardarStock(@RequestBody Stock nuevoStock) {

        Stock guardado = stockRepository.save(nuevoStock);
        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }

    @PostMapping("/consumir")
    public ResponseEntity<?> consumirPieza(@RequestParam UUID idOr,
            @RequestParam UUID idPieza,
            @RequestParam int cantidad) {
        try {
            stockService.consumirPieza(idOr, idPieza, cantidad);
            return ResponseEntity.ok().body("Pieza añadida a la orden y stock actualizado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
