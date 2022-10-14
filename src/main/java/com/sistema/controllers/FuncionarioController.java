package com.sistema.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.models.Cargo;
import com.sistema.models.Funcionario;
import com.sistema.models.UF;
import com.sistema.repositories.CargoRepository;
import com.sistema.services.FuncionarioService;
import com.sistema.validators.FuncionarioValidator;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @Autowired
    private CargoRepository cargoRepository;

    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new FuncionarioValidator());
	} // inicia o validator do spring mvc para validar data de saída
    
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
    public String listar(Model model) {
        return listaPaginada(model, 1);
    }

    @GetMapping("/listar/{pageNumber}")
    public String listaPaginada(Model model,
        @PathVariable(value = "pageNumber") int currentPage) {

        Page<Funcionario> page = service.listarTodos(currentPage);
        List<Funcionario> funcionarios = page.getContent();
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("funcionarios", funcionarios);

        return "/funcionario/lista"; // template
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

    @GetMapping("/buscar/nome")
	public String getPorNome(@RequestParam("nome") String nome, ModelMap model) {		
		model.addAttribute("funcionarios", service.buscarPorNome(nome));
		return "/funcionario/lista"; // template
	}

    @GetMapping("/buscar/cargo")
	public String getPorCargo(@RequestParam("id") Long id, ModelMap model) {
		model.addAttribute("funcionarios", service.buscarPorCargo(id));
		return "/funcionario/lista";
	}

    @GetMapping("/buscar/data")
    public String getPorDatas(@RequestParam(name = "entrada", required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entrada,
        @RequestParam(name = "saida", required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate saida, ModelMap model) {

        model.addAttribute("funcionarios", service.buscarPorDatas(entrada, saida));
        return "/funcionario/lista";
    }
}
