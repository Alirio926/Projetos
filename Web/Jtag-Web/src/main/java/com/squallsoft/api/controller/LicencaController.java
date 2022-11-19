package com.squallsoft.api.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.squallsoft.api.dominios.Role;
import com.squallsoft.api.dominios.User;
import com.squallsoft.api.dominios.licenca.Cliente;
import com.squallsoft.api.dominios.licenca.Licenca;
import com.squallsoft.api.repositorios.RepositorioCliente;
import com.squallsoft.api.service.LicencaService;
import com.squallsoft.api.service.UserService;

@Controller()
@RequestMapping("/pages/licencas")
public class LicencaController {

	@Autowired
	private RepositorioCliente repCliente;
	@Autowired
	private LicencaService licenseService;
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
	
	public Boolean isSameLogedUser(String name) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        
        if(name.equalsIgnoreCase(user.getUserName()))
        	return Boolean.TRUE;
        
        return Boolean.FALSE;
	}
	
	/**
	 * [ADMIN] Form para cadastro de nova licença.
	 * @param model
	 * @param clienteId
	 * @return
	 */
	@RolesAllowed("ADMIN")
	@RequestMapping(value = "/adicionar/{clienteId}", method = RequestMethod.GET)
	public String novaLicenca(Model model, @PathVariable("clienteId") Long clienteId) {
		//if(!isAdmin()) return "";
		
		Licenca lic = new Licenca();
		Cliente c = repCliente.findById(clienteId).get();
				
		lic.setClienteId(c.getId());
		
		model.addAttribute("licenca", lic);
		model.addAttribute("nomeCliente", c.getNome());
		
		return "licenca.adicionar.tiles";
	}
	
	/**
	 * [ADMIN] Cadastra uma nova licença.
	 * @param licenca
	 * @param result
	 * @param model
	 * @return
	 */
	@RolesAllowed("ADMIN")
	@RequestMapping(value = "/adicionar", method = RequestMethod.POST)
	public String adicionar(@ModelAttribute("licenca") @Valid Licenca licenca, BindingResult result, Model model) {
		//if(!isAdmin()) return "";
				
		if(result.hasErrors()){
			model.addAttribute("error","Erro, observe os campos abaixo");
			return "licenca.adicionar.tiles";
		}

		Cliente cliente = repCliente.findById(licenca.getClienteId()).get();
		Licenca lic = licenseService.obterLicencaDisponivel(cliente);
		if(lic != null) {
			return "redirect:/pages/clientes/listar";
		}
		
		licenca.setDataAtivacao(LocalDate.now());
		
		licenseService.save(licenca);
		return "redirect:/pages/clientes/listar";
	}
	
	/**
	 * [ADMIN] Lista licenças de um cliente com paginação.
	 * @param pathVariablesMap
	 * @param req
	 * @return
	 */
	@RolesAllowed("ADMIN")
	@RequestMapping(value = {"/listar/{type}", "findby/{id}"}, method = RequestMethod.GET)
	public String listByCliente(@PathVariable Map<String, String> pathVariablesMap, HttpServletRequest req) {
		//if(!isAdmin()) return "";
		
		PagedListHolder<Licenca> licencaList = null;
		String type = pathVariablesMap.get("type");
		Long id = Long.decode(pathVariablesMap.get("id"));
		
		Optional<Cliente> cliente = repCliente.findById(id);
				
		if(null == type) {
			licencaList = new PagedListHolder<>();
			licencaList.setSource(licenseService.listarLicencaPeloCliente(cliente.get()));
			licencaList.setPageSize(2);
			
			req.getSession().setAttribute("licencaList", licencaList);
		}else if("next".equals(type)){
			licencaList = (PagedListHolder<Licenca>) req.getSession().getAttribute("licencaList");	  
			licencaList.nextPage();
		}else if("prev".equals(type)) {
			licencaList = (PagedListHolder<Licenca>) req.getSession().getAttribute("licencaList");
			licencaList.previousPage();	          
		}else {
			licencaList = (PagedListHolder<Licenca>) req.getSession().getAttribute("licencaList");	            
            int pageNum = Integer.parseInt(type);	            
            licencaList.setPage(pageNum);
		}
		
		req.getSession().setAttribute("cliente", cliente.get().getNome());
		req.getSession().setAttribute("cliId", cliente.get().getId());
		return "licenca.listar.tiles";
	}
	
	/**
	 * [ADMIN] Lista todas as licenças usando paginação.
	 * @param pathVariablesMap
	 * @param req
	 * @return
	 */
	@RolesAllowed("ADMIN")
	@RequestMapping(value = {"/listarAll/{type}", "/listarAll"}, method = RequestMethod.GET)
	public String listTodos(@PathVariable Map<String, String> pathVariablesMap, HttpServletRequest req, Model model) {
		//if(!isAdmin()) return "";
		
		PagedListHolder<Licenca> licencaList = null;
		String type = pathVariablesMap.get("type");
		
		if(null == type) {
			List<Licenca> lstLic = licenseService.listarTodos();
			
			licencaList = new PagedListHolder<>();
			licencaList.setSource(lstLic);
			licencaList.setPageSize(10);
			
			List<String> lstClientes = new ArrayList<>();
			
			Long id;
			for(Licenca lic : lstLic) {
				id = lic.getClienteId();
				lstClientes.add(repCliente.findById(id).get().getNome());
			}
			
			req.getSession().setAttribute("lstClientes", lstClientes);
			req.getSession().setAttribute("licencaList", licencaList);
		}else if("next".equals(type)){
			licencaList = (PagedListHolder<Licenca>) req.getSession().getAttribute("licencaList");	  
			licencaList.nextPage();
		}else if("prev".equals(type)) {
			licencaList = (PagedListHolder<Licenca>) req.getSession().getAttribute("licencaList");
			licencaList.previousPage();	          
		}else {
			licencaList = (PagedListHolder<Licenca>) req.getSession().getAttribute("licencaList");	            
            int pageNum = Integer.parseInt(type);	            
            licencaList.setPage(pageNum);
		}
				
		return "licenca.listarAll.tiles";
	}
	
	/**
	 * [CLIENTE] Lista todas as licenças de um cliente com paginação.
	 * @param pathVariablesMap
	 * @param req
	 * @return
	 */
	@RolesAllowed("CLIENTE")
	@RequestMapping(value = {"/listarByCliente", "/listarByCliente/{type}"}, method = RequestMethod.GET)
	public String listarByCliente(@PathVariable Map<String, String> pathVariablesMap, HttpServletRequest req) {
		PagedListHolder<Licenca> licencaList = null;
		//String user = pathVariablesMap.get("user");		
		String type = pathVariablesMap.get("type");
		Cliente cliente = null;
		
		// Esta tentando ver a licença de outro cliente. Pode isso Arnaldo?
		//if(!isSameLogedUser(user)) 
		//	return "redirect: /index";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        
		if(user != null) {
			cliente = repCliente.findByUser(user);
						
			//
			if(null == type) {
				licencaList = new PagedListHolder<>();
				licencaList.setSource(licenseService.listarLicencaPeloCliente(cliente));
				licencaList.setPageSize(10);
				
				req.getSession().setAttribute("licencaList", licencaList);
			}else if("next".equals(type)){
				licencaList = (PagedListHolder<Licenca>) req.getSession().getAttribute("licencaList");	  
				licencaList.nextPage();
			}else if("prev".equals(type)) {
				licencaList = (PagedListHolder<Licenca>) req.getSession().getAttribute("licencaList");
				licencaList.previousPage();	          
			}else {
				licencaList = (PagedListHolder<Licenca>) req.getSession().getAttribute("licencaList");	            
	            int pageNum = Integer.parseInt(type);	            
	            licencaList.setPage(pageNum);
			}
			
			return "licenca.listarByCliente.tiles";
			//				
			
		}
		
		return "home.login.tiles";
	}
}
