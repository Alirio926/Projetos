package br.com.squallsoft.blog.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.squallsoft.blog.dominios.Avatar;
import br.com.squallsoft.blog.repositorios.RepositorioAvatar;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class AvatarService {
	
	private static final Logger LOG = Logger.getLogger(AvatarService.class);
	@Autowired
	private RepositorioAvatar repositorio;
	
	@Transactional(readOnly = false)
	public void saveOrUpdate(Avatar avatar) {
		repositorio.save(avatar);
	}
	
	@Transactional(readOnly = false)
	public Avatar getAvatarByUpload(MultipartFile file) {

		Avatar avatar = new Avatar();
		if(file != null && file.getSize() > 0) {			
			try {
				avatar.setTitulo(file.getOriginalFilename());
				avatar.setTipo(file.getContentType());
				avatar.setAvatar(file.getBytes());
			} catch (IOException e) {
				LOG.error("Ocorreu um erro em Avatar Service "+ e.getMessage());
			}
		}

		return avatar;
	}

	public Avatar findById(Long id) {		
		return repositorio.findById(id).get();
	}
}
