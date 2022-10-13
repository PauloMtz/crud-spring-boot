package com.sistema.validators;

import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sistema.models.Funcionario;

public class FuncionarioValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Funcionario.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Funcionario func = (Funcionario) target;
        LocalDate entrada = func.getDataEntrada();

        if (func.getDataSaida() != null) {
            if (func.getDataSaida().isBefore(entrada)) {
                errors.rejectValue("dataSaida", "PosteriorDataEntrada.funcionario.dataSaida");
            }
        }
    }
}
