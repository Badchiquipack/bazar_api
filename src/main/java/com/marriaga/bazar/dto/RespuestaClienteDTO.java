package com.marriaga.bazar.dto;

import com.marriaga.bazar.model.Cliente;

public record RespuestaClienteDTO(Long clienteId, String nombre, String apellido, String dni) {
    public RespuestaClienteDTO(Cliente cliente) {
        this(cliente.getClienteId(), cliente.getNombreCliente(), cliente.getApellido(), cliente.getDni());
    }
}
