package br.com.squallsoft.blog.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.squallsoft.blog.dominios.Avatar;
import br.com.squallsoft.blog.dominios.Perfil;
import br.com.squallsoft.blog.dominios.PerfilEditorSupport;
import br.com.squallsoft.blog.dominios.Usuario;
import br.com.squallsoft.blog.service.AvatarService;
import br.com.squallsoft.blog.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AvatarService avatarService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Perfil.class, new PerfilEditorSupport());
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/listar")
	public String listarUsuarios(Model model) {
		model.addAttribute("usuarios", usuarioService.findAll());
		return "jsonTemplate";
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(value="/cadastrar") 
	public String cadastraUsuario(@ModelAttribute @Valid Usuario usuario, @RequestParam(value = "file", required = true)MultipartFile file, Model model) {
		
		Avatar avatar = avatarService.getAvatarByUpload(file);
		
		usuario.setAvatar(avatar);
		
		usuarioService.save(usuario);
		
		model.addAttribute("usuarios", usuarioService.findAll());

		System.out.println(String.format("Nome: %s, Email: %s, Senha: %s, Perfil: %s, File name: %s, Size: %d\n", 
			usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getPerfil().toString(), 
			usuario.getAvatar().getTitulo(), usuario.getAvatar().getAvatar().length));		
		
		return "jsonTemplate";
	} 
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value="/deletar/{id}")
	public void deletaUsuario(@PathVariable("id")Long id) {
		usuarioService.delete(id);
	}
}
