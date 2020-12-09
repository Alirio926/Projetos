package br.com.squallsoftware.clinvet.controllers;

import java.text.DateFormat;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.squallsoftware.clinvet.dominios.Animal;
import br.com.squallsoftware.clinvet.repositorios.RepositorioAnimal;

@Controller
@RequestMapping("/animais")
public class AnimalController {

	@Autowired
	private RepositorioAnimal repositorio;
	
	@RequestMapping(value="/listar", method = RequestMethod.GET)
	public String listar(Model model){
		List<Animal> animais = repositorio.findAll();
		model.addAttribute("animais", animais);
		return "animal.listar.tiles";
	}
	
	@RequestMapping(value="/adicionar", method=RequestMethod.GET)
	public String adicionar(Model model){
		model.addAttribute("animal",new Animal());
		return "animal.adicionar.tiles";
	}
	
	@RequestMapping(value="/adicionar", method=RequestMethod.POST)
	public String adicionar(@ModelAttribute("animal") @Valid Animal animal, BindingResult result){
		if(result.hasErrors())
			return "animal.adicionar.tiles";
		
		repositorio.save(animal);
		return "redirect: listar";
	}
	
	@RequestMapping(value="/alterar/{id}", method = RequestMethod.GET)
	public String alterar(@PathVariable("id") Long id, Model model){
		Animal animal = repositorio.findOne(id);
		model.addAttribute("animal", animal);
		return "animal.alterar.tiles";
	}
	
	@RequestMapping(value="/alterar", method=RequestMethod.POST)
	public String alterar(@ModelAttribute("animal") @Valid Animal animal, BindingResult result){
		if(result.hasErrors())
			return "animal.alterar.tiles";
		
		repositorio.save(animal);
		return "redirect:/animais/listar";
	}
	@RequestMapping(value="/excluir/{id}", method=RequestMethod.GET)
	public String excluir(@PathVariable("id") Long id){		
		repositorio.delete(id);		
		return "redirect:/animais/listar";
	}
	
	@RequestMapping(value="/porNome", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Animal> pesquisarPorNome(@RequestParam(name="nome", defaultValue="") String nomeAnimal){
		System.out.println("porNome");
		nomeAnimal = org.apache.commons.lang3.StringEscapeUtils.unescapeJava(nomeAnimal);
		return repositorio.findByNome(nomeAnimal);
	}
}
