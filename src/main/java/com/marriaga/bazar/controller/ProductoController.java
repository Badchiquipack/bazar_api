package com.marriaga.bazar.controller;

import com.marriaga.bazar.dto.DatosProductoDTO;
import com.marriaga.bazar.dto.DatosProductoEdicionDTO;
import com.marriaga.bazar.service.IProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private IProductoService service;

    @PostMapping("/agregar")
    public ResponseEntity agregarProducto(@RequestBody @Valid DatosProductoDTO datos) {
        service.agregarProducto(datos);
        return ResponseEntity.ok("Producto agregado correctamente");
    }

    @GetMapping("/listar")
    public ResponseEntity listarProductos() {
        return ResponseEntity.ok(service.listarProductos());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity listarProductosPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarProductoPorId(id));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity editarPorId(@PathVariable Long id,
                                      @RequestBody DatosProductoEdicionDTO datos) {
        return ResponseEntity.ok(service.editarPorId(id, datos));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity eliminarPorId(@PathVariable Long id) {
        service.eliminarPorId(id);
        return ResponseEntity.ok("Producto eliminado correctamente.");
    }

    @GetMapping("/existencias/{cantidad}")
    public ResponseEntity mostrarExistencias(@PathVariable Integer cantidad){
        return ResponseEntity.ok(service.mostrarExistencias(cantidad));
    }

}
