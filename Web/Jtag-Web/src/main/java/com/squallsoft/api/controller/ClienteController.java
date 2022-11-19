package com.squallsoft.api.controller;

import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.squallsoft.api.dominios.licenca.Cliente;
import com.squallsoft.api.repositorios.RepositorioCliente;

@Controller()
@RequestMapping("/pages/clientes")
public class ClienteController {

	@Autowired
	private RepositorioCliente repCliente;
		
	/**
	 * [ADMIN] Form cadastrar novo cliente
	 * @param model
	 * @return
	 */
	@RolesAllowed("ADMIN")
	@RequestMapping(value = "/adicionar", method = RequestMethod.GET)
	public String novoCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		
		return "cliente.adicionar.tiles";
	}

	/**
	 * [ADMIN] POST form cadastrar novo cliente
	 * @param cliente
	 * @param result
	 * @param model
	 * @return
	 */
	@RolesAllowed("ADMIN")
	@RequestMapping(value="/adicionar", method=RequestMethod.POST)
	public String adicionar(@ModelAttribute("cliente") @Valid Cliente cliente, BindingResult result, Model model) {
		if(result.hasErrors()){
			return "cliente.adicionar.tiles";		
		}
		
		// valid cliente
		if(false){
			model.addAttribute("error", "CPF já cadastrado em nome de "+"meu nome aqui");			
			return "cliente.adicionar.tiles";
		}
		
		// Persist
		repCliente.saveAndFlush(cliente);
		
		// redirect
		return "redirect:/pages/clientes/adicionar";
	}
	
	/**
	 * [ADMIN] Lista todos os clientes
	 * @param pathVariablesMap
	 * @param req
	 * @return
	 */
	@RolesAllowed("ADMIN")
	@RequestMapping(value = {"/listar/{type}","/listar"}, method = RequestMethod.GET)
	public String lstClientes(@PathVariable Map<String, String> pathVariablesMap, HttpServletRequest req) {
		PagedListHolder<Cliente> clienteList = null;
		String type = pathVariablesMap.get("type");
		 		
		if(null == type) {
			clienteList = new PagedListHolder<>();
			clienteList.setSource(repCliente.findAll());
			clienteList.setPageSize(10);
			
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
	}
}
