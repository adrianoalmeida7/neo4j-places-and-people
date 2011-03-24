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

import com.ahalmeida.neo4j.model.Lugar;
import com.ahalmeida.neo4j.model.Viagem;
import com.ahalmeida.neo4j.persistence.dao.LugarDAO;
import com.ahalmeida.neo4j.persistence.dao.ViagemDAO;

@Resource
public class LugaresController {
	private final Logger LOG = Logger.getLogger(LugaresController.class);
	private final Result result;
	private LugarDAO dao;
	private final ViagemDAO viagemDAO;

	public LugaresController(LugarDAO dao, ViagemDAO viagemDAO, Result result) {
		this.dao = dao;
		this.viagemDAO = viagemDAO; 
		this.result = result;
	}
		
	@Path("/lugar/new")
	public void novo() {
	}

	@Path("/lugar/{id}")
	@Get
	public void show(long id) {
		Lugar lugar = dao.findById(id);
		List<Viagem> viagens = viagemDAO.quemViajouPara(lugar);
		List<Lugar> lugaresTambemVisitados = dao.tambemVisitaramAPartirDe(lugar);
		result.include("lugar", lugar);
		result.include("viagens", viagens);
		result.include("lugaresTambemVisitados", lugaresTambemVisitados);
	}
	
	@Path("/lugar/edit/{id}")
	public Lugar edit(long id) {
		return dao.findById(id);
	}

	@Put
	@Path("lugar")
	public void update(Lugar lugar) {
		dao.atualiza(lugar);
		result.redirectTo(LugaresController.class).lista();
	}
	
	
	@Post
	@Path("/lugar")
	public void create(Lugar lugar) {
		LOG.info("Deveria estar criando o lugar:" + lugar.getCidade());
		dao.salva(lugar);
		result.redirectTo(LugaresController.class).lista();
	}
	
	@Delete
	@Path("/lugar/{id}")
	public void delete(long id) {
		dao.remove(id);
		result.redirectTo(LugaresController.class).lista();
	}
	
	@Get
	@Path("/lugares")
	public List<Lugar> lista() {
		return dao.todos();
	}
}
