package com.sistema.exceptions;

import javax.persistence.EntityNotFoundException;

public class CargoNaoEncontradoException extends EntityNotFoundException {

    public CargoNaoEncontradoException(String message) {
        super(message);
    }
    
}
