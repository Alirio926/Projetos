package br.com.squallsoftware.clinvet.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.squallsoftware.clinvet.dominios.Prontuario;

public interface RepositorioProntuario extends JpaRepository<Prontuario, Long>{
	@Query("SELECT a FROM Prontuario a WHERE a.observacao LIKE %:observacao%")
	List<Prontuario> findByObservacao(@Param("observacao") String observacao);
}
