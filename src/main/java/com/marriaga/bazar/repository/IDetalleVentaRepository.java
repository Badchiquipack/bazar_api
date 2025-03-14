package com.marriaga.bazar.repository;

import com.marriaga.bazar.model.DetalleVenta;
import com.marriaga.bazar.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    void deleteAllByVenta(Venta venta);

}
