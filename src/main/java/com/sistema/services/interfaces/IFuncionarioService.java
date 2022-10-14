package com.sistema.services.interfaces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.sistema.models.Funcionario;

public interface IFuncionarioService {
    
    void salvar(Funcionario funcionario);

    void excluir(Long id);

    Funcionario buscarPorId(Long id);

    Page<Funcionario> listarTodos(int numPage);

    List<Funcionario> buscarPorNome(String nome);

	List<Funcionario> buscarPorCargo(Long id);
	
	List<Funcionario> buscarPorDatas(LocalDate entrada, LocalDate saida);
}
