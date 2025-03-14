package com.marriaga.bazar.model;

import jakarta.persistence.*;

@Entity(name = "Producto")
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long productoId;
    private String nombreProducto;
    private String marca;
    private Double costo;
    private Double stock;
    private Boolean estado;

    public Producto() {
    }

    public Producto(Long productoId, String nombreProducto, String marca, Double costo, Double stock, Boolean estado) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.marca = marca;
        this.costo = costo;
        this.stock = stock;
        this.estado = estado;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
