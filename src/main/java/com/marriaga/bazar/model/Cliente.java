package com.marriaga.bazar.model;

import jakarta.persistence.*;

@Entity(name = "Cliente")
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long clienteId;
    private String nombreCliente;
    private String apellido;
    private String dni;
    private Boolean estado;

    public Cliente() {
    }

    public Cliente(Long clienteId, String nombreCliente, String apellido, String dni, Boolean estado) {
        this.clienteId = clienteId;
        this.nombreCliente = nombreCliente;
        this.apellido = apellido;
        this.dni = dni;
        this.estado = true;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
