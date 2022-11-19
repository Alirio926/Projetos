package com.squallsoft.api.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squallsoft.api.dominios.Board;
import com.squallsoft.api.dominios.Mapper;
import com.squallsoft.api.repositorios.RepositorioBoard;
import com.squallsoft.api.repositorios.RepositorioMapper;
import com.squallsoft.api.util.MapperList;
import com.squallsoft.api.util.MapperLoader;

@Service
public class MapperService{

	Logger logger = LoggerFactory.getLogger(MapperService.class);
	private MapperLoader mapper = new MapperLoader();
	private MapperList lstMappers = new MapperList();
	@Autowired
	private RepositorioMapper repMapper;
	@Autowired
	private RepositorioBoard repBoard;
	
	public List<Mapper> findAll() {
		List<Mapper> lstMappers = repMapper.findAll();
		for(int i=0; i < lstMappers.size(); i++) 
			lstMappers.get(i).setXsvf_file(null);
		
		return lstMappers;
	}

	public Mapper findById(Long id) {
		try {
			return repMapper.findById(id).get();
		}catch(NoSuchElementException ex) {
			return new Mapper();
		}
	}

	public <S extends Mapper> S saveAndFlush(S entity) {
		try {
			return repMapper.saveAndFlush(entity);	
		}catch(Exception e) {
			return (S) new Mapper();
		}
	}

	public void delete(Long id) {
		repMapper.deleteById(id);
	}

	public Integer processMapperFiles() {		
		byte code = -3;
		Integer count=0;
		
		try {
			// load file
			lstMappers.MapperListLoad(); 
			
			// ler arquivo com lista de mappers disponiveis para inclusão
			if(!lstMappers.listMapperFound() || !lstMappers.isNewList()) return count; // no list file found or não é novo
			
			logger.debug("Novo mapper found.");
			StringTokenizer st = lstMappers.getListMappers();
						
			RandomAccessFile xsvf;
			byte xsvf_file[];
			// parse em cada file 
			logger.debug("Init mapper parser.");
			String name;
			String boards;
			Integer ines;
			while(st.hasMoreElements()) {
				mapper.readMapperFile(st.nextToken());
				if(mapper.mapperFound()) {
					count++;
					name = mapper.getChipName();
					boards = mapper.getSupportedBoards();
					ines = Integer.decode(mapper.getMapperINES());
					logger.info("Chip Name: " + name + 
							   " SupportedBoards: " + boards);
					
					xsvf = new RandomAccessFile(new File(mapper.getMapperFilePath()),"r");
					
					// re-arrange array size
					xsvf_file = new byte[(int) xsvf.length()];
					
					// read file to buff
					xsvf.read(xsvf_file, 0, (int) xsvf.length());
					
					// encode
					for(int x=0; x < xsvf.length(); x++)
						xsvf_file[x] = (byte) (xsvf_file[x] + code);
					
					// novo objeto
					Mapper chip = new Mapper(name, boards, LocalDate.now(), Boolean.TRUE, xsvf_file, ines);
					
					// persiste no banco
					repMapper.saveAndFlush(chip);
					 
					/* parse por suported boards Ex.: SOROM:0;SUROM:2*/
					StringTokenizer stBoard = new StringTokenizer(boards, ";");
					while(stBoard.hasMoreElements()) {
						// Token Ex.: = SOROM:0
						StringTokenizer stPCB = new StringTokenizer(stBoard.nextToken(), ":");
						String pcb = stPCB.nextToken();
						Integer jmp = Integer.decode(stPCB.nextToken());
						
						Board b = new Board(pcb, jmp, chip.getId());
						repBoard.saveAndFlush(b);
						
						System.out.println(String.format("Chip Id: %d, Board count %d persist Nome: %s, jmp: %d", chip.getId(), count, pcb, jmp));
					}
				}				
			}
			// informa que o arquivo de lista já foi processado, para não incluir duplicados
			lstMappers.removeFlagNewList();
		}catch(Exception ioex) {
			logger.error("Error IOEX: " + ioex.getLocalizedMessage());
		}
		
		return count;
	}
	
	public List<Board> findAllBoardsByMapperId(Long id) {
		List<Board> listBoards = repBoard.findAllBoardsByMapperId(id);
		
		return listBoards;
	}
	
	
}
