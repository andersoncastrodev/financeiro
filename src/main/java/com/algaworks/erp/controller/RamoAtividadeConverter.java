package com.algaworks.erp.controller;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import com.algaworks.erp.model.RamoAtividade;

//Conversor de Campos de Ramo Atividade para ser usado na busca por AutoComplete.
public class RamoAtividadeConverter implements Converter<Object> {

	private List<RamoAtividade> listaRamoAtividades;
	
	public RamoAtividadeConverter(List<RamoAtividade> listaRamoAtividades) {
		this.listaRamoAtividades = listaRamoAtividades;
	}

	@Override //Usado para setar o valor
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null) {
			return null;
		}
		Long id = Long.valueOf(value);
		
		for(RamoAtividade ramoAtividade : listaRamoAtividades) {
			if (id.equals(ramoAtividade.getId())) {
				return ramoAtividade;	
			}
		}
		return null;
	}

	@Override //Usado para busca
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null) {
			return null;
		}	
		RamoAtividade ramoAtividade = (RamoAtividade) value;
		
		return ramoAtividade.getId().toString();
	}
	
}
