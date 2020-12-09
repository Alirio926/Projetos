package br.com.squallsoft.blog.dominios;

import java.beans.PropertyEditorSupport;

public class PerfilEditorSupport extends PropertyEditorSupport {
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException{
		if(text.equalsIgnoreCase("ADMIN")) {
			super.setValue(Perfil.ADMIN);
		}else if(text.equalsIgnoreCase("AUTOR")) {
			super.setValue(Perfil.AUTOR);
		}else{
			super.setValue(Perfil.LEITOR);
		}
	}

}
