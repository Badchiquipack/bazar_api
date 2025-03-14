package com.marriaga.bazar.service;

import com.marriaga.bazar.dto.DatosClienteDTO;
import com.marriaga.bazar.dto.RespuestaClienteDTO;
import com.marriaga.bazar.infra.excepciones.ValidacionException;
import com.marriaga.bazar.model.Cliente;
import com.marriaga.bazar.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository repository;

    @Override
    public void crearCliente(DatosClienteDTO cliente) {

        boolean existente = repository.existsByDni(cliente.dni());

        if (!existente) {
            Cliente clienteNuevo = new Cliente();
            clienteNuevo.setNombreCliente(cliente.nombre());
            clienteNuevo.setApellido(cliente.apellido());
            clienteNuevo.setDni(cliente.dni());
            clienteNuevo.setEstado(true);
            repository.save(clienteNuevo);
        } else {
            throw new ValidacionException("El cliente con el dni " + cliente.dni() + " ya existe.");
        }
    }

    @Override
    public List<RespuestaClienteDTO> listarTodos() {
        List<RespuestaClienteDTO> clientes = repository.findAll()
                .stream().filter(cliente -> cliente.getEstado().equals(true))
                .map(RespuestaClienteDTO::new).toList();
        return clientes;
    }

    @Override
    public RespuestaClienteDTO listarPorId(Long id) {
        if (repository.existsById(id) &&
                repository.findById(id).orElseGet(null).getEstado().equals(true)) {
            return new RespuestaClienteDTO(repository.getReferenceById(id));
        } else {
            throw new ValidacionException("El cliente con el id " + id + " no existe.");
        }
    }

    @Override
    public RespuestaClienteDTO editarPorId(Long id, DatosClienteDTO cliente) {
        if (repository.existsById(id) &&
                repository.findById(id).orElseGet(null).getEstado().equals(true)) {
            Cliente clienteAnterior = repository.getReferenceById(id);
            if (cliente.nombre() != null) {
                clienteAnterior.setNombreCliente(cliente.nombre());
            }
            if (cliente.apellido() != null) {
                clienteAnterior.setApellido(cliente.apellido());
            }
            if (cliente.dni() != null) {
                clienteAnterior.setDni(cliente.dni());
            }
            repository.save(clienteAnterior);
            return new RespuestaClienteDTO(clienteAnterior);
        } else {
            throw new ValidacionException("El cliente con el id que deseas editar no existe");
        }
    }

    @Override
    public void eliminarPorId(Long id) {
        if (repository.existsById(id) &&
                repository.findById(id).orElseGet(null).getEstado().equals(true)) {
            Cliente cliente = repository.getReferenceById(id);
            cliente.setEstado(false);
            repository.save(cliente);
        }else {
            throw new ValidacionException("El cliente que deseas eliminar no existe");
        }
    }

}
