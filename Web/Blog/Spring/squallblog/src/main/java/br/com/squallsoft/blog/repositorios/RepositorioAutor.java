package br.com.squallsoft.blog.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.squallsoft.blog.dominios.Autor;

public interface RepositorioAutor extends JpaRepository<Autor, Long>{
	Autor findByNome(String nome);
}
