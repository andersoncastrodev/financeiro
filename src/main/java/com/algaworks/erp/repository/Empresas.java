package com.algaworks.erp.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import com.algaworks.erp.model.Empresa;



public class Empresas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Empresas() {
		
	}
	public Empresas(EntityManager manager) {
		this.manager = manager;
	}
	
	public Empresa porId(Long id) {	
		return manager.find(Empresa.class, id);
	}
	
	public List<Empresa> pesquisar(String nome) {	
		//Consulta não tipada
		//Query query = manager.createQuery("from Empresa where nomeFantasia like :nomeFantasia"); 
		
		//Consulta Tipada, O resultado é o mesmo só deixa de exibir o warn perigo na IDE.
	    TypedQuery<Empresa> query = manager.createQuery("from Empresa where razaoSocial like :razaoSocial", Empresa.class);
		query.setParameter("razaoSocial", nome + "%");
		return query.getResultList();
	}
	
	public List<Empresa> todas() {	
		return manager.createQuery("from Empresa",Empresa.class).getResultList();
	}
	
	
	public Empresa guardar(Empresa empresa) {
		return manager.merge(empresa);
	}
	
	public void remover(Empresa empresa) {
		//Faz a busca primeiro por codigo pra depois deletar.
		empresa = porId(empresa.getId());
		manager.remove(empresa);
	}
}
