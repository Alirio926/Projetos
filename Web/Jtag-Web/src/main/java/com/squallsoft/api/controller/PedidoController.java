package com.squallsoft.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.squallsoft.api.dominios.BurnLog;
import com.squallsoft.api.dominios.PedidoEntity;
import com.squallsoft.api.dominios.Role;
import com.squallsoft.api.dominios.User;
import com.squallsoft.api.dominios.licenca.Cliente;
import com.squallsoft.api.dominios.licenca.Licenca;
import com.squallsoft.api.repositorios.RepositorioCliente;
import com.squallsoft.api.service.BurnLogService;
import com.squallsoft.api.service.LicencaService;
import com.squallsoft.api.service.PedidoService;
import com.squallsoft.api.service.UserService;

@Controller()
@RequestMapping("/pages/pedido")
public class PedidoController {

	@Autowired
	private BurnLogService burnService;
	@Autowired
	private LicencaService licenseService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private RepositorioCliente repCliente;
	@Autowired
	private UserService userService;

	
	public Boolean isAdmin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        
        for(Role r : user.getRoles()) 
        	if(r.getRole().equalsIgnoreCase("ROLE_ADMIN"))
        		return Boolean.TRUE;
        
        
        return Boolean.FALSE;
	}

	/**
	 * [ANY] Lista pedidos de uma licença
	 * @param pathVariablesMap
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/listar/{type}", "/findby/{licId}"}, method = RequestMethod.GET)
	public String listarByLicenca(@PathVariable Map<String, String> pathVariablesMap, HttpServletRequest req){
		PagedListHolder<PedidoEntity> lstPedidos = null;
		List<PedidoEntity> lpe = null;
		String type = pathVariablesMap.get("type");
		Long licId = Long.decode(pathVariablesMap.get("licId"));
				
		Licenca lic = licenseService.findById(licId);	
		
		// Pode ver licença de outros usuarios? Pode isso Arnaldo.
		Optional<Cliente> c = repCliente.findById(lic.getClienteId());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Boolean canKeep = false;
		
		// se for admin, passa direto, se for cliente, verifica
		if(!isAdmin()) {
	        User user = userService.findUserByUserName(auth.getName());
			if(c.isPresent()) {
				if(c.get().getUser().getUserName().equalsIgnoreCase(user.getUserName())) {
					canKeep = true;
				}
			}
			if(!canKeep) 
				return "home.bemvindo.tiles";
		}
		
		lpe = lic.getPedidos();
		
		Optional<Cliente> cliente = repCliente.findById(lic.getClienteId());
		
		String nome;
		String mCode = lic.getMachineCode();
		
		if(!cliente.isPresent()) 
			nome = "";
		else
			nome = cliente.get().getNome();
		
		// envia lista vazia, para evitar erro de null point exception
		if(lpe == null) lpe = new ArrayList<>();
		
		
		req.getSession().setAttribute("userName", nome);
		req.getSession().setAttribute("mCode", mCode);
		
		if(type == null) {
			lstPedidos = new PagedListHolder<>();
			lstPedidos.setSource(lpe);
			lstPedidos.setPageSize(10);
			
			req.getSession().setAttribute("listaPedidos", lstPedidos);
		}else if("next".equals(type)){
			lstPedidos = (PagedListHolder<PedidoEntity>) req.getSession().getAttribute("listaPedidos");	  
			lstPedidos.nextPage();
		}else if("prev".equals(type)) {
			lstPedidos = (PagedListHolder<PedidoEntity>) req.getSession().getAttribute("listaPedidos");
			lstPedidos.previousPage();	          
		}else {
			lstPedidos = (PagedListHolder<PedidoEntity>) req.getSession().getAttribute("listaPedidos");	            
            int pageNum = Integer.parseInt(type);	            
            lstPedidos.setPage(pageNum);
		}
		
		return "pedido.listar.tiles";
	}
	
	/**
	 * [ADMIN] Lista todos os pedidos de todos os clientes
	 * @param pathVariablesMap
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RolesAllowed("ROLE_ADMIN")
	@RequestMapping(value={"/listarAll/{type}", "/listarAll"}, method = RequestMethod.GET)
	public String listarTodos(@PathVariable Map<String, String> pathVariablesMap, HttpServletRequest req){
		PagedListHolder<PedidoEntity> lstPedidos = null;
		
		String type = pathVariablesMap.get("type");
				
		List<BurnLog> Logs;
		List<List<BurnLog>> listLogs = new ArrayList<>();
		
		if(type == null) {
			List<String> lstClientes = new ArrayList<>();
			List<PedidoEntity> listPedidoE = pedidoService.listarTodos();
			for(PedidoEntity pe : listPedidoE) {
				Cliente c = repCliente.findById(pe.getLicenca().getClienteId()).get();
				lstClientes.add(c.getNome());
				
				Logs = burnService.findByPedido(pe.getId());
				listLogs.add(Logs);
			}
			
			lstPedidos = new PagedListHolder<>();
			lstPedidos.setSource(listPedidoE);
			lstPedidos.setPageSize(10);
			
			req.getSession().setAttribute("listaLogs", listLogs);
			req.getSession().setAttribute("lstClientes", lstClientes);
			req.getSession().setAttribute("listaPedidos", lstPedidos);
		}else if("next".equals(type)){
			
			lstPedidos = (PagedListHolder<PedidoEntity>) req.getSession().getAttribute("listaPedidos");	  
			lstPedidos.nextPage();
			
			List<String> lstClientes = new ArrayList<>();
			for(PedidoEntity pe : lstPedidos.getPageList()) {
				Cliente c = repCliente.findById(pe.getLicenca().getClienteId()).get();
				lstClientes.add(c.getNome());
				
				Logs = burnService.findByPedido(pe.getId());
				listLogs.add(Logs);
			}
			
			req.getSession().setAttribute("lstClientes", lstClientes);
			req.getSession().setAttribute("listaLogs", listLogs);			
		}else if("prev".equals(type)) {
			lstPedidos = (PagedListHolder<PedidoEntity>) req.getSession().getAttribute("listaPedidos");
			lstPedidos.previousPage();	

			List<String> lstClientes = new ArrayList<>();
			for(PedidoEntity pe : lstPedidos.getPageList()) {
				Cliente c = repCliente.findById(pe.getLicenca().getClienteId()).get();
				lstClientes.add(c.getNome());
				
				Logs = burnService.findByPedido(pe.getId());
				listLogs.add(Logs);
			}
			
			req.getSession().setAttribute("lstClientes", lstClientes);
			req.getSession().setAttribute("listaLogs", listLogs);
		}else {
			lstPedidos = (PagedListHolder<PedidoEntity>) req.getSession().getAttribute("listaPedidos");	            
            int pageNum = Integer.parseInt(type);	            
            lstPedidos.setPage(pageNum);
            
            List<String> lstClientes = new ArrayList<>();
			for(PedidoEntity pe : lstPedidos.getPageList()) {
				Cliente c = repCliente.findById(pe.getLicenca().getClienteId()).get();
				lstClientes.add(c.getNome());
				
				Logs = burnService.findByPedido(pe.getId());
				listLogs.add(Logs);
			}
			
			req.getSession().setAttribute("listaLogs", listLogs);
		}
		
		return "pedido.listarAll.tiles";
	}
	
	
	
	 /* [ANY] Lista pedidos de uma licença por cliente
	 * @param pathVariablesMap
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/listarByCliente/{type}", "/listarByCliente"}, method = RequestMethod.GET)
	public String listarByCliente(@PathVariable Map<String, String> pathVariablesMap, HttpServletRequest req){
		PagedListHolder<PedidoEntity> lstPedidos = null;
		List<PedidoEntity> lpe = null;
		Cliente cliente = null;
		String type = pathVariablesMap.get("type");
						
		// Pode ver licença de outros usuarios? Pode isso Arnaldo.
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
		
        //List<List<BurnLog>> listBurns = new ArrayList<>();
		if(user != null) {			
			if(type == null) {
				cliente = repCliente.findByUser(user);
				
				lpe = pedidoService.listarByCliente(cliente.getId());
								
				lstPedidos = new PagedListHolder<>();				
				lstPedidos.setSource(lpe);
				lstPedidos.setPageSize(10);
				
				//req.getSession().setAttribute("listaLogs", listBurns);
				req.getSession().setAttribute("listaPedidos", lstPedidos);
			}else if("next".equals(type)){
				lstPedidos = (PagedListHolder<PedidoEntity>) req.getSession().getAttribute("listaPedidos");	  
				lstPedidos.nextPage();
			}else if("prev".equals(type)) {
				lstPedidos = (PagedListHolder<PedidoEntity>) req.getSession().getAttribute("listaPedidos");
				lstPedidos.previousPage();	          
			}else {
				lstPedidos = (PagedListHolder<PedidoEntity>) req.getSession().getAttribute("listaPedidos");	            
	            int pageNum = Integer.parseInt(type);	            
	            lstPedidos.setPage(pageNum);
			}	
			
			
		}
				
		return "pedido.listarByCliente.tiles";
	}
	
	@RequestMapping(value={"/listarByMonth{type}", "/listarByMonth"}, method = RequestMethod.POST)
	public String listarByMonth(@PathVariable Map<String, String> pathVariablesMap, @RequestParam(name="month", defaultValue="") String month, HttpServletRequest req) {
		month = org.apache.commons.lang3.StringEscapeUtils.unescapeJava(month);

		PagedListHolder<PedidoEntity> lstPedidos = null;
		List<PedidoEntity> lpe = null;
		Cliente cliente = null;
		String type = pathVariablesMap.get("type");
				
		List<BurnLog> Logs;
		List<List<BurnLog>> listLogs = new ArrayList<>();
		
		// Pode ver licença de outros usuarios? Pode isso Arnaldo.
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
		
        System.out.println("Mês: " + month + " type: " + type);
        if(user != null) {			
			if(type == null) {
				//cliente = repCliente.findByUser(user);
				int mes, ano;
				
		        if(month != null) {
		        	StringTokenizer st = new StringTokenizer(month, "-");
		        	ano = Integer.decode(st.nextToken());
		        	mes = Integer.decode(st.nextToken());
		        }else {
		        	ano = Calendar.YEAR;
		        	mes = Calendar.MONTH;
		        }
		        
				lpe = pedidoService.findPedidoByClienteUserName(user, mes, ano);
				
				lstPedidos = new PagedListHolder<>();				
				lstPedidos.setSource(lpe);
				lstPedidos.setPageSize(10);
				
				List<String> lstClientes = new ArrayList<>();
				for(PedidoEntity pe : lstPedidos.getPageList()) {
					Cliente c = repCliente.findById(pe.getLicenca().getClienteId()).get();
					lstClientes.add(c.getNome());
					
					Logs = burnService.findByPedido(pe.getId());
					listLogs.add(Logs);
				}
				
				req.getSession().setAttribute("lstClientes", lstClientes);
				req.getSession().setAttribute("listaLogs", listLogs);	
				
				req.getSession().setAttribute("listaPedidos", lstPedidos);
			}else if("next".equals(type)){
				lstPedidos = (PagedListHolder<PedidoEntity>) req.getSession().getAttribute("listaPedidos");	  
				lstPedidos.nextPage();
				
				List<String> lstClientes = new ArrayList<>();
				for(PedidoEntity pe : lstPedidos.getPageList()) {
					Cliente c = repCliente.findById(pe.getLicenca().getClienteId()).get();
					lstClientes.add(c.getNome());
					
					Logs = burnService.findByPedido(pe.getId());
					listLogs.add(Logs);
				}
				
				req.getSession().setAttribute("lstClientes", lstClientes);
				req.getSession().setAttribute("listaLogs", listLogs);		
			}else if("prev".equals(type)) {
				lstPedidos = (PagedListHolder<PedidoEntity>) req.getSession().getAttribute("listaPedidos");
				lstPedidos.previousPage();	
				
				List<String> lstClientes = new ArrayList<>();
				for(PedidoEntity pe : lstPedidos.getPageList()) {
					Cliente c = repCliente.findById(pe.getLicenca().getClienteId()).get();
					lstClientes.add(c.getNome());
					
					Logs = burnService.findByPedido(pe.getId());
					listLogs.add(Logs);
				}
				
				req.getSession().setAttribute("lstClientes", lstClientes);
				req.getSession().setAttribute("listaLogs", listLogs);		
			}else {
				lstPedidos = (PagedListHolder<PedidoEntity>) req.getSession().getAttribute("listaPedidos");	            
	            int pageNum = Integer.parseInt(type);	            
	            lstPedidos.setPage(pageNum);
	            
	            List<String> lstClientes = new ArrayList<>();
				for(PedidoEntity pe : lstPedidos.getPageList()) {
					Cliente c = repCliente.findById(pe.getLicenca().getClienteId()).get();
					lstClientes.add(c.getNome());
					
					Logs = burnService.findByPedido(pe.getId());
					listLogs.add(Logs);
				}
				
				req.getSession().setAttribute("lstClientes", lstClientes);
				req.getSession().setAttribute("listaLogs", listLogs);		
			}
		}
		
		return "pedido.listarByMonth.tiles";
	}
	
	
	
	@RequestMapping(value="/burnID", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody List<String> getLogByBurnId(@RequestParam(name="id", defaultValue="")Long id) {

		BurnLog l = burnService.findById(id);

		List<String> log = new ArrayList<>();
		
		log.add(l.getLog());
		
		return log;
	}
	
	@RequestMapping(value="/porID", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody String getLogByPedidoId(@RequestParam(name="id", defaultValue="")Long id) {
		String log = "Ainda não esta pronto.";
		//PedidoEntity pedido =id pedidoService.findPedido(id);
		List<String> l = new ArrayList<>();
		
		return log;
	}
}
