package br.com.squallsoftware.clinvet.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.squallsoftware.clinvet.dominios.Usuario;
import br.com.squallsoftware.clinvet.repositorios.RepositorioUsuario;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private RepositorioUsuario repositorio;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){ 
		Usuario admin = repositorio.findByRole("ROLE_ADMIN");		
		if(admin == null){			
			return "redirect:admin";			
		}
		return "home.login.tiles"; 
		
	}
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String adicionar(Model model){ 
		Usuario admin = repositorio.findByRole("ROLE_ADMIN");		
		if(admin != null){			
			return "redirect:login";// se existir um admin, redirect para pagina de login			
		}
		model.addAttribute("usuario",new Usuario());
		return "home.addAdmin.tiles"; 
		
	}
	@RequestMapping(value="/admin", method = RequestMethod.POST)
	public String adicionar(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult result){
		System.out.println("/admin-POST");
		if(result.hasErrors())
			return "home.login.tiles";
		
		usuario.setRole("ROLE_ADMIN");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		
		repositorio.save(usuario);
		
		return "redirect:login";
	}

	@RequestMapping(value = "/bemvindo", method = RequestMethod.GET)
	public String bemvindo() { return "home.bemvindo.tiles"; }
}
