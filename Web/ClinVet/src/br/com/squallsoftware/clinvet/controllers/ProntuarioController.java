package br.com.squallsoftware.clinvet.controllers;

import java.util.ArrayList;
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
import br.com.squallsoftware.clinvet.dominios.Prontuario;
import br.com.squallsoftware.clinvet.dominios.Veterinario;
import br.com.squallsoftware.clinvet.repositorios.RepositorioAnimal;
import br.com.squallsoftware.clinvet.repositorios.RepositorioProntuario;
import br.com.squallsoftware.clinvet.repositorios.RepositorioVeterinario;

@Controller
@RequestMapping("/atendimentos")
public class ProntuarioController {

	@Autowired
	private RepositorioProntuario repositorio;
	@Autowired
	private RepositorioVeterinario rep_veterinario;
	@Autowired
	private RepositorioAnimal rep_animal;
	
	@RequestMapping(value="/adicionar", method=RequestMethod.GET)
	public String adicionar(Model model){		
		model.addAttribute("prontuario",new Prontuario());
		model.addAttribute("veterinarios",rep_veterinario.findAll());
		model.addAttribute("animais",rep_animal.findAll());		
		return "atendimento.adicionar.tiles";
	}
	@RequestMapping(value="/adicionar", method=RequestMethod.POST)
	public String adicionar(@ModelAttribute("prontuario") @Valid Prontuario prontuario, BindingResult result){
		if(result.hasErrors())
			return "atendimento.adicionar.tiles";
		
		repositorio.save(prontuario);
		return "redirect: listar";
	}
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String listar(Model model){
		List<Prontuario> prontuarios = repositorio.findAll();
		model.addAttribute("prontuarios",prontuarios);
		return "atendimento.listar.tiles";
	}
	
	@RequestMapping(value="/alterar/{id}", method=RequestMethod.GET)
	public String alterar(@PathVariable("id") Long id, Model model){
		Prontuario prontuario = repositorio.findOne(id);
		
		List<Animal> animal = new ArrayList<>();
		List<Veterinario> vet = new ArrayList<>();
		animal.add(prontuario.getAnimal());
		vet.add(prontuario.getVeterinario());
		
		model.addAttribute(prontuario);
		model.addAttribute("animais", animal);
		model.addAttribute("veterinarios", vet);
		return "atendimento.alterar.tiles";
	}
	@RequestMapping(value="/alterar", method=RequestMethod.POST)
	public String alterar(@ModelAttribute("prontuario") @Valid Prontuario prontuario, BindingResult result){
		if(result.hasErrors())
			return "atendimento.alterar.tiles";
		
		repositorio.save(prontuario);
		
		return "redirect: listar";
	}
	
	@RequestMapping(value="/excluir/{id}", method=RequestMethod.GET)
	public String excluir(@PathVariable("id") Long id){
		repositorio.delete(id);		
		return "redirect:/atendimentos/listar";
	}
	@RequestMapping(value="/porNome", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Prontuario> pesquisarPorNome(@RequestParam(name="observacao", defaultValue="") String observacao){
		observacao = org.apache.commons.lang3.StringEscapeUtils.unescapeJava(observacao);
		List<Prontuario> lst = repositorio.findByObservacao(observacao);
		//Prontuario p = (Prontuario) lst.iterator().next();
		//System.out.println(p.getAnimal().getNome());
		return lst;
	}
}
