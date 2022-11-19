package com.squallsoft.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.squallsoft.api.dominios.BurnLog;
import com.squallsoft.api.repositorios.RepositorioLog;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class BurnLogService {

	@Autowired
	private RepositorioLog repLog;
	
	@Transactional(readOnly = false)
	public void save(BurnLog log) {
		// TODO Auto-generated method stub
		repLog.saveAndFlush(log);
	}

	public BurnLog findById(Long id) {
		return repLog.findById(id).get();
	}
	
	public List<BurnLog> findAll(){
		return repLog.findAll();
	}
	
	public List<BurnLog> findByPedido(Long fk){
		return repLog.findByPedidoFK(fk);
	}
}
