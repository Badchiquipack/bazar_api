package com.marriaga.bazar.dto;

public record DatosRespuestaDetalleVentaDTO (
        Long productoId,
        String nombreProducto,
        String marca,
        Double cantidad,
        Double precio
) {
}
