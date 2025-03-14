package com.marriaga.bazar.dto;

import java.time.LocalDate;
import java.util.List;

public record RespuestaVentaDTO(
        Long ventaId,
        LocalDate fechaVenta,
        Double total,
        List<DatosRespuestaDetalleVentaDTO> detalle,
        RespuestaClienteDTO respuestaClienteDTO) {
}
