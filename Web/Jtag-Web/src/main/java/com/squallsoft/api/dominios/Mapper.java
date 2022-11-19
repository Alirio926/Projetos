package com.squallsoft.api.dominios;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tbl_mappers")
public class Mapper extends AbstractPersistable<Long>{

	@Column(nullable=false)
	private String chip_name;
	
	@Column(nullable=false)
	private String supported_boards;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
	@Column(name = "data_ativacao", nullable=false)
	private LocalDate data_ativacao;
	
	@Column(name = "ativo", nullable=false)
	private Boolean ativo;	
	
	@Lob
	@Column(nullable=false)
	private byte[] xsvf_file;

	@Column(name = "ines", nullable=false)
    private Integer ines;
	
	public Mapper() {}
	
	public Mapper(String chip_name, String supported_boards, LocalDate data_ativacao, Boolean ativo, byte[] xsvf_file, Integer id) {
		super();
		this.chip_name = chip_name;
		this.supported_boards = supported_boards;
		this.data_ativacao = data_ativacao;
		this.ativo = ativo;
		this.xsvf_file = xsvf_file;
		this.ines = id;
	}

    public Integer getInes() {
        return ines;
    }

    public void setInes(Integer ines) {
        this.ines = ines;
    }    
    
	public String getChip_name() {
		return chip_name;
	}

	public void setChip_name(String chip_name) {
		this.chip_name = chip_name;
	}

	public String getSupported_boards() {
		return supported_boards;
	}

	public void setSupported_boards(String supported_boards) {
		this.supported_boards = supported_boards;
	}

	public LocalDate getData_ativacao() {
		return data_ativacao;
	}

	public void setData_ativacao(LocalDate data_ativacao) {
		this.data_ativacao = data_ativacao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public byte[] getXsvf_file() {
		return xsvf_file;
	}

	public void setXsvf_file(byte[] xsvf_file) {
		this.xsvf_file = xsvf_file;
	}	
}
