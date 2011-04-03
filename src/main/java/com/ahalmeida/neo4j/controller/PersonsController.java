package com.ahalmeida.neo4j.controller;

import java.util.List;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.ahalmeida.neo4j.model.Person;
import com.ahalmeida.neo4j.model.Travel;
import com.ahalmeida.neo4j.persistence.dao.PersonDAO;
import com.ahalmeida.neo4j.persistence.dao.TravelDAO;

@Resource
public class PersonsController {
	private final Result result;
	private final PersonDAO dao;
	private final TravelDAO travelDAO;

	public PersonsController(PersonDAO dao, TravelDAO travelDAO, Result result) {
		this.dao = dao;
		this.travelDAO = travelDAO;
		this.result = result;
	}

	@Path("/person/new")
	public void form() {
	}

	@Path("/person/filterByNameForm")
	public void filterByNameForm() {
	}
	
	@Path("/person/byName")
	@Get
	public void filterByName(String name) {
		result.include("personList", dao.findByName(name));
		result.use(Results.page()).of(PersonsController.class).list();
	}

	@Path("/person/edit/{id}")
	public Person edit(long id) {
		return dao.findById(id);
	}

	@Path("/person/{id}")
	@Get
	public void show(long id) {
		Person person = dao.findById(id);
		List<Travel> viagens = travelDAO.traveledToWhere(person);
		List<Person> whoTraveledToSamePlaces = dao.whoTraveledToTheSamePlacesThan(person);
		result.include("person", person);
		result.include("travels", viagens);
		result.include("whoTraveledToSamePlaces", whoTraveledToSamePlaces);
	}
	
	@Put
	@Path("person")
	public void update(Person person) {
		dao.update(person);
		result.redirectTo(PersonsController.class).list();
	}

	@Post
	@Path("/person")
	public void create(Person person) {
		dao.save(person);
		result.redirectTo(PersonsController.class).list();
	}

	@Delete
	@Path("/person/{id}")
	public void delete(long id) {
		dao.remove(id);
		result.redirectTo(PersonsController.class).list();
	}

	@Get
	@Path("/persons")
	public List<Person> list() {
		return dao.all();
	}
}
