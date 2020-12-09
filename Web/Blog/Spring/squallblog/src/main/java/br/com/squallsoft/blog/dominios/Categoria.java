package br.com.squallsoft.blog.dominios;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "tbl_categoria")
public class Categoria extends AbstractPersistable<Long>{

	@Column(nullable=false, unique=true, length=30)
	private String descricao;
	
	@Column(nullable=false, unique=true, length=30)
	private String permalink;
	
	@ManyToMany
	@JoinTable(
			name = "postagens_has_categorias", 
			joinColumns = @JoinColumn(name = "categoria_id"),
	 inverseJoinColumns = @JoinColumn(name = "postagem_id"))
	private List<Postagem> postagens;

	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public List<Postagem> getPostagens() {
		return postagens;
	}

	public void setPostagens(List<Postagem> postagens) {
		this.postagens = postagens;
	}
}
