package br.com.squallsoftware.clinvet.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.squallsoftware.clinvet.dominios.Usuario;
import br.com.squallsoftware.clinvet.dominios.Veterinario;
import br.com.squallsoftware.clinvet.repositorios.RepositorioUsuario;
import br.com.squallsoftware.clinvet.repositorios.RepositorioVeterinario;

@Controller
@RequestMapping("/veterinarios")
public class VeterinarioController {

	@Autowired
	private RepositorioVeterinario repositorio;
	@Autowired
	private RepositorioUsuario rep_user;
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String listar(Model model){
		List<Veterinario> veterinarios = repositorio.findAll();
		model.addAttribute("veterinario", veterinarios);
		return "veterinario.listar.tiles";
	}
	
	@RequestMapping(value="/adicionar", method=RequestMethod.GET)
	public String adicionar(Model model){
		model.addAttribute("veterinario",new Veterinario());
		return "veterinario.adicionar.tiles";
	}
	
	@RequestMapping(value="/adicionar", method=RequestMethod.POST)
	public String adicionar(@ModelAttribute("veterinario") @Valid Veterinario veterinario, BindingResult result){
		if(result.hasErrors())
			return "veterinario.adicionar.tiles";
		
		Usuario user = new Usuario();
		user.setUsername(veterinario.getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(veterinario.getPassword()));
		user.setRole(veterinario.getRole());
		
		try{
			rep_user.save(user);
			veterinario.setIdUser(user.getId());
			repositorio.save(veterinario);
		}catch(Exception e){
			repositorio.delete(veterinario);
			rep_user.delete(user);
		}
		
		return "redirect:/veterinarios/listar";
	}
	@RequestMapping(value="/porNome", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Veterinario> pesquisarPorNome(@RequestParam(name="nome_vet", defaultValue="") String nomeVet){
		System.out.println("porNome");
		nomeVet = org.apache.commons.lang3.StringEscapeUtils.unescapeJava(nomeVet);
		return repositorio.findByNome(nomeVet);
	}
}
