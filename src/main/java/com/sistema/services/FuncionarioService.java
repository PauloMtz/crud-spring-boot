package com.sistema.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sistema.exceptions.FuncionarioNaoEncontradoException;
import com.sistema.models.Funcionario;
import com.sistema.repositories.FuncionarioRepository;
import com.sistema.services.interfaces.IFuncionarioService;

@Service
public class FuncionarioService implements IFuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    @Override
    public void salvar(Funcionario funcionario) {
        repository.save(funcionario);
    }

    @Override
    public void excluir(Long id) {
        var funcionarioEncontrado = buscarPorId(id);
        repository.delete(funcionarioEncontrado);
    }

    @Override
    public Funcionario buscarPorId(Long id) {
        var funcionarioEncontrado = repository.findById(id);

        if (funcionarioEncontrado.isPresent()) {
            return funcionarioEncontrado.get();
        }

        throw new FuncionarioNaoEncontradoException("Funcionário não encontrado.");
    }

    @Override
    public Page<Funcionario> listarTodos(int numPage) {
        int size = 3;
        Pageable pageable = PageRequest.of(numPage -1, size);
        return repository.findAll(pageable);
    }

    @Override
    public List<Funcionario> buscarPorNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<Funcionario> buscarPorCargo(Long id) {
        return repository.findByCargoId(id);
    }

    @Override
    public List<Funcionario> buscarPorDatas(LocalDate entrada, LocalDate saida) {
        
        if (entrada != null && saida != null) {	    	
            return repository.findByDataEntradaDataSaida(entrada, saida);
        } else if (entrada != null) {        	
	        return repository.findByDataEntrada(entrada);
        } else if (saida != null) {        	
	        return repository.findByDataSaida(saida);
        } else {
        	return repository.findAll();
        }
    }
}
