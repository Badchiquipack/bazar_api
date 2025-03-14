package com.marriaga.bazar.service;

import com.marriaga.bazar.dto.DatosClienteDTO;
import com.marriaga.bazar.dto.RespuestaClienteDTO;

import java.util.List;

public interface IClienteService {

    void crearCliente(DatosClienteDTO cliente);

    List<RespuestaClienteDTO> listarTodos();

    RespuestaClienteDTO listarPorId(Long id);

    RespuestaClienteDTO editarPorId(Long id, DatosClienteDTO cliente);

    void eliminarPorId(Long id);
}
