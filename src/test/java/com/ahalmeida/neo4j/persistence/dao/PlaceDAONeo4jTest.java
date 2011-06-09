package com.ahalmeida.neo4j.persistence.dao;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ahalmeida.neo4j.components.Neo4JIndexer;
import com.ahalmeida.neo4j.model.LivedAt;
import com.ahalmeida.neo4j.model.Person;
import com.ahalmeida.neo4j.model.Place;
import com.ahalmeida.neo4j.model.Travel;
import com.ahalmeida.neo4j.model.infra.nodes.PersonNodeConverter;
import com.ahalmeida.neo4j.model.infra.nodes.PlaceNodeConverter;


public class PlaceDAONeo4jTest extends Neo4JIntegration {

	private PlaceNodeConverter placeConverter = new PlaceNodeConverter();
	private PersonNodeConverter personConverter = new PersonNodeConverter();
	
	@Test
	public void should_retrieve_places_who_were_traveled_and_lived_by_people_who_went_to_a_given_place(){
		PlaceDAONeo4j placeDAO = new PlaceDAONeo4j(getDb(), new Neo4JIndexer(getDb()).getInstance(), null, placeConverter);
		Place toronto = new Place("Toronto", "Canada");
		placeDAO.save(toronto);
		Place tokyo = new Place("Tokyo", "Japan");
		placeDAO.save(tokyo);
		Place rome = new Place("Rome", "Italy");
		placeDAO.save(rome);
		Place paris = new Place("Paris", "France");
		placeDAO.save(paris);
		
		PersonDAONeo4j personDAO = new PersonDAONeo4j(getDb(), new Neo4JIndexer(getDb()).getInstance(), null, personConverter);
		Person adriano = new Person("Adriano");
		personDAO.save(adriano);
		
		TravelDAONeo4J travelDAO = new TravelDAONeo4J(getDb(), placeConverter, personConverter);
		Travel travelToRome = new Travel(adriano, rome, Calendar.getInstance());
		travelDAO.save(travelToRome);
		Travel travelToToronto = new Travel(adriano, toronto, Calendar.getInstance());
		travelDAO.save(travelToToronto);
		
		LivedAtDAONeo4j livedAtDAO = new LivedAtDAONeo4j(getDb(), placeConverter, personConverter);
		LivedAt livedAtParis = new LivedAt(adriano, paris, Calendar.getInstance(), Calendar.getInstance());
		livedAtDAO.save(livedAtParis);
		LivedAt livedAtTokyo = new LivedAt(adriano, tokyo, Calendar.getInstance(), Calendar.getInstance());
		livedAtDAO.save(livedAtTokyo);
		
		List<Place> places = placeDAO.alsoPassedThroughAs(tokyo);

		Assert.assertEquals(3, places.size());
		Assert.assertTrue(places.contains(rome));
		Assert.assertTrue(places.contains(paris));
		Assert.assertTrue(places.contains(toronto));
		Assert.assertFalse(places.contains(tokyo));
	}
	
	@Test
	public void should_retrieve_places_who_were_traveled_by_people_who_traveled_to_a_given_place(){
		PlaceDAONeo4j placeDAO = new PlaceDAONeo4j(getDb(), new Neo4JIndexer(getDb()).getInstance(), null, placeConverter);
		Place toronto = new Place("Toronto", "Canada");
		placeDAO.save(toronto);
		Place rome = new Place("Rome", "Italy");
		placeDAO.save(rome);
		
		PersonDAONeo4j personDAO = new PersonDAONeo4j(getDb(), new Neo4JIndexer(getDb()).getInstance(), null, personConverter);
		Person adriano = new Person("Adriano");
		personDAO.save(adriano);
		
		TravelDAONeo4J travelDAO = new TravelDAONeo4J(getDb(), placeConverter, personConverter);
		Travel travelToRome = new Travel(adriano, rome, Calendar.getInstance());
		travelDAO.save(travelToRome);
		Travel travelToToronto = new Travel(adriano, toronto, Calendar.getInstance());
		travelDAO.save(travelToToronto);
		
		List<Place> places = placeDAO.alsoTraveledAs(rome);
		Assert.assertEquals(1, places.size());
		Assert.assertTrue(places.contains(toronto));
	}
}
