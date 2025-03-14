package com.marriaga.bazar.service;

import com.marriaga.bazar.dto.DatosProductoDTO;
import com.marriaga.bazar.dto.DatosProductoEdicionDTO;
import com.marriaga.bazar.dto.RespuestaProductoDTO;
import com.marriaga.bazar.infra.excepciones.ValidacionException;
import com.marriaga.bazar.model.Producto;
import com.marriaga.bazar.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoRepository repository;

    @Override
    public void agregarProducto(DatosProductoDTO datos) {
        Producto producto = new Producto();
        producto.setNombreProducto(datos.nombreProducto());
        producto.setMarca(datos.marca());
        producto.setCosto(datos.costo());
        producto.setStock(datos.stock());
        producto.setEstado(true);
        repository.save(producto);
    }

    @Override
    public List<RespuestaProductoDTO> listarProductos() {
        return repository.findAll().stream()
                .filter(producto -> producto.getEstado().equals(true))
                .map(RespuestaProductoDTO::new).toList();
    }

    @Override
    public RespuestaProductoDTO listarProductoPorId(Long id) {
        if (repository.existsById(id) &&
                repository.findById(id).orElseGet(null).getEstado().equals(true)) {
            return new RespuestaProductoDTO(repository.findById(id).orElseGet(null));
        } else {
            throw new ValidacionException("El producto con el id proporcionado no existe.");
        }
    }

    @Override
    public RespuestaProductoDTO editarPorId(Long id, DatosProductoEdicionDTO datos) {
        if (repository.existsById(id) &&
                repository.findById(id).orElseGet(null).getEstado().equals(true)) {
            Producto productoAnterior = repository.getReferenceById(id);
            if (datos.nombreProducto() != null) {
                productoAnterior.setNombreProducto(datos.nombreProducto());
            }
            if (datos.marca() != null) {
                productoAnterior.setMarca(datos.marca());
            }
            if (datos.costo() != null) {
                productoAnterior.setCosto(datos.costo());
            }
            if (datos.stock() != null) {
                productoAnterior.setStock(datos.stock());
            }
            repository.save(productoAnterior);
            return new RespuestaProductoDTO(productoAnterior);
        } else {
            throw new ValidacionException("El producto con el id que deseas editar no existe.");
        }
    }

    @Override
    public void eliminarPorId(Long id) {
        if (repository.existsById(id) &&
                repository.findById(id).orElseGet(null).getEstado().equals(true)) {
            Producto productoAnterior = repository.getReferenceById(id);
            productoAnterior.setEstado(false);
            repository.save(productoAnterior);
        } else {
            throw new ValidacionException("El producto con el id que deseas eliminar no existe.");
        }
    }

    @Override
    public List<RespuestaProductoDTO> mostrarExistencias(Integer cantidad) {
        return this.listarProductos().stream()
                .filter(respuestaProductoDTO -> respuestaProductoDTO.stock() < cantidad).toList();
    }


}
