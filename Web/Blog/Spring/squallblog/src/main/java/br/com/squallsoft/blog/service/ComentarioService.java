package br.com.squallsoft.blog.service;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.squallsoft.blog.dominios.Comentario;
import br.com.squallsoft.blog.repositorios.RepositorioComentario;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ComentarioService {

	private static final Logger LOG = Logger.getLogger(CategoriaService.class);
	@Autowired
	RepositorioComentario repository;
	
	@Transactional(readOnly = false)
	public void save(Comentario comentario) {
		comentario.setDataComentario(LocalDateTime.now());
		repository.save(comentario);
	}
}
