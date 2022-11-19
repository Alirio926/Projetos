package com.squallsoft.api.dominios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "tbl_burns")
public class BurnLog extends AbstractPersistable<Long>{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column(name = "pedidoFK", nullable=false)
	private Long pedidoFK;
	
	@Column(name = "info", nullable=true, columnDefinition = "TEXT")
	private String log;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPedidoFK() {
		return pedidoFK;
	} 

	public void setPedidoFK(Long pedidoFK) {
		this.pedidoFK = pedidoFK;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}	
	
}
