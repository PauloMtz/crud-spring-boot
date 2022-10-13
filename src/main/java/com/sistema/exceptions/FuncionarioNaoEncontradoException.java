package com.sistema.exceptions;

import javax.persistence.EntityNotFoundException;

public class FuncionarioNaoEncontradoException extends EntityNotFoundException {

    public FuncionarioNaoEncontradoException(String message) {
        super(message);
    }
    
}
