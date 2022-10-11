package com.sistema.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.models.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    Optional<Departamento> findByNome(String nome);

    default Boolean isDepartamentoCadastrado(Departamento departamento) {
        if (departamento.getNome() == null) {
            return false;
        }

        return findByNome(departamento.getNome())
            .map(deptoEncontrado -> !deptoEncontrado.getId().equals(departamento.getId()))
            .orElse(false);
    }
}
