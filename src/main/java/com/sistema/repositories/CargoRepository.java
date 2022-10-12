package com.sistema.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.models.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

    Optional<Cargo> findByNome(String nome);

    default Boolean isCargoCadastrado(Cargo cargo) {
        if (cargo.getNome() == null) {
            return false;
        }

        return findByNome(cargo.getNome())
            .map(cargoEncontrado -> !cargoEncontrado.getId().equals(cargo.getId()))
            .orElse(false);
    }
}