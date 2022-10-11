package com.sistema.services.interfaces;

import java.util.List;

import com.sistema.models.Cargo;

public interface ICargoService {
    
    void salvar(Cargo cargo);
    void excluir(Long id);
    Cargo buscarPorId(Long id);
    List<Cargo> listarTodos();
}
