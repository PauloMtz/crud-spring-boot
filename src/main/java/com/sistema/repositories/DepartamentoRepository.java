package com.sistema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.models.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}
