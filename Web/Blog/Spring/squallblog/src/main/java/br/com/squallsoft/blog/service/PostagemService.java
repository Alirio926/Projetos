package br.com.squallsoft.blog.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.squallsoft.blog.dominios.Comentario;
import br.com.squallsoft.blog.dominios.ListAllPosts;
import br.com.squallsoft.blog.dominios.Postagem;
import br.com.squallsoft.blog.dominios.api.Post;
import br.com.squallsoft.blog.repositorios.RepositorioComentario;
import br.com.squallsoft.blog.repositorios.RepositorioPostagem;
import br.com.squallsoft.blog.util.MyReplaceString;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class PostagemService {

	@Autowired
	private RepositorioPostagem repositorio;
	@Autowired
	private RepositorioComentario repComentario;
	
	public List<Postagem> findAll(){
		
		return repositorio.findAll();
	}
	
	public Postagem findById(Long id){
		return repositorio.findById(id).get();
	}
	
	public Postagem findByPermalink(String permalink){
		return repositorio.findByPermalink(permalink);
	}	
	
	@Transactional(readOnly = false)
	public void saveOrUpdate(Postagem postagem) {
		if(postagem.getId() == null) {
			save(postagem);
		}else {
			update(postagem);
		}
	}

	private void update(Postagem postagem) {
		Postagem persistente = repositorio.findById(postagem.getId()).get();
		if(!persistente.getTitulo().equals(postagem.getTitulo())) {
			persistente.setTitulo(postagem.getTitulo());
		}
		if(!persistente.getTexto().equals(postagem.getTexto())) {
			persistente.setTexto(postagem.getTexto());
		}
		repositorio.save(persistente);
		
	}

	private void save(Postagem postagem) {
		String permalink = MyReplaceString.formatarPermalink(postagem.getTitulo());
		
		postagem.setPermalink(permalink);
		
		postagem.setDataPostagem(LocalDateTime.now());
		
		repositorio.save(postagem);		
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		repositorio.deleteById(id);
	}
	
	public List<ListAllPosts> listarPostagem() {
		List<Postagem> postagens = repositorio.findAll();
		List<ListAllPosts> allpost = new ArrayList<>();
		ListAllPosts temp;
		List<String> categorias;
		
		for(int i=0;i< postagens.size(); i++) {
			temp = new ListAllPosts();
			temp.setId(postagens.get(i).getId());
			temp.setTitulo(postagens.get(i).getTitulo());
			temp.setTexto(postagens.get(i).getTexto());
			temp.setDataPostagem(postagens.get(i).getDataPostagem().toString());
			temp.setPermalink(postagens.get(i).getPermalink());
			
			temp.setAutorNome(postagens.get(i).getAutor().getNome());
			temp.setAutorId(postagens.get(i).getAutor().getId());
			temp.setAutorBiografia(postagens.get(i).getAutor().getBiografia());
			
			categorias = new ArrayList<>();
			for(int x=0; x < postagens.get(i).getCategorias().size();x++) {
				categorias.add(postagens.get(i).getCategorias().get(x).getDescricao());				
			}
			temp.setCategorias(categorias);
			
			allpost.add(temp);
		}
		
		return allpost;
	}
	
	
	// API methods
	public Post getPostByPermalink(String permalink) {
		Post post = new Post();
		Postagem postagem = repositorio.findByPermalink(permalink);
		
		post = new Post();
		post.setId(postagem.getId());
		post.setTitulo(postagem.getTitulo());
		post.setTexto(postagem.getTexto());
		post.setDataPostagem(postagem.getDataPostagem().format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMM 'de' yyyy 'as' HH:mm a")));
		post.setPermalink(postagem.getPermalink());
		
		// Problema
		List<Comentario> com = repComentario.findByPostagemId(20L);
		List<Comentario> obj = repComentario.lerComments(20L);
		post.setComentario(postagem.getComentarios());
		
		post.setAutorId(postagem.getAutor().getId());
		post.setAutorNome(postagem.getAutor().getNome());
		post.setAutorBiografia(postagem.getAutor().getBiografia());
		
		post.setCategorias(postagem.getCategorias());
				
		return post;
	}
	public List<Post> listAPI() {
		List<Postagem> postagens = repositorio.findAll();
		List<Post> posts = new ArrayList<>();
		Post post;
		
		for(int i=0;i< postagens.size(); i++) {
			post = new Post();
			post.setId(postagens.get(i).getId());
			post.setTitulo(postagens.get(i).getTitulo());
			post.setTexto(postagens.get(i).getTexto());
			post.setDataPostagem(postagens.get(i).getDataPostagem().format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMM 'de' yyyy 'as' HH:mm a")));
			post.setPermalink(postagens.get(i).getPermalink());
			
			post.setAutorId(postagens.get(i).getAutor().getId());
			post.setAutorNome(postagens.get(i).getAutor().getNome());
			post.setAutorBiografia(postagens.get(i).getAutor().getBiografia());
			
			post.setCategorias(postagens.get(i).getCategorias());
			
			posts.add(post);
		}		
		return posts;
	}
}
