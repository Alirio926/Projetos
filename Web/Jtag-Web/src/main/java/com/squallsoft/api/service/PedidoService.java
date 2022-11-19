package com.squallsoft.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.squallsoft.api.dominios.PedidoEntity;
import com.squallsoft.api.dominios.User;
import com.squallsoft.api.dominios.licenca.Licenca;
import com.squallsoft.api.repositorios.RepositorioPedido;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class PedidoService {

	@Autowired
	private RepositorioPedido repPedido;
	
	@Transactional(readOnly = false)
	public PedidoEntity save(PedidoEntity p) {
		return repPedido.saveAndFlush(p);
	}

	public PedidoEntity findPedido(Long sessionId) {
		// TODO Auto-generated method stub
		Optional<PedidoEntity> rep = repPedido.findById(sessionId);
		if(!rep.isPresent())
			return new PedidoEntity();
		
		return repPedido.findById(sessionId).get();
	}
	
	public List<PedidoEntity> FindByData(int mes, int ano) {
		return repPedido.findByData(mes, ano);
	}
	
	public List<PedidoEntity> FindByLicenca(Licenca lic){
		return repPedido.findByLicenca(lic.getId());
	}

	public List<PedidoEntity> listarTodos() {
		// TODO Auto-generated method stub
		return repPedido.findAll();
	}
	
	public List<PedidoEntity> listarByCliente(Long cId) {
		return repPedido.findPedidoByCliente(cId);
	}
	
	public List<PedidoEntity> findPedidoByClienteUserName(User u, int mes, int ano){
		return repPedido.findPedidoByClienteUserName(u, mes, ano);
	}
}
