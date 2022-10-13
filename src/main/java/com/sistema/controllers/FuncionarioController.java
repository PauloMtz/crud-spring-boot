package com.sistema.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.models.Cargo;
import com.sistema.models.Funcionario;
import com.sistema.models.UF;
import com.sistema.repositories.CargoRepository;
import com.sistema.services.FuncionarioService;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @Autowired
    private CargoRepository cargoRepository;
    
    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        var mv = new ModelAndView("/funcionario/cadastro"); // template
        mv.addObject("func_controller_form", new Funcionario());
        return mv;
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid @ModelAttribute("func_controller_form") 
        Funcionario funcionario, BindingResult result, RedirectAttributes attr) 
        throws IOException {

        if (result.hasErrors()) {
			return "funcionario/cadastro"; // template
		}

        service.salvar(funcionario);
        attr.addFlashAttribute("success", "Registro inserido com sucesso.");
        return "redirect:/funcionarios/listar"; // rota
    }

    @GetMapping("/listar")
    public ModelAndView listar() {
        var mv = new ModelAndView("funcionario/lista"); // template
        mv.addObject("funcionarios", service.listarTodos());
        return mv;
    }

    // popula campo select no formulário
    @ModelAttribute("cargoSelect")
    public List<Cargo> listaCargos() {
        return cargoRepository.findAll();
    }

    @ModelAttribute("listaUFs")
	public UF[] listaUFs() {
		return UF.values();
	}

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        var mv = new ModelAndView("/funcionario/cadastro"); // template
        mv.addObject("func_controller_form", service.buscarPorId(id));
        return mv;
    }

    /*
        o hibernate e a jpa diferem inserir de atualizar 
        no método save através do id que foi passado
        não pode ter input type hidden no form
    */
    @PostMapping("/{id}/editar")
    public String editar(@Valid @ModelAttribute("func_controller_form") Funcionario funcionario, 
        BindingResult result, RedirectAttributes attr) throws IOException {

        if (result.hasErrors()) {
			return "funcionario/cadastro"; // template
		}

        service.salvar(funcionario);
        attr.addFlashAttribute("success", "Registro atualizado com sucesso.");
        return "redirect:/funcionarios/listar"; // rota
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {
        service.excluir(id);
        attr.addFlashAttribute("success", "Registro excluído com sucesso.");
        return "redirect:/funcionarios/listar"; // rota
    }
}
