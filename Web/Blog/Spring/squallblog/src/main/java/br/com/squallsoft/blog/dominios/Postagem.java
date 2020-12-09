package br.com.squallsoft.blog.dominios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_postagens")
public class Postagem extends AbstractPersistable<Long>{
	
	@Column(nullable=false, unique=true, length=60)
	private String titulo;
	
	@Column(nullable=false, columnDefinition = "LONGTEXT")
	private String texto;
	
	@Column(nullable=false, unique=true, length=60)
	private String permalink;
	
	
	@Column(name = "data_postagem", nullable=false)
	private LocalDateTime dataPostagem;
	
	@JsonIgnoreProperties({"postagens"})
	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Autor autor;
	
	@JsonIgnoreProperties({"postagens"})
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "postagens_has_categorias", 
			   joinColumns = @JoinColumn(name = "postagem_id"),
	    inverseJoinColumns = @JoinColumn(name = "categoria_id"))	
	private List<Categoria> categorias;
	
	@OneToMany(mappedBy = "postagem", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Comentario> comentarios;		
	
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
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

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public LocalDateTime getDataPostagem() {
		return dataPostagem;
	}

	public void setDataPostagem(LocalDateTime dataPostagem) {
		this.dataPostagem = dataPostagem;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
}
