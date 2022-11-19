package com.squallsoft.api.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squallsoft.api.dominios.Board;
import com.squallsoft.api.dominios.Mapper;
import com.squallsoft.api.service.MapperService;

@RestController
@RequestMapping("/mapper")
public class MapperController {

	@Autowired
	private MapperService mapperService;
	
	/**
	 * Procura no sistema de arquivo por novos mappers para procesar
	 * @return quantidade de mapper processados
	 */
	@RolesAllowed("ADMIN")
	@GetMapping("/processMappers") 
	public ResponseEntity<String> novoMapper(){ 
		Integer processedMappers = mapperService.processMapperFiles();
		
		return ResponseEntity.ok().body(String.format("%d mapper(s) encontrado(s).", processedMappers));
	}
	@RolesAllowed("ADMIN")
	@GetMapping("/processBoards")
	public ResponseEntity<String> novoBoard(){
		Integer processedBoards = 0;
		
		return ResponseEntity.ok().body(String.format("%d board(s) encontrado(s).", processedBoards));
	}
	
	/**
	 * Lista chips disponivais para gravação
	 * @return Uma lista com todos os chips suportados
	 */
	@GetMapping(value = "/listarMappers", produces = "application/json")
	public List<Mapper> listAllFirmwares() {
		return mapperService.findAll();
	}
	
	/**
	 * procura por um mapperID e retorna lista de pcb suportadas
	 * @param mapperId
	 * @return lista de todas as placas suportadas por este mapper
	 */
	@GetMapping(value = "/findBoardsByMapper/{mapperId}", produces = "application/json")
	public List<Board> findBoardsByMapper(@PathVariable("mapperId") Long mapperId){
		List<Board> listBoards = null;
		
		// Obtem todas as placas suportadas por este mapper
		listBoards = mapperService.findAllBoardsByMapperId(mapperId);
				
		return listBoards;
	}
}
