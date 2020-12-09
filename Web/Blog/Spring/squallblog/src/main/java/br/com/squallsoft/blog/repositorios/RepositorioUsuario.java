package br.com.squallsoft.blog.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.squallsoft.blog.dominios.Avatar;
import br.com.squallsoft.blog.dominios.Usuario;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long>{

	Usuario findByEmail(String email);
	
	Usuario findByAvatar(Avatar avatar);
}
