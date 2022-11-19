package com.squallsoft.api.dominios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.squallsoft.api.dominios.licenca.Licenca;
import com.squallsoft.api.dominios.licenca.enums.PedidoStatus;

@Entity
@Table(name = "tbl_pedidos")
public class PedidoEntity extends AbstractPersistable<Long>{
	
	@Column(nullable=false)
	private String machineCode;
	
	@Column(name = "status", nullable=false)
	private PedidoStatus status;
	
	@Column(name = "quant", nullable=false)
	private Integer quant;
	
	@Column(name = "qtdSuccess", nullable=true)
	private Integer qtdSuccess;
	
	@Column(name = "qtdFail", nullable=true)
	private Integer qtdFail;
	
	@Column(name = "qtdCancel", nullable=true)
	private Integer qtdCancel;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
	@Column(name = "data_pedido", nullable=false)
	private LocalDate data; 
	
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm:ss")
	@Column(name = "hora_pedido", nullable=true)
	private LocalTime hora_pedido; 	
	
	@Column(name = "mapperId", nullable=false)
	private Long mappperId;
	
	@ManyToOne
	@JoinColumn(name="licenca_id")
	private Licenca licenca;
	
	public PedidoEntity() {}
	
	public PedidoEntity(String machineCode, Integer quant, LocalDateTime data_pedido, Long mappperId) {
		super();
		this.machineCode = machineCode;
		this.quant = quant;
		this.data = data_pedido.toLocalDate();
		this.hora_pedido = data_pedido.toLocalTime();
		this.mappperId = mappperId;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public Integer getQuant() {
		return quant;
	}

	public void setQuant(Integer quant) {
		this.quant = quant;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data_pedido) {
		this.data = data_pedido;
	}

	public Long getMappperId() {
		return mappperId;
	}

	public void setMappperId(Long mappperId) {
		this.mappperId = mappperId;
	}

	public LocalTime getHora_pedido() {
		return hora_pedido;
	}

	public void setHora_pedido(LocalTime hora_pedido) {
		this.hora_pedido = hora_pedido;
	}

	public PedidoStatus getStatus() {
		return status;
	}

	public void setStatus(PedidoStatus status) {
		this.status = status;
	}

	public Licenca getLicenca() {
		return licenca;
	}

	public void setLicenca(Licenca licenca) {
		this.licenca = licenca;
	}

	public Integer getQtdSuccess() {
		return qtdSuccess;
	}

	public void setQtdSuccess(Integer qtdSuccess) {
		this.qtdSuccess = qtdSuccess;
	}

	public Integer getQtdFail() {
		return qtdFail;
	}

	public void setQtdFail(Integer qtdFail) {
		this.qtdFail = qtdFail;
	}

	public Integer getQtdCancel() {
		return qtdCancel;
	}

	public void setQtdCancel(Integer qtdCancel) {
		this.qtdCancel = qtdCancel;
	}	
}
