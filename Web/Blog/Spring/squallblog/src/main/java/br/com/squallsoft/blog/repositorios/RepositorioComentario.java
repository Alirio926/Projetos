package br.com.squallsoft.blog.repositorios;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.squallsoft.blog.dominios.Comentario;

public interface RepositorioComentario extends JpaRepository<Comentario, Long>{
	List<Comentario> findByPostagemId(Long id);
	
	@Query("SELECT c "
		 + "FROM Postagem p, Comentario c "
		 + "WHERE p.id = :#{#id}")
	List<Comentario> lerComments(Long id);
}
