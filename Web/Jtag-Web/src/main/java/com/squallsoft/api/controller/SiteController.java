package com.squallsoft.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.squallsoft.api.dominios.licenca.Cliente;
import com.squallsoft.api.repositorios.RepositorioCliente;

@Controller()
@RequestMapping("/pages")
public class SiteController {

	
	//https://stackoverflow.com/questions/35957040/how-to-show-custom-404-page-when-user-enters-invalid-url-in-spring-boot-applicat
	
	
	@Autowired
	private RepositorioCliente repCliente;
	
	@RequestMapping("/home") 
	public String home(Model model) {
		return "home.bemvindo.tiles";
	}
	
	
	
	
	
	
	
	/*
	@RequestMapping(value = {"/clientes/listar/{type}","/clientes/listar"}, method = RequestMethod.GET)
	public String lstClientes(@PathVariable Map<String, String> pathVariablesMap, HttpServletRequest req) {
		PagedListHolder<Cliente> clienteList = null;
		String type = pathVariablesMap.get("type");
		 		
		if(null == type) {
			clienteList = new PagedListHolder<>();
			clienteList.setSource(repCliente.findAll());
			clienteList.setPageSize(2);
			
			req.getSession().setAttribute("clienteList", clienteList);
		}else if("next".equals(type)){
			clienteList = (PagedListHolder<Cliente>) req.getSession().getAttribute("clienteList");	  
			clienteList.nextPage();
		}else if("prev".equals(type)) {
			clienteList = (PagedListHolder<Cliente>) req.getSession().getAttribute("clienteList");
			clienteList.previousPage();	          
		}else {
			clienteList = (PagedListHolder<Cliente>) req.getSession().getAttribute("clienteList");	            
            int pageNum = Integer.parseInt(type);	            
            clienteList.setPage(pageNum);
		}
		
		return "cliente.listar.tiles";
	}*/
}
