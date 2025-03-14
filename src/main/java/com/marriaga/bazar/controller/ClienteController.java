package com.marriaga.bazar.controller;

import com.marriaga.bazar.dto.DatosClienteDTO;
import com.marriaga.bazar.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private IClienteService service;

    @PostMapping("/crear")
    public ResponseEntity crearCliente(@RequestBody @Valid DatosClienteDTO cliente) {
        service.crearCliente(cliente);
        return ResponseEntity.ok("Cliente creado correctamente!");
    }

    @GetMapping("/mostrar")
    public ResponseEntity listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/mostrar/{id}")
    public ResponseEntity listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity editarPorId(@PathVariable Long id,
                                      @RequestBody DatosClienteDTO cliente) {
        return ResponseEntity.ok(service.editarPorId(id, cliente));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity eliminarPorId(@PathVariable Long id) {
        service.eliminarPorId(id);
        return ResponseEntity.ok("Cliente eliminado correctamente.");
    }
}
