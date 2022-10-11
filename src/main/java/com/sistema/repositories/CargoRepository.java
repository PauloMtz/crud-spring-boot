package com.sistema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.models.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
}