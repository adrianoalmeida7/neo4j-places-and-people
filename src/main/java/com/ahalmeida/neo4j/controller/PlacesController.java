package com.ahalmeida.neo4j.controller;

import java.util.List;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.ahalmeida.neo4j.model.LivedAt;
import com.ahalmeida.neo4j.model.Place;
import com.ahalmeida.neo4j.model.Travel;
import com.ahalmeida.neo4j.persistence.dao.LivedAtDAO;
import com.ahalmeida.neo4j.persistence.dao.PlaceDAO;
import com.ahalmeida.neo4j.persistence.dao.TravelDAO;

@Resource
public class PlacesController {
	private final Result result;
	private PlaceDAO placeDao;
	private final TravelDAO travelDAO;
	private final LivedAtDAO livedAtDAO;

	public PlacesController(PlaceDAO dao, TravelDAO travelDAO,LivedAtDAO livedAtDAO, Result result) {
		this.placeDao = dao;
		this.travelDAO = travelDAO;
		this.livedAtDAO = livedAtDAO; 
		this.result = result;
	}
		
	@Path("/place/new")
	public void form() {
	}

	@Path("/place/{id}")
	@Get
	public void show(long id) {
		Place place = placeDao.findById(id);
		List<Travel> travels = travelDAO.whoTraveledTo(place);
		List<LivedAt> livedHistory = livedAtDAO.whoLivedAt(place);
		List<Place> placesAlsoTraveled = placeDao.alsoTraveledAs(place);
		List<Place> alsoPassedThroughAs = placeDao.alsoPassedThroughAs(place);
		
		result.include("place", place);
		result.include("travels", travels);
		result.include("livedHistory", livedHistory);
		result.include("placesAlsoTraveled", placesAlsoTraveled);
		result.include("alsoPassedThroughAs", alsoPassedThroughAs);
	}
	
	@Path("/place/edit/{id}")
	public Place edit(long id) {
		return placeDao.findById(id);
	}

	@Put
	@Path("place")
	public void update(Place place) {
		placeDao.update(place);
		result.redirectTo(PlacesController.class).list();
	}
	
	
	@Post
	@Path("/place")
	public void create(Place place) {
		placeDao.save(place);
		result.redirectTo(PlacesController.class).list();
	}
	
	@Delete
	@Path("/place/{id}")
	public void delete(long id) {
		placeDao.remove(id);
		result.redirectTo(PlacesController.class).list();
	}
	
	@Get
	@Path("/places")
	public List<Place> list() {
		return placeDao.all();
	}
}
