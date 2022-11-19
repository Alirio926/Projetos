package com.squallsoft.api.dominios;

import java.time.LocalDate;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tbl_firmwares")
public class FirmwareEntity extends AbstractPersistable<Long>{

	@Column(nullable=false)
	private String description;	
	
	@Column(nullable=false)
	private String name;	
	
	@Column(nullable=false)
	private String version;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
	@Column(name = "data_activate", nullable=false)
	private LocalDate dataactivate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
	@Column(name = "data_desactivate", nullable=true)
	private LocalDate datadesactivate;	
	
	@Lob
	@Column(nullable=false)
	private byte[] xsvf_file;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public LocalDate getDataactivate() {
		return dataactivate;
	}

	public void setDataactivate(LocalDate dataactivate) {
		this.dataactivate = dataactivate;
	}

	public LocalDate getDatadesactivate() {
		return datadesactivate;
	}

	public void setDatadesactivate(LocalDate datadesactivate) {
		this.datadesactivate = datadesactivate;
	}

	public byte[] getXsvf_file() {
		return xsvf_file;
	}

	public void setXsvf_file(byte[] xsvf_file) {
		this.xsvf_file = xsvf_file;
	}

	@Override
	public String toString() {
		return "FirmwareEntity [description=" + description + ", name=" + name + ", version=" + version
				+ ", dataactivate=" + dataactivate + ", datadesactivate=" + datadesactivate  + "]";
	}	
	
}
