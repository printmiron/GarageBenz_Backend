package com.garagebenz.demo.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garagebenz.demo.models.OrdenReparacion;
import com.garagebenz.demo.models.OrdenesPieza;
import com.garagebenz.demo.models.OrdenesPiezaId;
import com.garagebenz.demo.models.Piezas;
import com.garagebenz.demo.models.Stock;
import com.garagebenz.demo.repository.OrdenReparacionRepository;
import com.garagebenz.demo.repository.OrdenesPiezaRepository;
import com.garagebenz.demo.repository.PiezasRepository;
import com.garagebenz.demo.repository.StockRepository;
import com.garagebenz.demo.service.IStockService;

@RestController
@RequestMapping("/stock")
@CrossOrigin(origins = "http://localhost:4200")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private IStockService stockService;

    @Autowired
    private OrdenReparacionRepository ordenRepo;

    @Autowired
    private PiezasRepository piezasRepo;

    @Autowired
    private OrdenesPiezaRepository ordenesPiezaRepo;

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
    public ResponseEntity<?> consumirStock(
            @RequestParam UUID idOr,
            @RequestParam UUID idPieza,
            @RequestParam int cantidad) {

     
        OrdenReparacion orden = ordenRepo.findById(idOr)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + idOr));

        Piezas piezas = piezasRepo.findById(idPieza)
                .orElseThrow(() -> new RuntimeException("Pieza no encontrada con ID: " + idPieza));

     
        OrdenesPiezaId idCompuesto = new OrdenesPiezaId(idOr, idPieza);

        OrdenesPieza registro = new OrdenesPieza();
        registro.setId(idCompuesto);
        registro.setOrden(orden);
        registro.setPieza(piezas);
        registro.setCantidadUsada(cantidad);


        ordenesPiezaRepo.save(registro);

        return ResponseEntity.ok("Operación exitosa: Pieza vinculada a la orden.");
    }


    @PutMapping("/reponer")
    public ResponseEntity<?> reponerStock(@RequestBody Map<String, Object> payload) {
   
        UUID idPieza = UUID.fromString(payload.get("idPieza").toString());

        Integer cantidad = Integer.parseInt(payload.get("cantidad").toString());

        stockService.sumarStock(idPieza, cantidad);
        return ResponseEntity.ok().build();
    }
}
