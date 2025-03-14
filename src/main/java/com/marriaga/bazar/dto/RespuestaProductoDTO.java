package com.marriaga.bazar.dto;

import com.marriaga.bazar.model.Producto;

public record RespuestaProductoDTO(
        Long idProducto,
        String nombreProducto,
        String marca,
        Double costo,
        Double stock) {

    public RespuestaProductoDTO(Producto producto) {
        this(producto.getProductoId(), producto.getNombreProducto(),
                producto.getMarca(), producto.getCosto(), producto.getStock());
    }
}
