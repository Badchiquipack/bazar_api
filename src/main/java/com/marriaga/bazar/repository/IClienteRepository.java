package com.marriaga.bazar.repository;

import com.marriaga.bazar.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByDni(String dni);
}
