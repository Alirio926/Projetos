package br.com.squallsoftware.clinvet.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.squallsoftware.clinvet.dominios.Animal;
import br.com.squallsoftware.clinvet.dominios.Veterinario;

public interface RepositorioVeterinario extends JpaRepository<Veterinario, Long>{
	@Query("SELECT a FROM Veterinario a WHERE a.nome_vet LIKE %:nome_vet%")
	List<Veterinario> findByNome(@Param("nome_vet") String nome_vet);
}
