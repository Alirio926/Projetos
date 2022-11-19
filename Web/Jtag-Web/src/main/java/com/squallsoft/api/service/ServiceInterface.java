package com.squallsoft.api.service;

import java.util.List;

import com.squallsoft.api.dominios.Board;


public interface ServiceInterface<T, ID> {

	public List<T> findAll();
	
	public T findById(ID id);
	
	public <S extends T> S saveAndFlush(S entity);
	
	public void delete(ID id);
	
	// especificos
	public Integer processMapperFiles();
	
	public List<Board> findAllBoardsByMapperId(Long id);
	//public FirmwareToBurn getFirmware(Long id, String code) throws Exception;
}
