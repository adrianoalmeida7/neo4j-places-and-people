package com.ahalmeida.neo4j.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.ahalmeida.neo4j.model.Viagem;
import com.ahalmeida.neo4j.persistence.dao.LugarDAO;
import com.ahalmeida.neo4j.persistence.dao.PessoaDAO;
import com.ahalmeida.neo4j.persistence.dao.ViagemDAO;

@Resource
public class ViagensController {
	
	private Result result;
	private final LugarDAO lugarDAO;
	private final PessoaDAO pessoaDAO;
	private final ViagemDAO viagemDAO;

	public ViagensController(Result result, LugarDAO lugarDAO, PessoaDAO pessoaDAO, ViagemDAO viagemDAO) {
		this.result = result;
		this.lugarDAO = lugarDAO;
		this.pessoaDAO = pessoaDAO;
		this.viagemDAO = viagemDAO;
	}
	
	@Path("viagem/new")
	public void novo() {
		result.include("pessoas", pessoaDAO.todos());
		result.include("lugares", lugarDAO.todos());
	}
	
	@Post
	@Path("viagem")
	public void create(Viagem viagem) {
		viagemDAO.salva(viagem);
		result.redirectTo(ViagensController.class).novo();
	}
}
