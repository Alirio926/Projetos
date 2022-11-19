package com.squallsoft.api.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.squallsoft.api.dominios.licenca.Cliente;
import com.squallsoft.api.dominios.licenca.Licenca;
import com.squallsoft.api.dominios.licenca.enums.Produto;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public interface LicencaService {

	public List<Licenca> listarTodos();
	
	public String verifyLicenca(Licenca licenca, Integer qtdPedido, String machineCode);
	
	/**
	 * Lista todas as licenças no banco, indexando por produto
	 * @param produto
	 * @return List<Licenca>
	 */
	public List<Licenca> listarLicencaPeloProduto(Produto produto);
	
	/**
	 * Lista todas as licenças no banco indexando pelo cliente
	 * @param cliente
	 * @return List<Licenca>
	 */
	public List<Licenca> listarLicencaPeloCliente(Cliente cliente);
	
	/**
	 * Obter licença pelo codigo da licença
	 * @param code
	 * @return Licença
	 */
	public Licenca obterLicencaPeloCodigo(String code);
	
	/**
	 * 
	 * @param machineCode
	 * @return Licenca
	 */
	public Licenca obterLicencaPelaMachineCode(String machineCode);	
	
	/**
	 * Obter Licença valida para uso.
	 * @param cliente
	 * @return Licenca
	 */
	public Licenca obterLicencaDisponivel(Cliente cliente);
	
	/**
	 * 
	 * @param licenca_id
	 * @return
	 */
	public Licenca findById(Long licenca_id);

	/**
	 * 
	 * @param licenca
	 */
	public Licenca save(Licenca licenca);
}
