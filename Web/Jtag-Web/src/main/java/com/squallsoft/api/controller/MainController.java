package com.squallsoft.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.squallsoft.api.dominios.BurnLog;
import com.squallsoft.api.dominios.PedidoEntity;
import com.squallsoft.api.service.BurnLogService;
import com.squallsoft.api.service.PedidoService;

@Controller()
@RequestMapping("/site")
public class MainController {
	 
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private BurnLogService burnLogService;
	
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model) {
		
		return "home.login.tiles";
	}
	
	@RequestMapping(value="/index", method = RequestMethod.GET)	
	public String  index(Model model) {		
		Long qtdPedidos = 0L;
		Long qtdSolicitada = 0L;
		Long qtdCancel  = 0L;
		Long qtdBurns   = 0L;
		Long qtdSuccess = 0L;
		Long qtdFail    = 0L;
		Double total	= 0.0;
		
		Long qtdGeralCancel  = 0L;
		Long qtdGeralSuccess = 0L;
		Long qtdGeralFail    = 0L;
		Double totalGeral	 = 0.0;
		
		List<String> lines = new ArrayList<>();
		
		int temp = 0;
		// Obtem pedidos do mes
		List<PedidoEntity> listaPedidos = pedidoService.FindByData(4, 2021);
		
		// Obtem quant. de pedidos do mes
		qtdPedidos = (long) listaPedidos.size();
		
		// lista pedido por pedido para o obtem todos os logs
		for(PedidoEntity pedido : listaPedidos) {
			// obtem lista de lobs do pedido
			List<BurnLog> listaBurnLogs = burnLogService.findByPedido(pedido.getId());
			
			// soma quant. de logs do pedido
			qtdBurns += listaBurnLogs.size();
			
			qtdSolicitada += pedido.getQuant();
			
			temp = 0;
			qtdSuccess = 0L;
			qtdFail = 0L;
			for(BurnLog log : listaBurnLogs) {
				if(log.getLog().contains("Success!")) {
					qtdSuccess++;
					
				}else {
					qtdFail++;
					
				}
				temp++;
			}	
			qtdCancel = (long) (pedido.getQuant() - temp);
			total = (qtdSuccess * 20.0);
			
			String line = String.format(
					"Pedido id %03d "
				  + "qtd %02d "
				  + "cancelado(s) %02d "
				  + "com falha(s) %02d "
				  + "sucesso %02d "
				  + "valor R$ %s.", 
				  pedido.getId(), 
				  pedido.getQuant(), 
				  qtdCancel, 
				  qtdFail,
				  qtdSuccess,
				  total);	
			
			lines.add(line);
			
			qtdGeralCancel  += qtdCancel;
			qtdGeralSuccess += qtdSuccess;
			qtdGeralFail    += qtdFail;
			totalGeral	 	+= total;
			
		}		
		String line = String.format(
				"Núm pedidos feito(s) %03d "
			  + "qtd solictada %03d "
			  + "gravação cancel %02d "
			  + "qtd gravada %03d "			  
			  + "gravação erros %02d "
			  + "sucessoTotal %02d "
			  + "valor final R$ %s.", 
			  qtdPedidos, 
			  qtdSolicitada,
			  qtdGeralCancel,
			  qtdBurns, 
			  qtdGeralFail,
			  qtdGeralSuccess,
			  totalGeral);	
		
		lines.add(line);
		
		model.addAttribute("lista", lines);
		/*model.addAttribute("qtdBurns", qtdBurns);		
		model.addAttribute("qtdSuccess", qtdSuccess);		
		model.addAttribute("qtdFail", qtdFail);	
		model.addAttribute("qtdCancel", qtdFail);	
		model.addAttribute("total", total);	*/
		
        return "Index.html"; 
	} 
}
