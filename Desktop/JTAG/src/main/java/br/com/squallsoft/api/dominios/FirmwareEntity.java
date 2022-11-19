/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.api.dominios;

/**
 *
 * @author aliri
 */

public class FirmwareEntity {

        private Long id;
        
	private String description;	
	
	private String name;	
	
	private String version;
		
	private String dataactivate;
	
	private String datadesactivate;	
	
	private byte[] xsvf_file;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        
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

	public String getDataactivate() {
		return dataactivate;
	}

	public void setDataactivate(String dataactivate) {
		this.dataactivate = dataactivate;
	}

	public String getDatadesactivate() {
		return datadesactivate;
	}

	public void setDatadesactivate(String datadesactivate) {
		this.datadesactivate = datadesactivate;
	}

	public byte[] getXsvf_file() {
		return xsvf_file;
	}

	public void setXsvf_file(byte[] xsvf_file) {
		this.xsvf_file = xsvf_file;
	}	
	
}
