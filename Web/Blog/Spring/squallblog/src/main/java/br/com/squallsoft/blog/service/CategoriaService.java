package br.com.squallsoft.blog.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.squallsoft.blog.dominios.Categoria;
import br.com.squallsoft.blog.repositorios.RepositorioCategoria;
import br.com.squallsoft.blog.util.MyReplaceString;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class CategoriaService {
	
	private static final Logger LOG = Logger.getLogger(CategoriaService.class);
	@Autowired
	RepositorioCategoria repositorio;
	
	@Transactional(readOnly = false)
	public void saveOrUpdate(Categoria categoria){
		String permalink = MyReplaceString.formatarPermalink(categoria.getDescricao());
		categoria.setPermalink(permalink);
		repositorio.save(categoria);
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		repositorio.deleteById(id);
	}
	
	public Categoria findById(Long id) {
		return repositorio.findById(id).get();
	}
	
	public Categoria findByDescricao(String descricao) {
		return repositorio.findByDescricao(descricao);
	}
	
	public List<Categoria> findAll(){
		return repositorio.findAll();
	}

}
