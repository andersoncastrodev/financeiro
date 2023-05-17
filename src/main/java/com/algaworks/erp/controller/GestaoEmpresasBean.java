package com.algaworks.erp.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.algaworks.erp.model.Empresa;
import com.algaworks.erp.model.RamoAtividade;
import com.algaworks.erp.model.TipoEmpresa;
import com.algaworks.erp.repository.Empresas;
import com.algaworks.erp.repository.RamoAtividades;
import com.algaworks.erp.service.CadastroEmpresaService;
import com.algaworks.erp.util.FacesMessages;

@Named
@ViewScoped
public class GestaoEmpresasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Empresas empresas;
	
	@Inject
	private FacesMessages messages;
	
	@Inject
	private CadastroEmpresaService cadastroEmpresaService;
	
	@Inject
	private RamoAtividades ramoAtividades;
	
    private Converter ramoAtividadeConverter;
	
	private List<Empresa> listaEmpresas;
	
	private String termoPesquisa;
	
	private Empresa empresa;
	

	
	@PostConstruct
	public void init() {
		todasEmpresas();
	}

	public void pesquisar() {
		listaEmpresas = empresas.pesquisar(termoPesquisa);
		
		if(listaEmpresas.isEmpty()) {
			messages.info("Sua consulta não retorno registros.");
		}
	}

	//Cria uma instancia nova para receber os dados
	public void prepararNovaEmpresa() {
		empresa = new Empresa();
	}
	
	//Prepara para uma nova edicao
	public void prepararEdicao() {
		ramoAtividadeConverter = new RamoAtividadeConverter(Arrays.asList(empresa.getRamoAtividade()));

	}
	
	private boolean jaHouvePesquisa() {
		return termoPesquisa != null && !" ".equals(termoPesquisa);
	}
	
	public void salvar() {
		cadastroEmpresaService.salvar(empresa);
		
		if(listaEmpresas.isEmpty()) {
			pesquisar();
		}else {
			todasEmpresas();
		}
		messages.info("Empresa salva com sucesso.");
		
		//Atualizar um elemento na view Usado o JAVA 
		//Aqui estou atualizando a tabelaEmpresa e o campo de Menssagens.
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:empresasDataTable","frm:messages"));
	}
	
	//Excluir a Instancia do objeto empresa que já estar na Memoria.
	public void excluir() {
		cadastroEmpresaService.excluir(empresa);
		empresa = null;
		//Tira da Table na View
		if(listaEmpresas.isEmpty()) {
			pesquisar();
		}else {
			todasEmpresas();
		}
		messages.info("Empresa excluida  com sucesso.");
		
		//Atualizar um elemento na view Usado o JAVA 
		//Aqui estou atualizando a tabelaEmpresa e o campo de Menssagens.
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:empresasDataTable","frm:messages"));
	}
	
	
	public void todasEmpresas(){
		listaEmpresas = empresas.todas(); 
	}
	
	public List<RamoAtividade> completarRamoAtividade(String terno){
		List<RamoAtividade> listaRamoAtividades = ramoAtividades.pesquisar(terno);
		
		ramoAtividadeConverter = new RamoAtividadeConverter(listaRamoAtividades);
		
		return listaRamoAtividades;
	}
	
	public List<Empresa> getListaEmpresas(){
		return listaEmpresas;
	}
	

	//Get e Set do atributo termoPesquisa
	public String getTermoPesquisa() {
		return termoPesquisa;
	}
	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}
	
	//Retorna para view o Enum com opcao de empresas
	public TipoEmpresa[] getTipoEmpresas() {
		return TipoEmpresa.values();
	}
	
	//Get para usar o getRamoAtividadeConverte
	public Converter getRamoAtividadeConverter() {
		return ramoAtividadeConverter;
	}
	
	//Get para acessar a atributo Empresa na view
	public Empresa getEmpresa() {
		return empresa;
	}
	
	//Set para acessar a atributo Empresa na view
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	//Para saber se a empresa foi selecionada
	public boolean getEmpresaSelecionada() {
		return empresa != null && empresa.getId() != null;
	}
	
}
