package com.sistema.services.interfaces;

import java.util.List;

import com.sistema.models.Funcionario;

public interface IFuncionarioService {
    
    void salvar(Funcionario funcionario);

    void excluir(Long id);

    Funcionario buscarPorId(Long id);

    List<Funcionario> listarTodos();
}
