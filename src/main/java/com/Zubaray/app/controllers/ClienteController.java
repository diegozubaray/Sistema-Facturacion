package com.Zubaray.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Zubaray.app.models.Cliente;
import com.Zubaray.app.paginator.PageRender;
import com.Zubaray.app.service.IClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping(value = "/listar")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes",clientes) ;
		model.addAttribute("page", pageRender);
		return "listar";
	}
	
	
	
	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de CLiente");
		
		return"form";
	}
	
	
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		if(id>0) {
			cliente= clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("danger", " El ID del cliente no existe en la BBDD");
				return"redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("danger", " El ID del cliente no puede ser cero");
			return"redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar CLiente");
		return "form";
	}

 	
	@PostMapping(value = "/form")
	public String guardar(@Valid Cliente cliente,BindingResult result, Model model,RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de CLiente");
			return "form";
		}
		
		String mensajeFlash = (cliente.getId() != null)? "Cliente editado con éxito" : "Cliente Creado con éxito";
		
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/listar";
	}
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id ,RedirectAttributes flash) {		
		if(id>0) {
			clienteService.delete(id);
			flash.addFlashAttribute("danger", "Cliente eliminado con éxito");
		}		
		return "redirect:/listar";
	}
}










