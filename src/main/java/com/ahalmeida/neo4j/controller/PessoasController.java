package com.ahalmeida.neo4j.controller;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.ahalmeida.neo4j.model.Pessoa;
import com.ahalmeida.neo4j.model.Viagem;
import com.ahalmeida.neo4j.persistence.dao.PessoaDAO;
import com.ahalmeida.neo4j.persistence.dao.ViagemDAO;

@Resource
public class PessoasController {
	private final Logger LOG = Logger.getLogger(PessoasController.class);
	private final Result result;
	private final PessoaDAO dao;
	private final ViagemDAO viagemDAO;

	public PessoasController(PessoaDAO dao, ViagemDAO viagemDAO, Result result) {
		this.dao = dao;
		this.viagemDAO = viagemDAO;
		this.result = result;
	}

	@Path("/pessoa/new")
	public void novo() {
	}

	@Path("/pessoa/edit/{id}")
	public Pessoa edit(long id) {
		return dao.findById(id);
	}

	@Path("/pessoa/{id}")
	@Get
	public void show(long id) {
		Pessoa pessoa = dao.findById(id);
		List<Viagem> viagens = viagemDAO.paraOndeViajou(pessoa);
		result.include("pessoa", pessoa);
		result.include("viagens", viagens);
	}
	
	@Put
	@Path("pessoa")
	public void update(Pessoa pessoa) {
		dao.atualiza(pessoa);
		result.redirectTo(PessoasController.class).lista();
	}

	@Post
	@Path("/pessoa")
	public void create(Pessoa pessoa) {
		LOG.info("Deveria estar criando a pessoa: " + pessoa.getNome());
		dao.salva(pessoa);
		result.redirectTo(PessoasController.class).lista();
	}

	@Delete
	@Path("/pessoa/{id}")
	public void delete(long id) {
		dao.remove(id);
		result.redirectTo(PessoasController.class).lista();
	}

	@Get
	@Path("/pessoas")
	public List<Pessoa> lista() {
		return dao.todos();
	}
}
