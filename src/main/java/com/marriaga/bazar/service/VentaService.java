package com.marriaga.bazar.service;

import com.marriaga.bazar.dto.*;
import com.marriaga.bazar.infra.excepciones.ValidacionException;
import com.marriaga.bazar.model.Cliente;
import com.marriaga.bazar.model.DetalleVenta;
import com.marriaga.bazar.model.Producto;
import com.marriaga.bazar.model.Venta;
import com.marriaga.bazar.repository.IClienteRepository;
import com.marriaga.bazar.repository.IDetalleVentaRepository;
import com.marriaga.bazar.repository.IProductoRepository;
import com.marriaga.bazar.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private IVentaRepository repository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IDetalleVentaRepository detalleVentaRepository;

    @Transactional
    @Override
    public void crearVenta(DatosVentaDTO datosVentaDTO) {

        //Validando que el id del Cliente existe en la BD y es válido
        if (!clienteRepository.existsById(datosVentaDTO.clienteId()) ||
                clienteRepository.findById(datosVentaDTO.clienteId())
                        .orElseGet(null).getEstado().equals(false)) {
            throw new ValidacionException("El id del cliente proporcionado no existe");
        }

        //Validando que el id de los productos proporcionados existan en la BD y es válido
        datosVentaDTO.detalles().forEach(datosDetalleVenta -> {
            if (!productoRepository.existsById(datosDetalleVenta.productoId()) ||
                    productoRepository.findById(datosDetalleVenta.productoId())
                            .orElseGet(null).getEstado().equals(false)) {
                throw new ValidacionException("El id " + datosDetalleVenta.productoId() +
                        " del producto proporcionado no existe");
            }
        });

        //Validando que el stock de los productos de la venta existan
        datosVentaDTO.detalles().forEach(detalleVenta -> {
            if (detalleVenta.cantidad() >
                    productoRepository.getReferenceById(detalleVenta.productoId()).getStock()) {
                throw new ValidacionException("No hay suficiente stock disponible para el producto con id " +
                        detalleVenta.productoId());
            }
        });

        //Creando la venta y asignando valores
        Venta venta = new Venta();

        List<DetalleVenta> detalles = datosVentaDTO.detalles().stream().map(datosDetalleVenta -> {
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setVenta(venta);
            detalleVenta.setProducto(productoRepository
                    .findById(datosDetalleVenta.productoId()).orElseGet(null));
            detalleVenta.setCantidad(datosDetalleVenta.cantidad());
            return detalleVenta;
        }).toList();

        venta.setFechaVenta(LocalDate.now());
        venta.setCliente(clienteRepository.findById(datosVentaDTO.clienteId()).orElseGet(null));
        venta.setDetalles(detalles);
        venta.setEstado(true);
        venta.setTotal(detalles.stream()
                .map(detalleVenta -> detalleVenta.getCantidad() * detalleVenta.getProducto().getCosto())
                .reduce(0.0, Double::sum));

        //Modificando el stock disponible de los productos de la venta
        datosVentaDTO.detalles().forEach(detalleVenta -> {
            Producto producto = productoRepository.getReferenceById(detalleVenta.productoId());
            Double nuevoStockDisponible = producto.getStock() - detalleVenta.cantidad();
            producto.setStock(nuevoStockDisponible);
            productoRepository.save(producto);
        });

        repository.save(venta);
    }

    @Override
    public List<RespuestaVentaDTO> mostrarVentas() {
        //Obteniendo todas las ventas existentes
        List<Venta> ventas = repository.findAll()
                .stream().filter(venta -> venta.getEstado().equals(true)).toList();

        //transformando cada venta en el tipo de la respuestaDTO
        List<RespuestaVentaDTO> respuestas = ventas.stream().map(venta -> {

            List<DatosRespuestaDetalleVentaDTO> detalleVentaDTO = venta.getDetalles()
                    .stream().map(detalleVenta -> new DatosRespuestaDetalleVentaDTO(detalleVenta.getProducto().getProductoId(),
                            detalleVenta.getProducto().getNombreProducto(), detalleVenta.getProducto().getMarca(),
                            detalleVenta.getCantidad(), detalleVenta.getProducto().getCosto()))
                    .toList();

            return new RespuestaVentaDTO(venta.getVentaId(), venta.getFechaVenta(),
                    venta.getTotal(), detalleVentaDTO, new RespuestaClienteDTO(venta.getCliente()));
        }).toList();

        return respuestas;
    }

    @Override
    public RespuestaVentaDTO mostrarVentaPorId(Long id) {
        if (repository.existsById(id) && repository.findById(id).orElseGet(null).getEstado().equals(true)) {
            return this.mostrarVentas()
                    .stream().filter(respuestaVentaDTO -> respuestaVentaDTO.ventaId().equals(id))
                    .findFirst().orElseGet(null);
        } else {
            throw new ValidacionException("La venta con el id proporcionado no existe");
        }
    }

    @Transactional
    @Override
    public RespuestaVentaDTO editarPorId(Long id, DatosEditarVentaDTO datosEditarVentaDTO) {
        if (repository.existsById(id) &&
                repository.findById(id).orElseGet(null).getEstado().equals(true)) {

            Venta venta = repository.getReferenceById(id);

            if (datosEditarVentaDTO.clienteId() != null) {
                if (clienteRepository.existsById(datosEditarVentaDTO.clienteId()) &&
                        clienteRepository.findById(datosEditarVentaDTO.clienteId())
                                .orElseGet(null).getEstado().equals(true)) {
                    Cliente cliente = clienteRepository.getReferenceById(datosEditarVentaDTO.clienteId());
                    venta.setCliente(cliente);
                } else {
                    throw new ValidacionException("El id del cliente proporcionado no existe");
                }
            }

            if (datosEditarVentaDTO.detalles() != null) {
                //Validando que el id de los productos proporcionados existan en la BD y es válido
                datosEditarVentaDTO.detalles().forEach(datosDetalleVenta -> {
                    if (!productoRepository.existsById(datosDetalleVenta.productoId()) ||
                            productoRepository.findById(datosDetalleVenta.productoId())
                                    .orElseGet(null).getEstado().equals(false)) {
                        throw new ValidacionException("El id " + datosDetalleVenta.productoId() +
                                " del producto proporcionado no existe");
                    }
                });

                //Validando haya stock suficiente para realizar el cambio
                this.verificarStockParaEdicion(datosEditarVentaDTO, venta);

                venta.getDetalles().clear();
                repository.save(venta);
                detalleVentaRepository.deleteAllByVenta(venta);

                List<DetalleVenta> detalles = new ArrayList<>(datosEditarVentaDTO.detalles().stream().map(datosEditarVenta -> {
                    DetalleVenta detalleVenta = new DetalleVenta();
                    detalleVenta.setVenta(venta);
                    detalleVenta.setProducto(productoRepository
                            .findById(datosEditarVenta.productoId()).orElseGet(null));
                    detalleVenta.setCantidad(datosEditarVenta.cantidad());
                    return detalleVenta;
                }).toList());

                venta.setDetalles(detalles);

                venta.setTotal(detalles.stream()
                        .map(detalleVenta -> detalleVenta.getCantidad() * detalleVenta.getProducto().getCosto())
                        .reduce(0.0, Double::sum));

            }
            repository.save(venta);

            return this.mostrarVentaPorId(venta.getVentaId());
        } else {
            throw new ValidacionException("La venta con el id que deseas editar no existe");
        }
    }

    @Override
    public void eliminarPorId(Long id) {
        if (repository.existsById(id) && repository.getReferenceById(id).getEstado().equals(true)) {
            Venta venta = repository.getReferenceById(id);
            venta.setEstado(false);
            repository.save(venta);
        } else {
            throw new ValidacionException("La venta con el id que deas eliminar no existe");
        }
    }

    @Override
    public List<DatosRespuestaDetalleVentaDTO> mostrarProductosVentaPorId(Long id) {
        return this.mostrarVentaPorId(id).detalle();
    }

    @Override
    public RespuestaVentaDiaDTO ventaDia(LocalDate fecha) {
        List<Venta> ventas = repository.findAll()
                .stream().filter(venta -> venta.getEstado().equals(true))
                .filter(venta -> venta.getFechaVenta().equals(fecha)).toList();

        if (!ventas.isEmpty()) {
            Double total = ventas.stream().map(Venta::getTotal).reduce(0.0, Double::sum);
            Double cantidad = ventas.stream().flatMap(venta -> venta.getDetalles()
                    .stream()).mapToDouble(DetalleVenta::getCantidad).reduce(0.0, Double::sum);

            return new RespuestaVentaDiaDTO(fecha, total, cantidad);
        } else {
            throw new ValidacionException("No hay ventas registradas en el dia proporcionado");
        }
    }

    @Override
    public RespuestaVentaMayor mostrarVentaMayor() {

        if (!repository.findAll().isEmpty()) {
            Venta ventaMayor = repository.findAll().stream()
                    .max(Comparator.comparing(Venta::getTotal)).orElseThrow();
            Double cantidad = ventaMayor.getDetalles()
                    .stream().map(DetalleVenta::getCantidad).reduce(0.0, Double::sum);
            return new RespuestaVentaMayor(ventaMayor.getVentaId(), ventaMayor.getTotal(), cantidad,
                    ventaMayor.getCliente().getNombreCliente(), ventaMayor.getCliente().getApellido());
        } else {
            throw new ValidacionException("Aun no existen ventas registradas");
        }

    }

    private void verificarStockParaEdicion(DatosEditarVentaDTO datosEditarVentaDTO, Venta venta) {
        List<Producto> productos = productoRepository.findAll();
        //Simulando el stock completo contando la devolucion del stock de la venta
        venta.getDetalles().forEach(detalleVenta -> {
            productos.forEach(producto -> {
                if (detalleVenta.getProducto().getProductoId().equals(producto.getProductoId())) {
                    producto.setStock(producto.getStock() + detalleVenta.getCantidad());
                }
            });
        });

        //Verificando que haya stock suficiente para considerar la edicion
        datosEditarVentaDTO.detalles().forEach(detalleVenta -> {
            productos.forEach(producto -> {
                if (producto.getProductoId().equals(detalleVenta.productoId())) {
                    if (producto.getStock() < detalleVenta.cantidad()) {
                        throw new ValidacionException("El stock del producto que deseas editar con id " +
                                detalleVenta.productoId() + " excede las existencias(" + producto.getStock() + ")");
                    }
                }
            });
        });

        productos.forEach(producto -> {
            datosEditarVentaDTO.detalles().forEach(detalleVenta -> {
                if (producto.getProductoId().equals(detalleVenta.productoId())) {
                    producto.setStock(producto.getStock() - detalleVenta.cantidad());
                }
            });
        });

    }
}
