package com.sistema.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.exceptions.DepartamentoJaCadastradoException;
import com.sistema.exceptions.ValidacaoException;
import com.sistema.models.Departamento;
import com.sistema.repositories.DepartamentoRepository;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoRepository repository;
    
    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        var mv = new ModelAndView("/departamento/cadastro"); // template
        mv.addObject("depart_form_controller", new Departamento());
        return mv;
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid @ModelAttribute("depart_form_controller")
        Departamento departamento, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {
            return "departamento/cadastro"; // template
        }
        
        try {
            validaDepartamentoUnico(departamento);
            repository.save(departamento);
            attr.addFlashAttribute("success", "Registro inserido com sucesso.");
            return "redirect:/departamentos/listar"; // rota
        } catch(ValidacaoException e) {
            result.addError(e.getFieldError());
            return "departamento/cadastro"; // template
        }
    }

    @GetMapping("/listar")
    public ModelAndView listar() {
        var mv = new ModelAndView("departamento/lista"); // template
        mv.addObject("departamentos", repository.findAll());
        return mv;
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        repository.deleteById(id);
        attr.addFlashAttribute("success", "Registro excluído com sucesso.");
        return "redirect:/departamentos/listar"; // rota
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        var mv = new ModelAndView("/departamento/cadastro"); // template
        mv.addObject("depart_form_controller", repository.findById(id));
        return mv;
    }

    /*
        o hibernate e a jpa diferem inserir de atualizar 
        no método save através do id que foi passado
        não pode ter input type hidden no form
    */
    @PostMapping("/{id}/editar")
    public String editar(@Valid @ModelAttribute("depart_form_controller")
    Departamento departamento, BindingResult result, RedirectAttributes attr) 
    throws IOException {

        if (result.hasErrors()) {
			return "cargo/cadastro"; // template
		}

        try {
            validaDepartamentoUnico(departamento);
            repository.save(departamento);
            attr.addFlashAttribute("success", "Registro atualizado com sucesso.");
            return "redirect:/departamentos/listar"; // rota
        } catch(ValidacaoException e) {
            result.addError(e.getFieldError());
            return "departamento/cadastro"; // template
        }
    }

    private void validaDepartamentoUnico(Departamento departamento) {
        if (repository.isDepartamentoCadastrado(departamento)) {
            var mensagem = "Este departamento já está cadastrado.";
            var fieldError = new FieldError(departamento.getClass().getName(),
                "nome", departamento.getNome(), false, null, null, mensagem);
            
            throw new DepartamentoJaCadastradoException(mensagem, fieldError);
        }
    }
}
