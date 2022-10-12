package com.sistema.services.interfaces;

import org.springframework.data.domain.Page;

import com.sistema.models.Cargo;

public interface ICargoService {
    
    void salvar(Cargo cargo);
    void excluir(Long id);
    Cargo buscarPorId(Long id);
    Page<Cargo> listarTodos(int pagNum);
}
