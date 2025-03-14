package com.marriaga.bazar.service;

import com.marriaga.bazar.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface IVentaService {

    void crearVenta(DatosVentaDTO datosVentaDTO);

    List<RespuestaVentaDTO> mostrarVentas();

    RespuestaVentaDTO mostrarVentaPorId(Long id);

    RespuestaVentaDTO editarPorId(Long id, DatosEditarVentaDTO datosEditarVentaDTO);

    void eliminarPorId(Long id);

    List<DatosRespuestaDetalleVentaDTO> mostrarProductosVentaPorId(Long id);

    RespuestaVentaDiaDTO ventaDia(LocalDate fecha);

    RespuestaVentaMayor mostrarVentaMayor();

}
