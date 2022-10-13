package com.sistema.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Funcionario> listarTodos() {
        return repository.findAll();
    }
}
