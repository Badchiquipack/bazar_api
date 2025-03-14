package com.marriaga.bazar.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record DatosDetalleVenta(
        @NotNull
        Long productoId,
        @NotNull
        @PositiveOrZero
        Double cantidad
) {
}
