package com.squallsoft.api.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.boot.logging.logback.LogbackLoggingSystemProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.squallsoft.api.dominios.BurnLog;
import com.squallsoft.api.dominios.BurnLogTransient;
import com.squallsoft.api.dominios.FirmwareToBurn;
import com.squallsoft.api.dominios.Mapper;
import com.squallsoft.api.dominios.PedidoEntity;
import com.squallsoft.api.dominios.licenca.Cliente;
import com.squallsoft.api.dominios.licenca.Licenca;
import com.squallsoft.api.dominios.licenca.enums.PedidoStatus;
import com.squallsoft.api.service.BurnLogService;
import com.squallsoft.api.service.LicencaService;
import com.squallsoft.api.service.MapperService;
import com.squallsoft.api.service.PedidoService;
import com.squallsoft.api.util.Security;

@RestController
@RequestMapping("/pedido")
public class PedidoRest {

	Logger logger = LoggerFactory.getLogger(PedidoRest.class);
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private MapperService mapperService;
	@Autowired
	private BurnLogService burnLogService;
	
	@Autowired
	private LicencaService licenseService;
	
	//@RolesAllowed("BURN")
	@RequestMapping(value="/novo/{firmId}/{code}/{quant}", method = RequestMethod.GET)
	public Object getFirmware(@PathVariable("firmId") Long firmId, 
							  @PathVariable("code")  String code, 
							  @PathVariable("quant") Integer quant
							  ){
		
		// configura retorno
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(MediaType.MULTIPART_MIXED_VALUE));
		InputStream in = null;
		String clienteInfo; // licenca cod vem do cliente na URL
		try { 
			// fill licença do usuario
			Licenca licenca = licenseService.obterLicencaPelaMachineCode(code);
			
			if(licenca == null) {
				//"Licença não encontrada"
				System.out.println("Licença não encontrada. MCode: "+code);
				
				FirmwareToBurn fi = new FirmwareToBurn(code, null, LocalDate.now(), -1l, false, "Não foi encontrada licença.");
								
				byte[] source = SerializationUtils.serialize(fi);		
				logger.info("Firmware size: "+source.length);		
				byte[] compactado = Security.compressByteArray(source);
				logger.info("Firmware zip: "+compactado.length);	
				
				// Transforma pra inputstream
				in = new ByteArrayInputStream(compactado);
				
				// envia
				return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
			}
			
			System.out.println("Licença encontrada.");
			
			// verifica se esta legal, e tem creditos disponiveis;
			clienteInfo = licenseService.verifyLicenca(licenca, quant, code);
			
			// cliente pode fazer o pedido?
			if(!clienteInfo.equalsIgnoreCase("")) {
				System.out.println(clienteInfo);
				FirmwareToBurn fi = new FirmwareToBurn(code, null, LocalDate.now(), -1l, false, clienteInfo);
				
				byte[] source = SerializationUtils.serialize(fi);		
				logger.info("Firmware size: "+source.length);		
				byte[] compactado = Security.compressByteArray(source);
				logger.info("Firmware zip: "+compactado.length);	
				
				// Transforma pra inputstream
				in = new ByteArrayInputStream(compactado);
				
				// envia
				return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
			}
			System.out.println("Cliente validado");
			// fim da verificação			
			// aqui temos uma licença validada pelo licença manager			
									
			Mapper mapper = mapperService.findById(firmId);
			if(mapper.getId() == null) {
				FirmwareToBurn fi = new FirmwareToBurn(code, null, LocalDate.now(), -1l, false, "Mapper não encontrado.");
				
				byte[] source = SerializationUtils.serialize(fi);		
				logger.info("Firmware size: "+source.length);		
				byte[] compactado = Security.compressByteArray(source);
				logger.info("Firmware zip: "+compactado.length);	
				
				// Transforma pra inputstream
				in = new ByteArrayInputStream(compactado);
				
				// envia
				return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
			}
			
			// Grava informação do pedido no banco
			PedidoEntity pedido = new PedidoEntity(code, quant, LocalDateTime.now(), mapper.getId());
			// 
			//novos atributos do pedido
			pedido.setLicenca(licenca);
			pedido.setQtdSuccess(0);
			pedido.setQtdFail(0);
			pedido.setQtdCancel(0);
			pedido.setStatus(PedidoStatus.OPEN);
			
			// persiste pedido
			PedidoEntity pe = pedidoService.save(pedido);
			licenca.getPedidos().add(pe);
			
			// atualiza licença
			licenseService.save(licenca);
			
			// Entidade que será devolvida ao cliente		
			FirmwareToBurn ftb = new FirmwareToBurn(code, mapper.getXsvf_file(), LocalDate.now(), pedido.getId(), Boolean.TRUE, null);
			
			// Serializa objeto
			byte[] source = SerializationUtils.serialize(ftb);		
			logger.info("Firmware size: "+source.length);		
			byte[] compactado = Security.compressByteArray(source);
			logger.info("Firmware zip: "+compactado.length);	
			
			// Transforma pra inputstream
			in = new ByteArrayInputStream(compactado);
			
			// envia
			return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} catch (IOException e) {
			logger.error("Error: " + e.getLocalizedMessage());
		}finally{
			try {
				if(in != null)
					in.close();
			} catch (IOException e) {
				logger.error("Ocorreu um erro ao fechar o stream do arquivo."+ e.getMessage());
			}
		}
		return ResponseEntity.ok().body("Firmware not found.");		
	}
	
	/**
	 * Atualiza entidade pedido, se 
	 * qtdSuccess + qtdFail + qtdCancel for = a qtdTotal do pedido
	 * fecha pedido, caso contrario
	 * coloca pedido com status VERIFICAR.
	 * @param pid
	 * @param qtdCancel
	 * @return
	 */
	//@RolesAllowed("BURN")
	@GetMapping("/close/{pid}/{qtdCancel}")
	public ResponseEntity<String> processAndClosePedido(@PathVariable("pid") Long pid, @PathVariable("qtdCancel") Integer qtdCancel) {
		
		Integer qtdSuccess = 0;
		Integer qtdFail    = 0;		
		
		// Obtem pedido
		PedidoEntity pedido = pedidoService.findPedido(pid);
		
		if(pedido.getId() == null) {
			return ResponseEntity.ok().body("Pedido não encontrado.");	
		}
		
		// Obtem Licenca
		Licenca licenca = pedido.getLicenca();
		
		// Obtem lista de logs
		List<BurnLog> logs = burnLogService.findByPedido(pid);
				
		// processa logs
		for(BurnLog log : logs) {
			if(log.getLog().contains("Success!")) {
				qtdSuccess++;
			}else {
				qtdFail++;
			}
		}
		
		// sanity check
		if((qtdSuccess + qtdFail) != pedido.getQuant() && (qtdSuccess + qtdFail + qtdCancel) != pedido.getQuant()) {
			pedido.setQtdSuccess(qtdSuccess);
			pedido.setQtdFail(qtdFail);
			pedido.setQtdCancel(qtdCancel);
			pedido.setStatus(PedidoStatus.VERIFICAR);
			
			pedidoService.save(pedido);
			// Erro grave, estou sendo roubado.
			return ResponseEntity.ok().body("Fui roubado?");	
		}else {
			// Ufa pedido dentro da normalidade.
			// Atualiza pedido no banco
			pedido.setQtdSuccess(qtdSuccess);
			pedido.setQtdFail(qtdFail);
			pedido.setQtdCancel(qtdCancel);
			pedido.setStatus(PedidoStatus.CLOSE);
			
			pedidoService.save(pedido);
			
			// atualiza licenca
			licenca.setQtdDisponivel(licenca.getQtdDisponivel() - qtdSuccess);
			licenseService.save(licenca);
		}		
		return ResponseEntity.ok().body("Tudo ok.");	
	}
	
	/**
	 * Usado para obter ID de uma nova task
	 * quando se deseja inicar uma gravação
	 * @param id
	 * @return TaskLog ID
	 */
	//@RolesAllowed("BURN")
	@GetMapping(value="/newTask/{id}", produces = "application/json")
	public ResponseEntity<Long> newTask(@PathVariable("id") Long id) {
		PedidoEntity pedido = pedidoService.findPedido(id);
		
		BurnLog burnLog = new BurnLog();
		
		if(pedido.getId() != null){
			burnLog.setPedidoFK(id);
			burnLog.setLog("");
			
			// persist nova task
			burnLogService.save(burnLog);	
			
			// retorna id da nova task
			return ResponseEntity.ok().body(burnLog.getId());
		}		
		
		return ResponseEntity.ok().body(0L);	
	}
	
	/**
	 * Usado para atualizar o log da tsk(logo da gravadora) 
	 * @param burnLog
	 * @return
	 */
	//@RolesAllowed("BURN")
	@PostMapping(value="/updateTask", produces = "application/json", consumes="application/json")
	public ResponseEntity<Long>  addTaskLog(@RequestBody BurnLogTransient burnLog) {
	
		BurnLog log = null;
		
		if(burnLog.getBurnID() > 0 && burnLog.getPedidoID() > 0) {
			
			// Obtem objeto burnlog
			log = burnLogService.findById(burnLog.getBurnID());
			
			// atualiza log
			log.setLog(burnLog.getLog().toString());
			
			// persist log
			burnLogService.save(log);		
		}
		
		return ResponseEntity.ok().body(log == null ? 0L:log.getId());
	}	
}
