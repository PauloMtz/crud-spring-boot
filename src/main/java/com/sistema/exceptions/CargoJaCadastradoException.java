package com.sistema.exceptions;

import org.springframework.validation.FieldError;

public class CargoJaCadastradoException extends ValidacaoException {

    public CargoJaCadastradoException(String message, FieldError fieldError) {
        super(message, fieldError);
    }
    
}
