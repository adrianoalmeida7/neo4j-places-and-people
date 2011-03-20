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
import com.ahalmeida.neo4j.persistence.dao.PessoaDAO;

@Resource
public class PessoasController {
	private final Logger LOG = Logger.getLogger(PessoasController.class);
	private final Result result;
	private final PessoaDAO dao;

	public PessoasController(PessoaDAO dao, Result result) {
		this.dao = dao;
		this.result = result;
	}

	@Path("/pessoa/new")
	public void novo() {
	}

	@Path("/pessoa/edit/{id}")
	public Pessoa edit(long id) {
		return dao.findById(id);
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
