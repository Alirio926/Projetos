package br.com.squallsoft.blog.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.squallsoft.blog.dominios.Postagem;

public interface RepositorioPostagem extends JpaRepository<Postagem, Long>{
	
	public Postagem findByPermalink(String permalink);
		
	@Query("SELECT p.id AS id, p.titulo AS titulo, p.texto AS texto, p.dataPostagem AS dataPostagem, p.autor.id AS autorId, "
			+ "p.autor.nome As autorNome, p.autor.biografia AS autorBiografia "
		 + "FROM Postagem p "		 
		 //+ "LEFT JOIN p.categorias c "
		 /*+ "GROUP BY c.id"*/) 
	public List<Object> listarPosts();
}
