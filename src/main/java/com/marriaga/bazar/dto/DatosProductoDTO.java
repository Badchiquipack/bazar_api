package com.marriaga.bazar.dto;

import jakarta.validation.constraints.NotNull;

public record DatosProductoDTO(
        @NotNull
        String nombreProducto,
        @NotNull
        String marca,
        @NotNull
        Double costo,
        @NotNull
        Double stock) {
}
