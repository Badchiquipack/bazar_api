package com.marriaga.bazar.dto;

import java.time.LocalDate;

public record RespuestaVentaDiaDTO(
        LocalDate fecha,
        Double totalVenta,
        Double cantidadVendida
) {
}
