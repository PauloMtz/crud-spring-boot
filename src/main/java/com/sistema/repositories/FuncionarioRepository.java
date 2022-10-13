package com.sistema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.models.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
