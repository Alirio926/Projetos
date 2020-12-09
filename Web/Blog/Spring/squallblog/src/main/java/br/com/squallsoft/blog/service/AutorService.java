package br.com.squallsoft.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.squallsoft.blog.dominios.Autor;
import br.com.squallsoft.blog.repositorios.RepositorioAutor;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class AutorService {

	@Autowired
	private RepositorioAutor repository;
	
	public Autor findById(Long id){
		return repository.findById(id).get();
	}
	public Autor findByNome(String nome) {
		return repository.findByNome(nome);
	}
	public List<Autor> findAll(){
		return repository.findAll();
	}
	
	public Autor save(Autor autor) {
		return repository.save(autor);
	}
}
