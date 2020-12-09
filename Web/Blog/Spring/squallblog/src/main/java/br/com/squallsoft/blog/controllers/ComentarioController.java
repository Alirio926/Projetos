package br.com.squallsoft.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.squallsoft.blog.dominios.Comentario;
import br.com.squallsoft.blog.dominios.Postagem;
import br.com.squallsoft.blog.service.ComentarioService;
import br.com.squallsoft.blog.service.PostagemService;

@Controller
@RequestMapping("/api")
public class ComentarioController {

	@Autowired
	ComentarioService comentarioService;
	@Autowired
	PostagemService postagemService;
	
	@RequestMapping(value="/comentario")
	public void save(@ModelAttribute("comentario")Comentario comentario,
					   @RequestParam("permalink")String permalink) {
		
		Postagem postagem = postagemService.findByPermalink(permalink);
		comentario.setPostagem(postagem);
		
		comentarioService.save(comentario);
	}
}
