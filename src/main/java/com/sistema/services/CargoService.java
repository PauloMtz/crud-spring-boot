package com.sistema.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.exceptions.CargoNaoEncontradoException;
import com.sistema.models.Cargo;
import com.sistema.repositories.CargoRepository;
import com.sistema.services.interfaces.ICargoService;

@Service
public class CargoService implements ICargoService {

    @Autowired
    private CargoRepository repository;

    @Override
    public void salvar(Cargo cargo) {
        repository.save(cargo);
    }

    @Override
    public void excluir(Long id) {
        var cargoEncontrado = buscarPorId(id);
        repository.delete(cargoEncontrado);
    }

    @Override
    public Cargo buscarPorId(Long id) {
        var cargoEncontrado = repository.findById(id);

        if (cargoEncontrado.isPresent()) {
            return cargoEncontrado.get();
        }

        throw new CargoNaoEncontradoException("Cargo não encontrado.");
    }

    @Override
    public List<Cargo> listarTodos() {
        return repository.findAll();
    }
    
}
