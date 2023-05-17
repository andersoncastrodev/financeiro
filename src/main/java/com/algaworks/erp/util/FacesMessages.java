package com.algaworks.erp.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/*
 * Classe Responsavel por Customizar as messagens enviar para VIEW do USUARIO
 */
public class FacesMessages implements Serializable {

	private static final long serialVersionUID = 1L;

	//Prepara a MSG
	private void add(String msg, FacesMessage.Severity severity ) {
		FacesMessage facesMessage = new FacesMessage(msg);
		facesMessage.setSeverity(severity);
		
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	//Usa a MSG
	public void info(String msg) {
		add(msg, FacesMessage.SEVERITY_INFO);
	}
	
}
