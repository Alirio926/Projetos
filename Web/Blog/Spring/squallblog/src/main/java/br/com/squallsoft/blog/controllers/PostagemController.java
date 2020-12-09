package br.com.squallsoft.blog.controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.squallsoft.blog.dominios.ListAllPosts;
import br.com.squallsoft.blog.dominios.Postagem;
import br.com.squallsoft.blog.repositorios.RepositorioPostagem;
import br.com.squallsoft.blog.service.PostagemService;

@Controller
@RequestMapping("/api")
public class PostagemController {

	@Autowired
	private PostagemService postagemService;
	
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/post/listar")
	public String listar(Model model) {
		model.addAttribute("postagens", postagemService.listAPI());
		return "jsonTemplate";
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = {"/post/{permalink}"}, method = RequestMethod.GET)
	public String PostByPermalink(@PathVariable("permalink")String permalink, Model model) {	
		model.addAttribute("postagem", postagemService.getPostByPermalink(permalink));
		
		return "jsonTemplate";
	}
	
	
	
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value = "/deletar/{id}")
	public void deletaPostagem(@PathVariable("id")Long id) {
		postagemService.delete(id);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(value="/newpost") 
	public void save(@ModelAttribute Postagem postagem) {
		postagemService.saveOrUpdate(postagem);
	}
	
	@GetMapping("/lst")
	public String listarPostagens(Model model) {
		List<Postagem> postagens = postagemService.findAll();
		
		model.addAttribute("postagens", postagens);
		return "jsonTemplate";
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/allpost")
	public String listarPostagem(Model model) {
		List<Postagem> postagens = postagemService.findAll();
		List<ListAllPosts> allpost = new ArrayList<>();
		ListAllPosts temp;
		List<String> categorias;
		
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for(int i=0;i< postagens.size(); i++) {
			temp = new ListAllPosts();
			temp.setId(postagens.get(i).getId());
			temp.setTitulo(postagens.get(i).getTitulo());
			temp.setTexto(postagens.get(i).getTexto());
			
			temp.setDataPostagem(postagens.get(i).getDataPostagem().format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMM 'de' yyyy 'as' HH:mm a")));
			
			temp.setPermalink(postagens.get(i).getPermalink());
			
			temp.setAutorNome(postagens.get(i).getAutor().getNome());
			temp.setAutorId(postagens.get(i).getAutor().getId());
			temp.setAutorBiografia(postagens.get(i).getAutor().getBiografia());
			
			temp.setPerfil(postagens.get(i).getAutor().getUsuario().getPerfil().toString());
			
			categorias = new ArrayList<>();
			for(int x=0; x < postagens.get(i).getCategorias().size();x++) {
				categorias.add(postagens.get(i).getCategorias().get(x).getDescricao()+" ");				
			}
			temp.setCategorias(categorias);
			
			allpost.add(temp);
		}
		model.addAttribute("postagens", allpost);
		return "jsonTemplate";
	}
	
	/*
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = {"/post/{permalink}"}, method = RequestMethod.GET)
	public String showPostByPermalink(@PathVariable("permalink")String permalink, Model model) {	
		Postagem postagens = postagemService.findByPermalink(permalink);
		ListAllPosts temp = new ListAllPosts();
		List<String> categorias;
		temp.setId(postagens.getId());
		temp.setTitulo(postagens.getTitulo());
		temp.setTexto(postagens.getTexto());
		
		temp.setDataPostagem(postagens.getDataPostagem().format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMM 'de' yyyy 'as' HH:mm a")));
		
		temp.setPermalink(postagens.getPermalink());
		
		temp.setAutorNome(postagens.getAutor().getNome());
		temp.setAutorId(postagens.getAutor().getId());
		temp.setAutorBiografia(postagens.getAutor().getBiografia());
		temp.setPerfil(postagens.getAutor().getUsuario().getPerfil().toString());
		
		categorias = new ArrayList<>();
		for(int x=0; x < postagens.getCategorias().size();x++) {
			categorias.add(postagens.getCategorias().get(x).getDescricao()+" ");				
		}
		temp.setCategorias(categorias);
		
		model.addAttribute("postagens", temp);
		
		return "jsonTemplate";
	}*/
	/*
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = {"/listar/{type}","/listar"}, method = RequestMethod.GET)
	public String postagens(@PathVariable Map<String, String> pathVariablesMap, HttpServletRequest req, Model model) {
		PagedListHolder<ListAllPosts> listPost = null;
		
		String type = pathVariablesMap.get("type");
				
		if(null == type) {
			listPost = new PagedListHolder<ListAllPosts>();
			listPost.setSource(postagemService.listarPostagem());
			listPost.setPageSize(2);
			
			req.getSession().setAttribute("listPost",  listPost);
		}else if("next".equals(type)) {
			listPost = (PagedListHolder<ListAllPosts>) req.getSession().getAttribute("listPost");
			listPost.nextPage();
		}else if("prev".equals(type)) {
			listPost = (PagedListHolder<ListAllPosts>) req.getSession().getAttribute("listPost");
			listPost.previousPage();
		}else {
			System.out.println("type:" + type);
			listPost = (PagedListHolder<ListAllPosts>) req.getSession().getAttribute("listPost");	
            int pageNum = Integer.parseInt(type);	            
            listPost.setPage(pageNum);
		}
		
		model.addAttribute("postagens", listPost);
		return "jsonTemplate";
	}*/
}
// 10:11