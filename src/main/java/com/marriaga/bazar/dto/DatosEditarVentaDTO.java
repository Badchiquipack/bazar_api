package com.marriaga.bazar.dto;

import jakarta.validation.Valid;

import java.util.List;

public record DatosEditarVentaDTO(
        @Valid
        List<DatosDetalleVenta> detalles,
        Long clienteId
) {
}
