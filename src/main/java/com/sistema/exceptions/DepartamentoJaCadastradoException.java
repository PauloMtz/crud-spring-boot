package com.sistema.exceptions;

import org.springframework.validation.FieldError;

public class DepartamentoJaCadastradoException extends ValidacaoException {

    public DepartamentoJaCadastradoException(String message, FieldError fieldError) {
        super(message, fieldError);
    }
    
}
