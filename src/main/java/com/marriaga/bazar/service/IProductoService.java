package com.marriaga.bazar.service;

import com.marriaga.bazar.dto.DatosProductoDTO;
import com.marriaga.bazar.dto.DatosProductoEdicionDTO;
import com.marriaga.bazar.dto.RespuestaProductoDTO;

import java.util.List;

public interface IProductoService {

    void agregarProducto(DatosProductoDTO datos);

    List<RespuestaProductoDTO> listarProductos();

    RespuestaProductoDTO listarProductoPorId(Long id);

    RespuestaProductoDTO editarPorId(Long id, DatosProductoEdicionDTO datos);

    void eliminarPorId(Long id);

    List<RespuestaProductoDTO> mostrarExistencias(Integer cantidad);
}
