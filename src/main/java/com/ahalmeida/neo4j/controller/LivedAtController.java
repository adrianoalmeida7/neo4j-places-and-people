package com.ahalmeida.neo4j.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.ahalmeida.neo4j.model.LivedAt;
import com.ahalmeida.neo4j.persistence.dao.LivedAtDAO;
import com.ahalmeida.neo4j.persistence.dao.PersonDAO;
import com.ahalmeida.neo4j.persistence.dao.PlaceDAO;

@Resource
public class LivedAtController {
	
	private Result result;
	private final PlaceDAO placeDAO;
	private final PersonDAO personDAO;
	private final LivedAtDAO livedAtDAO;

	public LivedAtController(Result result, PlaceDAO placeDAO, PersonDAO personDAO, LivedAtDAO livedAtDAO) {
		this.result = result;
		this.placeDAO = placeDAO;
		this.personDAO = personDAO;
		this.livedAtDAO = livedAtDAO;
	}
	
	@Path("livedAt/new")
	public void form() {
		result.include("persons", personDAO.all());
		result.include("places", placeDAO.all());
	}
	
	@Post
	@Path("livedAt")
	public void create(LivedAt lived) {
		livedAtDAO.save(lived);
		result.redirectTo(LivedAtController.class).form();
	}
}
