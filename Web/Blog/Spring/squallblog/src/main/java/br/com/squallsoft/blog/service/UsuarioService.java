package br.com.squallsoft.blog.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.squallsoft.blog.dominios.Avatar;
import br.com.squallsoft.blog.dominios.Usuario;
import br.com.squallsoft.blog.repositorios.RepositorioUsuario;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UsuarioService {

	private static final Logger LOG = Logger.getLogger(Usuario.class);
	
	@Autowired
	private RepositorioUsuario repositorio;
	
	public List<Usuario> findAll(){
		return repositorio.findAll();
	}
	
	public Usuario findByEmail(String email){
		return repositorio.findByEmail(email);
	}
	
	public Usuario findByAvatar(Avatar avatar) {
		return repositorio.findByAvatar(avatar);
	}
	
	public Usuario findById(Long id) {
		return repositorio.findById(id).get();
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		repositorio.deleteById(id);
	}
	
	@Transactional(readOnly = false)
	public Usuario save(Usuario user) {
		if(user.getDatacadastro() == null) {
			user.setDatacadastro(LocalDate.now());
		}
		String hash = new BCryptPasswordEncoder().encode(user.getSenha());
		user.setSenha(hash);	
		
		return repositorio.save(user);
		
	}
}
