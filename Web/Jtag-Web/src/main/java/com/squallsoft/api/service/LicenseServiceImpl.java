package com.squallsoft.api.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.squallsoft.api.dominios.PedidoEntity;
import com.squallsoft.api.dominios.licenca.Cliente;
import com.squallsoft.api.dominios.licenca.Licenca;
import com.squallsoft.api.dominios.licenca.enums.LicencaStatus;
import com.squallsoft.api.dominios.licenca.enums.PedidoStatus;
import com.squallsoft.api.dominios.licenca.enums.Produto;
import com.squallsoft.api.repositorios.RepositorioLicenca;
import com.squallsoft.api.repositorios.RepositorioPedido;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class LicenseServiceImpl implements LicencaService{

	@Autowired
	private RepositorioLicenca repository;
	@Autowired
	private RepositorioPedido pedidoRep;
	
	@Override
	public List<Licenca> listarLicencaPeloProduto(Produto produto) {
		return repository.findByProduto(produto);
	}

	@Override
	public List<Licenca> listarLicencaPeloCliente(Cliente cliente) {
		return repository.findByClienteId(cliente.getId());
	}

	@Override
	public Licenca obterLicencaPeloCodigo(String code) {
		return repository.findByLicense(code);
	}

	@Override
	public Licenca obterLicencaDisponivel(Cliente cliente) {
		return repository.findValidLicense(cliente.getId());
	}

	@Override
	public Licenca findById(Long licenca_id) {
		try {
			return repository.findById(licenca_id).get();
		}catch(NoSuchElementException ex) {
			return new Licenca();
		}
		
	}

	@Override
	@Transactional(readOnly = false)
	public Licenca save(Licenca licenca) {
		return repository.save(licenca);
		
	}

	@Override
	public Licenca obterLicencaPelaMachineCode(String machineCode) {
		return repository.findByMachineCode(machineCode, LicencaStatus.OPEN);
	}
	
	/**
	 * Verifica se:
	 * Licença possui creditos para o novo pedido.
	 * Existem pedidos incompletos.
	 * Se licença esta valida.
	 * Se licença pertence a maquina.
	 * Neste ponto, a licença do usuario esta atualizada
	 */
	@Override
	public String verifyLicenca(Licenca licenca, Integer qtdPedido, String machineCode) {
		// Licença esta ativa?
		if(licenca.getStatus() != LicencaStatus.OPEN) {
			return "Licença informada não é valida.";
		}
		
		// Licença pertence a esse computador?
		//StringTokenizer st = new StringTokenizer(licenca.getMachineCode(), ";");
		if(!licenca.getMachineCode().equalsIgnoreCase(machineCode)) {
			return "Licença com uso não autorizado nesta estação.";
		}
		
		// Licença possui pedidos não finalizados?
		List<PedidoEntity> listaPedidos = 
				pedidoRep.findPedidoFromClinteByStatusAndLicenca(licenca.getId(), PedidoStatus.OPEN);
		
		if(listaPedidos.size() > 0) {
			return "Licença possui pedidos em aberto.";
		}
		
		// Licença possui creditos para a quantidade solicitada no pedido?
		if(licenca.getQtdDisponivel() < qtdPedido) {
			return "A quantidade solicitada no pedido ultrapassa a quantidade disponivel na licença.";
		}
		
		return "";
	}

	@Override
	public List<Licenca> listarTodos() {
		return repository.findAll();
	}
}
