package com.marriaga.bazar.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "Venta")
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ventaId;
    private LocalDate fechaVenta;
    private Double total;
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private Boolean estado;

    public Venta() {
    }

    public Venta(Long ventaId, LocalDate fechaVenta, Double total, List<DetalleVenta> detalles, Cliente cliente, Boolean estado) {
        this.ventaId = ventaId;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.detalles = detalles;
        this.cliente = cliente;
        this.estado = estado;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "ventaId=" + ventaId +
                ", fechaVenta=" + fechaVenta +
                ", total=" + total +
                ", detalles=" + detalles +
                ", cliente=" + cliente +
                ", estado=" + estado +
                '}';
    }
}
