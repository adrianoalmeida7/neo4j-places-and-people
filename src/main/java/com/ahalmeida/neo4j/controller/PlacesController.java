package com.ahalmeida.neo4j.controller;

import java.util.List;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.ahalmeida.neo4j.model.Place;
import com.ahalmeida.neo4j.model.Travel;
import com.ahalmeida.neo4j.persistence.dao.PlaceDAO;
import com.ahalmeida.neo4j.persistence.dao.TravelDAO;

@Resource
public class PlacesController {
	private final Result result;
	private PlaceDAO dao;
	private final TravelDAO viagemDAO;

	public PlacesController(PlaceDAO dao, TravelDAO viagemDAO, Result result) {
		this.dao = dao;
		this.viagemDAO = viagemDAO; 
		this.result = result;
	}
		
	@Path("/place/new")
	public void form() {
	}

	@Path("/place/{id}")
	@Get
	public void show(long id) {
		Place place = dao.findById(id);
		List<Travel> travels = viagemDAO.whoTraveledTo(place);
		List<Place> placesAlsoVisited = dao.alsoVisitedFrom(place);
		result.include("place", place);
		result.include("travels", travels);
		result.include("placesAlsoVisited", placesAlsoVisited);
	}
	
	@Path("/place/edit/{id}")
	public Place edit(long id) {
		return dao.findById(id);
	}

	@Put
	@Path("place")
	public void update(Place place) {
		dao.update(place);
		result.redirectTo(PlacesController.class).list();
	}
	
	
	@Post
	@Path("/place")
	public void create(Place place) {
		dao.save(place);
		result.redirectTo(PlacesController.class).list();
	}
	
	@Delete
	@Path("/place/{id}")
	public void delete(long id) {
		dao.remove(id);
		result.redirectTo(PlacesController.class).list();
	}
	
	@Get
	@Path("/places")
	public List<Place> list() {
		return dao.all();
	}
}
