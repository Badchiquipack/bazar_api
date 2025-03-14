package com.marriaga.bazar.dto;

public record RespuestaVentaMayor(
        Long ventaId,
        Double total,
        Double cantidad,
        String nombreCliente,
        String apellidoCliente
) {
}
