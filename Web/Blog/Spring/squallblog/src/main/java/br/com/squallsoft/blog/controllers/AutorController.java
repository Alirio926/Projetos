package br.com.squallsoft.blog.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.squallsoft.blog.dominios.Autor;
import br.com.squallsoft.blog.service.AutorService;

@Controller
@RequestMapping("autor")
public class AutorController {

	private static final Logger LOG = Logger.getLogger(AutorController.class);
	@Autowired
	private AutorService autorService;
	
	@GetMapping
	public String getAutor(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autor", autorService.findById(id));		
		return "jsonTemplate";
	}
	
	@GetMapping("/listar")
	public String listarAutores(Model model) {
		model.addAttribute("autor", autorService.findAll());		
		return "jsonTemplate";
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(value="/cadastrar") 
	public String cadastrarAutor(@ModelAttribute("autor") Autor autor, Model model) {
		Autor ret =  autorService.save(autor);	
		model.addAttribute("autor", ret);		
		return "jsonTemplate";
	}
}
