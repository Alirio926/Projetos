package com.squallsoft.api.service;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.squallsoft.api.dominios.FirmwareEntity;
import com.squallsoft.api.dominios.FirmwareToBurn;
import com.squallsoft.api.nesdb.Database;
import com.squallsoft.api.repositorios.RepositorioFirmware;
import com.squallsoft.api.util.MapperList;
import com.squallsoft.api.util.MapperLoader;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class FirmwareService {

	private Long lastFirmwareId;
	
	@Autowired
	private RepositorioFirmware repositorio;

	private MapperLoader mapper = new MapperLoader();

	private MapperList lstMappers = new MapperList();
	

		
	@Transactional(readOnly = false)
	public void updateFirmwareList()  {
		byte code = -3;
		/*try {
			// load file
			lstMappers.MapperListLoad(); 
			
			// ler arquivo com lista de mappers disponiveis para inclusão
			if(!lstMappers.listMapperFound() || !lstMappers.isNewList()) return; // no list file found or não é novo
			
			StringTokenizer st = lstMappers.getListMappers();
			
			RandomAccessFile xsvf;
			byte xsvf_file[];
			// parse em cada file 
			while(st.hasMoreElements()) {
				// tenta ler file
				mapper.readMapperFile(st.nextToken());
				if(mapper.mapperFound()) {
					System.err.println("Mapper name: " + mapper.getMapperName()+
									 " Mapper Description: " + mapper.getMapperDescription()+
									 " Mapper version: " + mapper.getMapperVersion()+
									 " Mapper file: " + mapper.getMapperFilePath());
					
					FirmwareEntity fe = new FirmwareEntity();
					fe.setDataactivate(LocalDate.now());
					fe.setDescription(mapper.getMapperDescription());
					fe.setName(mapper.getMapperName());
					fe.setVersion(mapper.getMapperVersion());
					// open mapper file xsvf
					
					xsvf = new RandomAccessFile(new File(mapper.getMapperFilePath()),"r"); // servidor
					//xsvf = new RandomAccessFile(new File(mapper.getMapperFileLocalPath()),"r"); // local
					
					// re-arrange array size
					xsvf_file = new byte[(int) xsvf.length()];
					
					// read file to buff
					xsvf.read(xsvf_file, 0, (int) xsvf.length());
					
					// encode
					for(int x=0; x < xsvf.length(); x++)
						xsvf_file[x] = (byte) (xsvf_file[x] + code);
					// set on entity
					fe.setXsvf_file(xsvf_file);
					// send to DB
					repositorio.saveAndFlush(fe);
				}
			}	
			// informa que o arquivo de lista já foi processado, para não incluir duplicados
			lstMappers.removeFlagNewList();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	/*
	@Transactional(readOnly = false)
	public void newFirmware() throws IOException {
		byte code = -3;
		FirmwareEntity entity = new FirmwareEntity();
		entity.setName("SxROM");
		entity.setVersion("1.0");
		entity.setDescription("MMC1 Mapper like SxROM");
		entity.setDataactivate(LocalDate.now());
		
		RandomAccessFile xsvf = new RandomAccessFile(new File("D:\\Troca de HD\\Codigos\\nes\\Alirio Mapper\\Cart_MMC1\\impact\\Cart_MMC1.xsvf"),"r");
		byte xsvf_file[] = new byte[(int) xsvf.length()];
		xsvf.read(xsvf_file, 0, (int) xsvf.length());
		
		for(int x=0; x < xsvf.length(); x++)
			xsvf_file[x] = (byte) (xsvf_file[x] + code);
		
		entity.setXsvf_file(xsvf_file);
		
		repositorio.saveAndFlush(entity);
	}*/
	
	public List<FirmwareEntity> listFirmware() {
		List<FirmwareEntity> firmEntity = repositorio.findAll();
		for(int i =0; i < firmEntity.size(); i++) {
			firmEntity.get(i).setXsvf_file(null);
		}
		return firmEntity;		
	}
	
	@Transactional(readOnly = false)
	public FirmwareToBurn getFirmwareByTitle(String title, String machineCode) throws Exception{
		
		
		
		
		
		
		// TEMP
		return new FirmwareToBurn();
	}
	
	@Transactional(readOnly = false)
	public FirmwareToBurn getFimware(Long id, String code) throws Exception {
		// calcula hash, inclui na var hash, encrypt,  zip, send
		FirmwareEntity fe = repositorio.findById(id).get();
		
		lastFirmwareId = fe.getId();
		
		FirmwareToBurn fb = new FirmwareToBurn();
		
		fb.setCode(code);
		fb.setData(LocalDate.now());
		fb.setXsvf_file(fe.getXsvf_file());
		fb.setHash(fb.hashCode());
		
		
		return fb;
	}
	
	public Long getLastFirmwareId() { return lastFirmwareId;}
}
