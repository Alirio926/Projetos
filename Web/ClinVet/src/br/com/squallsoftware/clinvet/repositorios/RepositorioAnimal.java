package br.com.squallsoftware.clinvet.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.squallsoftware.clinvet.dominios.Animal;

public interface RepositorioAnimal extends JpaRepository<Animal, Long> {
	@Query("SELECT a FROM Animal a WHERE a.nome LIKE %:nome%")
	List<Animal> findByNome(@Param("nome") String nome);
}
