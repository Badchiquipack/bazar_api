package com.marriaga.bazar.dto;

import jakarta.validation.constraints.NotNull;

public record DatosClienteDTO(
        @NotNull
        String nombre,
        @NotNull
        String apellido,
        @NotNull
        String dni
) {
}
