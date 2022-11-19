package com.squallsoft.api.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squallsoft.api.dominios.BurnLog;

@Repository
public interface RepositorioLog extends JpaRepository<BurnLog, Long> {

	public List<BurnLog> findByPedidoFK(Long pedidoFK);
}
