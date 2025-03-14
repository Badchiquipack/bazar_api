package com.marriaga.bazar.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DatosVentaDTO(
        @NotNull
        @Valid
        List<DatosDetalleVenta> detalles,
        @NotNull
        Long clienteId
) {
}
