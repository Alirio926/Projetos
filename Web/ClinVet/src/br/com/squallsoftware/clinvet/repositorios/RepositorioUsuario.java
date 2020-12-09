package br.com.squallsoftware.clinvet.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.squallsoftware.clinvet.dominios.Usuario;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long>{
	Usuario findByUsername(String username);
	Usuario findByRole(String role);
}
