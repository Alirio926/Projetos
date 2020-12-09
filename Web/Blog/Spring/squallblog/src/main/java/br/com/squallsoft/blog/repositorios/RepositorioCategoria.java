package br.com.squallsoft.blog.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.squallsoft.blog.dominios.Categoria;

public interface RepositorioCategoria extends JpaRepository<Categoria, Long>{
	Categoria findByDescricao(String descricao);
}
