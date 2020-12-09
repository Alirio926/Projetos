package br.com.squallsoft.blog.dominios;

import java.time.LocalDateTime;
import java.util.List;

public class ListAllPosts {

	private Long id;
	private String titulo;
	private String texto;
	private String permalink;
	private String dataPostagem;
	private long autorId;
	private String autorNome;
	private String autorBiografia;
	private String perfil;
	private List<String> categorias;
	
	
	
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
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
	public long getAutorId() {
		return autorId;
	}
	public void setAutorId(long autorId) {
		this.autorId = autorId;
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
	public List<String> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}
	
}
