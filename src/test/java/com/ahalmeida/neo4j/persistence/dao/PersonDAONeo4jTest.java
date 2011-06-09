package com.ahalmeida.neo4j.persistence.dao;

import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.ahalmeida.neo4j.components.Neo4JIndexer;
import com.ahalmeida.neo4j.model.Person;
import com.ahalmeida.neo4j.model.Place;
import com.ahalmeida.neo4j.model.Travel;
import com.ahalmeida.neo4j.model.infra.nodes.PersonNodeConverter;
import com.ahalmeida.neo4j.model.infra.nodes.PlaceNodeConverter;


public class PersonDAONeo4jTest extends Neo4JIntegration{

	private PlaceNodeConverter placeConverter = new PlaceNodeConverter();
	private PersonNodeConverter personConverter = new PersonNodeConverter();
	
	@Test
	public void should_retrieve_persons_who_traveled_to_the_same_place_as_a_given_person() {
		PlaceDAONeo4j placeDAO = new PlaceDAONeo4j(getDb(), new Neo4JIndexer(getDb()).getInstance(), null, placeConverter);
		Place toronto = new Place("Toronto", "Canada");
		placeDAO.save(toronto);
		Place tokyo = new Place("Tokyo", "Japan");
		placeDAO.save(tokyo);
		Place rome = new Place("Rome", "Italy");
		placeDAO.save(rome);
		
		PersonDAONeo4j personDAO = new PersonDAONeo4j(getDb(), new Neo4JIndexer(getDb()).getInstance(), null, personConverter);
		Person adriano = new Person("Adriano");
		personDAO.save(adriano);		
		Person john = new Person("John Doe");
		personDAO.save(john);
		Person mary = new Person("Mary");
		personDAO.save(mary);
		
		TravelDAONeo4J travelDAO = new TravelDAONeo4J(getDb(), placeConverter, personConverter);
		Travel adrianoTravelToRome = new Travel(adriano, rome, Calendar.getInstance());
		travelDAO.save(adrianoTravelToRome);
		Travel maryTravelToToronto = new Travel(mary, toronto, Calendar.getInstance());
		travelDAO.save(maryTravelToToronto);
		Travel johnTravelToRome= new Travel(john, rome, Calendar.getInstance());
		travelDAO.save(johnTravelToRome);
		Travel johnTravelToTokyo= new Travel(john, tokyo, Calendar.getInstance());
		travelDAO.save(johnTravelToTokyo);
		
		List<Person> result = personDAO.whoTraveledToTheSamePlacesThan(adriano);
		Assert.assertEquals(1, result.size());
		Assert.assertTrue(result.contains(john));
	}
}
