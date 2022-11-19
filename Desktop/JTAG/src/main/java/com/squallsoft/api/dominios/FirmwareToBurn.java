package com.squallsoft.api.dominios;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;


import com.fasterxml.jackson.annotation.JsonFormat;

//@Entity
//@Table(name = "tbl_firmwares")
public class FirmwareToBurn implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String code;
	public byte[] xsvf_file;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
	private LocalDate data;
	public int hash;
	private Long pedidoId;
	private Boolean isValid;
	private String message;
	public String end = "CAFEBABE";
	
	public FirmwareToBurn() {}
	
	public FirmwareToBurn(String code, byte[] xsvf_file, LocalDate data, Long pid, Boolean v, String m) {
		super();
		this.code = code;
		this.xsvf_file = xsvf_file;
		this.data = data;
		this.pedidoId = pid;
		this.message = m;
		this.isValid = v;
				
	}
	
	
	
	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public byte[] getXsvf_file() {
		return xsvf_file;
	}
	public void setXsvf_file(byte[] xsvf_file) {
		this.xsvf_file = xsvf_file;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public int getHash() {
		return hash;
	}
	public void setHash(int hash) {
		this.hash = hash;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + Arrays.hashCode(xsvf_file);
		return result;
	}
	
}
