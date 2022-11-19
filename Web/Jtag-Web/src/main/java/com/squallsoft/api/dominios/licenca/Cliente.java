package com.squallsoft.api.dominios.licenca;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.squallsoft.api.dominios.User;

@Entity
@Table(name = "tbl_clientes")
public class Cliente extends AbstractPersistable<Long>{

	/**
	 * Nome do cliente
	 */
	private String nome;
	
	/**
	 * RG do cliente
	 */
	private String rg;
	
	/**
	 * Cliente possui alguma restrição
	 */
	private Boolean restricao;
	
	/**
	 * Motivo da restrição do cliente
	 */
	private String motivo;
	
	/**
	 * Data do cadastro do cliente
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataCadastro;
	
	/**
	 * Licenças adiquiridas pelo cliente
	 */
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER)
	private List<Licenca> licencas;
	
	@OneToOne
	private User user;
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Boolean getRestricao() {
		return restricao;
	}

	public void setRestricao(Boolean restricao) {
		this.restricao = restricao;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public List<Licenca> getLicencas() {
		return licencas;
	}

	public void setLicencas(List<Licenca> licencas) {
		this.licencas = licencas;
	}
	
}
