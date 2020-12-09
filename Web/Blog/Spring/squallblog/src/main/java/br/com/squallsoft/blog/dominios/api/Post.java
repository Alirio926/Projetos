package br.com.squallsoft.blog.dominios.api;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.squallsoft.blog.dominios.Categoria;
import br.com.squallsoft.blog.dominios.Comentario;

public class Post {

	private Long id;
	private String titulo;
	private String texto;
	private String permalink;
	private String dataPostagem;
	private Long autorId;
	private String autorNome;
	private String autorBiografia;
	@JsonIgnoreProperties({"postagens"})
	private List<Categoria> categorias;	
	@JsonIgnoreProperties({"postagem", "usuario"})
	private List<Comentario> Comentario;
		
	
	public List<Comentario> getComentario() {
		return Comentario;
	}
	public void setComentario(List<Comentario> comentario) {
		Comentario = comentario;
	}
	public Long getAutorId() {
		return autorId;
	}
	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}
	public List<Categoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	public String getPermalink() {
		return permalink;
	}
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getDataPostagem() {
		return dataPostagem;
	}
	public void setDataPostagem(String dataPostagem) {
		this.dataPostagem = dataPostagem;
	}
	public String getAutorNome() {
		return autorNome;
	}
	public void setAutorNome(String autorNome) {
		this.autorNome = autorNome;
	}
	public String getAutorBiografia() {
		return autorBiografia;
	}
	public void setAutorBiografia(String autorBiografia) {
		this.autorBiografia = autorBiografia;
	}
}
