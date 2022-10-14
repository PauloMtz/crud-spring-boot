package com.sistema.services.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.sistema.models.Funcionario;

public interface IFuncionarioService {
    
    void salvar(Funcionario funcionario);

    void excluir(Long id);

    Funcionario buscarPorId(Long id);

    List<Funcionario> listarTodos();

    List<Funcionario> buscarPorNome(String nome);

	List<Funcionario> buscarPorCargo(Long id);
	
	List<Funcionario> buscarPorDatas(LocalDate entrada, LocalDate saida);
}
