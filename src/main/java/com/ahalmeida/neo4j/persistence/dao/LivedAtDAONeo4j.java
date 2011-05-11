package com.ahalmeida.neo4j.persistence.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.ioc.Component;

import com.ahalmeida.neo4j.model.LivedAt;
import com.ahalmeida.neo4j.model.Person;
import com.ahalmeida.neo4j.model.Place;
import com.ahalmeida.neo4j.model.Relationships;
import com.ahalmeida.neo4j.model.Travel;
import com.ahalmeida.neo4j.model.infra.nodes.PersonNodeConverter;
import com.ahalmeida.neo4j.model.infra.nodes.PlaceNodeConverter;

@Component
public class LivedAtDAONeo4j implements LivedAtDAO {
	private final EmbeddedGraphDatabase db;
	private final PersonNodeConverter personConverter;
	private final PlaceNodeConverter placeConverter;
	
	public LivedAtDAONeo4j(EmbeddedGraphDatabase db, PlaceNodeConverter placeConverter, PersonNodeConverter personConverter) {
		this.db = db;
		this.placeConverter = placeConverter;
		this.personConverter = personConverter;
	}
	
	@Override
	public void save(LivedAt livedAt) {
		Node person = db.getNodeById(livedAt.getPerson().getId());
		Node place = db.getNodeById(livedAt.getPlace().getId());
		
		Relationship r = person.createRelationshipTo(place, Relationships.LIVED_AT);
		r.setProperty("startingAt", livedAt.getStartingAt().getTimeInMillis());
		r.setProperty("until", livedAt.getUntil().getTimeInMillis());
	}
	
	@Override
	public List<LivedAt> livedWhere(Person person) {
		Node personNode = db.getNodeById(person.getId());

		List<LivedAt> history = new ArrayList<LivedAt>();
		Iterable<Relationship> relationships = personNode.getRelationships(Relationships.LIVED_AT, Direction.OUTGOING);
		
		for (Relationship relationship : relationships) {
			Node placeNode = relationship.getEndNode();
			LivedAt lived = mountLivedAt(placeNode, personNode, relationship);
			history.add(lived);
		}
		
		return history;
	}
	
	@Override
	public List<LivedAt> whoLivedAt(Place place) {
		Node placeNode = db.getNodeById(place.getId());

		List<LivedAt> history = new ArrayList<LivedAt>();
		Iterable<Relationship> relationships = placeNode.getRelationships(Relationships.LIVED_AT, Direction.INCOMING);
		
		for (Relationship relationship : relationships) {
			Node personNode = relationship.getStartNode();
			LivedAt lived = mountLivedAt(placeNode, personNode, relationship);
			history.add(lived);
		}
		
		return history;
	}

	private LivedAt mountLivedAt(Node placeNode, Node personNode, Relationship relationship) {
		LivedAt travel = new LivedAt();
		travel.setPlace(placeConverter.fromNode(placeNode));
		travel.setPerson(personConverter.fromNode(personNode));
		Calendar startingAt = Calendar.getInstance();
		startingAt.setTimeInMillis((Long)relationship.getProperty("startingAt"));
		travel.setStartingAt(startingAt);
		Calendar until = Calendar.getInstance();
		until.setTimeInMillis((Long)relationship.getProperty("until"));
		travel.setUntil(until);
		return travel;
	}
}
