package com.ahalmeida.neo4j.persistence.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.model.Place;
import com.ahalmeida.neo4j.model.Person;
import com.ahalmeida.neo4j.model.Relationships;
import com.ahalmeida.neo4j.model.Travel;
import com.ahalmeida.neo4j.model.infra.nodes.PlaceNodeConverter;
import com.ahalmeida.neo4j.model.infra.nodes.PersonNodeConverter;

@Component
@RequestScoped
public class TravelDAONeo4J implements TravelDAO {
	
	private final EmbeddedGraphDatabase db;
	private final PersonNodeConverter personConverter;
	private final PlaceNodeConverter placeConverter;
	
	public TravelDAONeo4J(EmbeddedGraphDatabase db, PlaceNodeConverter placeConverter, PersonNodeConverter personConverter) {
		this.db = db;
		this.placeConverter = placeConverter;
		this.personConverter = personConverter;
	}
	
	@Override
	public void save(Travel travel) {
		Node person = db.getNodeById(travel.getPerson().getId());
		Node place = db.getNodeById(travel.getPlace().getId());
		
		Relationship r = person.createRelationshipTo(place, Relationships.TRAVELED_TO);
		r.setProperty("date", travel.getDate().getTimeInMillis());
	}
	
	//TODO O paraOndeViajou e o quemViajouPara sao mto parecidos. Talvez possam ser simplificados
	@Override
	public List<Travel> traveledToWhere(Person person) {
		Node personNode = db.getNodeById(person.getId());

		List<Travel> travels = new ArrayList<Travel>();
		Iterable<Relationship> relationships = personNode.getRelationships(Relationships.TRAVELED_TO, Direction.OUTGOING);
		
		for (Relationship relationship : relationships) {
			Node placeNode = relationship.getEndNode();
			Travel travel = mountTravel(placeNode, personNode, relationship);
			travels.add(travel);
		}
		
		return travels;
	}
	
	@Override
	public List<Travel> whoTraveledTo(Place place) {
		Node placeNode = db.getNodeById(place.getId());

		List<Travel> travels = new ArrayList<Travel>();
		Iterable<Relationship> relationships = placeNode.getRelationships(Relationships.TRAVELED_TO, Direction.INCOMING);
		
		for (Relationship relationship : relationships) {
			Node personNode = relationship.getStartNode();
			Travel travel = mountTravel(placeNode, personNode, relationship);
			travels.add(travel);
		}
		
		return travels;
	}

	private Travel mountTravel(Node placeNode, Node personNode, Relationship relationship) {
		Travel travel = new Travel();
		travel.setPlace(placeConverter.fromNode(placeNode));
		travel.setPerson(personConverter.fromNode(personNode));
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis((Long)relationship.getProperty("date"));
		travel.setDate(date);
		return travel;
	}

}
