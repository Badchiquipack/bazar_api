package com.marriaga.bazar.controller;

import com.marriaga.bazar.dto.DatosEditarVentaDTO;
import com.marriaga.bazar.dto.DatosVentaDTO;
import com.marriaga.bazar.service.IVentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private IVentaService service;

    @PostMapping("/crear")
    public ResponseEntity crearVenta(@RequestBody @Valid DatosVentaDTO datosVentaDTO) {
        service.crearVenta(datosVentaDTO);
        return ResponseEntity.ok("Venta registrada correctamente.");
    }

    @GetMapping("/mostrar")
    public ResponseEntity mostrarVentas() {
        return ResponseEntity.ok(service.mostrarVentas());
    }

    @GetMapping("/mostrar/{id}")
    public ResponseEntity mostrarVentaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.mostrarVentaPorId(id));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity editarPorId(@PathVariable Long id,
                                      @RequestBody DatosEditarVentaDTO datosEditarVentaDTO){
        return ResponseEntity.ok(service.editarPorId(id, datosEditarVentaDTO));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity eliminarPorId(@PathVariable Long id){
        service.eliminarPorId(id);
        return ResponseEntity.ok("Venta eliminada correctamente");
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity mostrarProductosVentaPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.mostrarProductosVentaPorId(id));
    }

    @GetMapping("/total/{fecha}")
    public ResponseEntity ventaDia(@PathVariable LocalDate fecha){
        return ResponseEntity.ok(service.ventaDia(fecha));
    }

    @GetMapping("/venta_mayor")
    public ResponseEntity mostrarVentaMayor(){
        return ResponseEntity.ok(service.mostrarVentaMayor());
    }
}
