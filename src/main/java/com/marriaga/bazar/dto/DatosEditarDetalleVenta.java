package com.marriaga.bazar.dto;

import jakarta.validation.constraints.PositiveOrZero;

public record DatosEditarDetalleVenta(
        Long productoId,
        @PositiveOrZero
        Double cantidad
) {
}
