package com.ahalmeida.neo4j.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.index.IndexHits;
import org.neo4j.index.lucene.LuceneIndexService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.Traversal;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.model.Person;
import com.ahalmeida.neo4j.model.Relationships;
import com.ahalmeida.neo4j.model.infra.nodes.PersonNodeConverter;
import com.ahalmeida.neo4j.persistence.neo.Neo4JNodeExcluder;

@RequestScoped
@Component
public class PersonDAONeo4j implements PersonDAO {

	private final EmbeddedGraphDatabase db;
	private final Neo4JNodeExcluder excluder;
	private final LuceneIndexService index;
	private final PersonNodeConverter personConverter;

	public PersonDAONeo4j(EmbeddedGraphDatabase db, LuceneIndexService index,
			Neo4JNodeExcluder excluder, PersonNodeConverter personConverter) {
		this.db = db;
		this.index = index;
		this.excluder = excluder;
		this.personConverter = personConverter;
	}

	@Override
	public List<Person> all() {
		IndexHits<Node> nodes = index.getNodes("type", Person.class.getName());
		List<Person> list = new ArrayList<Person>();
		for (Node node : nodes) {
			list.add(personConverter.fromNode(node));
		}
		return list;
	}

	@Override
	public void remove(long id) {
		excluder.exclude(id, Relationships.START);
	}

	@Override
	public Person findById(long id) {
		Node node = db.getNodeById(id);
		return personConverter.fromNode(node);
	}

	public void save(Person p) {
		Node node = db.createNode();
		Node referenceNode = db.getReferenceNode();
		referenceNode.createRelationshipTo(node, Relationships.START);
		node.setProperty("name", p.getName());
		node.setProperty("type", Person.class.getName());

		index.index(node, "type", Person.class.getName());
	}

	@Override
	public void update(Person person) {
		Node node = db.getNodeById(person.getId());
		node.setProperty("name", person.getName());
	}

	@Override
	public List<Person> whoTraveledToTheSamePlacesThan(Person p) {
		List<Person> people = new ArrayList<Person>();
		Node me = db.getNodeById(p.getId());
		Iterable<Node> nodes = Traversal.description()
				.evaluator(Evaluators.excludeStartPosition())
				.evaluator(Evaluators.atDepth(2))
				.relationships(Relationships.TRAVELED_TO, Direction.BOTH)
				.traverse(me).nodes();

		for (Node node : nodes) {
			people.add(personConverter.fromNode(node));
		}
		
		return people;
	}
}
