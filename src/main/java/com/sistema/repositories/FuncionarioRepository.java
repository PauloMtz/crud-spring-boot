package com.sistema.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sistema.models.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query("FROM Funcionario f WHERE UPPER(f.nome) LIKE CONCAT('%', UPPER(:nome), '%')")
    List<Funcionario> findByNome(@Param("nome") String nome);

    List<Funcionario> findByCargoId(Long id);

    List<Funcionario> findByDataEntrada(LocalDate entrada);

    List<Funcionario> findByDataSaida(LocalDate saida);

    @Query(
        """
            SELECT f.dataEntrada, f.dataSaida FROM Funcionario f 
                WHERE f.dataEntrada >= :entrada
                AND f.dataSaida <= :saida
                ORDER BY f.dataEntrada ASC
        """
    )
    List<Funcionario> findByDataEntradaDataSaida(LocalDate entrada, LocalDate saida);
}
