package br.com.squallsoft.blog.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.squallsoft.blog.dominios.Avatar;
import br.com.squallsoft.blog.service.AvatarService;

@Controller
@RequestMapping("avatar")
public class AvaterController {

	private static final Logger LOG = Logger.getLogger(AvaterController.class);
	@Autowired
	private AvatarService avatarService;
	
	@RequestMapping(value="/load/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> loadAvatar(@PathVariable("id") Long id){
		Avatar avatar = avatarService.findById(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(avatar.getTipo()));
		
		InputStream in = new ByteArrayInputStream(avatar.getAvatar());
		try {
			return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} catch (IOException e) {
			LOG.error("Ocorreu um erro ao recuperar o Avatar!"+ e.getMessage());
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				LOG.error("Ocorreu um erro ao fechar o stream do arquivo."+ e.getMessage());
			}
		}
		return null;
	}
}
