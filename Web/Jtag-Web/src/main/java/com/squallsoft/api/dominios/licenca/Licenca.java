package com.squallsoft.api.dominios.licenca;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.squallsoft.api.dominios.PedidoEntity;
import com.squallsoft.api.dominios.licenca.enums.LicencaStatus;
import com.squallsoft.api.dominios.licenca.enums.Produto;

@Entity
@Table(name = "tbl_licencas")
public class Licenca extends AbstractPersistable<Long> {

	/**
	 * Código gerado e enviado ao cliente.
	 */
	private String license;
	
	/**
	 * Data de ativação da licença.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataAtivacao;
	
	/**
	 * Quantidade de copias liberada.
	 */
	private Integer qtdInicial;
	
	/**
	 * Quantidade disponivel.
	 */
	private Integer qtdDisponivel;
	
	/**
	 * 
	 */
	private LicencaStatus status;
	
	/**
	 * Código da maquina liberada parausar esta licença
	 */
	private String machineCode;
	
	/**
	 * Produto atrelado a esta licença.
	 */
	private Produto produto;
	
	/**
	 * Valor da licensa em reais.
	 */
	private Double valor;
	
	/**
	 * Cliente ao qual esta licença pertence
	 */
	private Long clienteId;
	
	/**
	 * 
	 */
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "licenca", cascade = CascadeType.ALL)
	private List<PedidoEntity> pedidos;
	
	

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public LocalDate getDataAtivacao() {
		return dataAtivacao;
	}

	public void setDataAtivacao(LocalDate dataAtivacao) {
		this.dataAtivacao = dataAtivacao;
	}

	public Integer getQtdInicial() {
		return qtdInicial;
	}

	public void setQtdInicial(Integer qtdInicial) {
		this.qtdInicial = qtdInicial;
	}

	public Integer getQtdDisponivel() {
		return qtdDisponivel;
	}

	public void setQtdDisponivel(Integer qtdDisponivel) {
		this.qtdDisponivel = qtdDisponivel;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public List<PedidoEntity> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoEntity> pedidos) {
		this.pedidos = pedidos;
	}

	public LicencaStatus getStatus() {
		return status;
	}

	public void setStatus(LicencaStatus status) {
		this.status = status;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	
	
}
