package com.squallsoft.api.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squallsoft.api.dominios.Board;

@Repository
public interface RepositorioBoard extends JpaRepository<Board, Long>{

	public List<Board> findAllBoardsByMapperId(Long maperId);
}
