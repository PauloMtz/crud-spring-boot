package com.sistema.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.exceptions.ValidacaoException;
import com.sistema.models.Cargo;
import com.sistema.models.Departamento;
import com.sistema.repositories.DepartamentoRepository;
import com.sistema.services.CargoService;

@Controller
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private DepartamentoRepository repository;

    @Autowired
    private CargoService service;
    
    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        var mv = new ModelAndView("/cargo/cadastro"); // template
        mv.addObject("cargo_controller_form", new Cargo());
        return mv;
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid @ModelAttribute("cargo_controller_form") Cargo cargo, 
        BindingResult result, RedirectAttributes attr) throws IOException {

        if (result.hasErrors()) {
			return "cargo/cadastro"; // template
		}

        try {
            service.salvar(cargo);
            attr.addFlashAttribute("success", "Registro inserido com sucesso.");
            return "redirect:/cargos/listar"; // rota
        } catch(ValidacaoException e) {
            result.addError(e.getFieldError());
            return "cargo/cadastro"; // template
        }
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        return listaPaginada(model, 1);
    }

    @GetMapping("/listar/{pageNumber}")
    public String listaPaginada(Model model,
        @PathVariable(value = "pageNumber") int currentPage) {

        Page<Cargo> page = service.listarTodos(currentPage);
        List<Cargo> cargos = page.getContent();
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("cargos", cargos);

        return "/cargo/lista"; // template
    }

    // popula campo select no formulário
    @ModelAttribute("departamentosSelect")
    public List<Departamento> listaDepartamentos() {
        return repository.findAll();
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        service.excluir(id);
        attr.addFlashAttribute("success", "Registro excluído com sucesso.");
        return "redirect:/cargos/listar"; // rota
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        var mv = new ModelAndView("/cargo/cadastro"); // template
        mv.addObject("cargo_controller_form", service.buscarPorId(id));
        return mv;
    }

    /*
        o hibernate e a jpa diferem inserir de atualizar 
        no método save através do id que foi passado
        não pode ter input type hidden no form
    */
    @PostMapping("/{id}/editar")
    public String editar(@Valid @ModelAttribute("cargo_controller_form") Cargo cargo, 
        BindingResult result, RedirectAttributes attr) throws IOException {

        if (result.hasErrors()) {
			return "cargo/cadastro"; // template
		}

        try {
            service.salvar(cargo);
            attr.addFlashAttribute("success", "Registro inserido com sucesso.");
            return "redirect:/cargos/listar"; // rota
        } catch(ValidacaoException e) {
            result.addError(e.getFieldError());
            return "cargo/cadastro"; // template
        }
    }
}
