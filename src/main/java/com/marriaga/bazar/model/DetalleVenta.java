package com.marriaga.bazar.model;

import jakarta.persistence.*;

@Entity(name = "Detalle")
@Table(name = "detalles")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long detalleId;
    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    private Double cantidad;

    public DetalleVenta() {
    }

    public DetalleVenta(Long detalleId, Venta venta, Producto producto, Double cantidad) {
        this.detalleId = detalleId;
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Long getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(Long detalleId) {
        this.detalleId = detalleId;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
}
