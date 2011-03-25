package com.ahalmeida.neo4j.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.ahalmeida.neo4j.model.Travel;
import com.ahalmeida.neo4j.persistence.dao.PlaceDAO;
import com.ahalmeida.neo4j.persistence.dao.PersonDAO;
import com.ahalmeida.neo4j.persistence.dao.TravelDAO;

@Resource
public class TravelsController {
	
	private Result result;
	private final PlaceDAO placeDAO;
	private final PersonDAO personDAO;
	private final TravelDAO travelDAO;

	public TravelsController(Result result, PlaceDAO placeDAO, PersonDAO personDAO, TravelDAO travelDAO) {
		this.result = result;
		this.placeDAO = placeDAO;
		this.personDAO = personDAO;
		this.travelDAO = travelDAO;
	}
	
	@Path("travel/new")
	public void form() {
		result.include("persons", personDAO.all());
		result.include("places", placeDAO.all());
	}
	
	@Post
	@Path("travel")
	public void create(Travel travel) {
		travelDAO.save(travel);
		result.redirectTo(TravelsController.class).form();
	}
}
