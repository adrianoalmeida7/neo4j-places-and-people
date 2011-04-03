package com.ahalmeida.neo4j.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.Traversal;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.model.Place;
import com.ahalmeida.neo4j.model.Relationships;
import com.ahalmeida.neo4j.model.infra.nodes.PlaceNodeConverter;
import com.ahalmeida.neo4j.persistence.neo.IndexTypes;
import com.ahalmeida.neo4j.persistence.neo.Neo4JNodeExcluder;

@RequestScoped
@Component
public class PlaceDAONeo4j implements PlaceDAO {

	private final EmbeddedGraphDatabase db;
	private final Neo4JNodeExcluder excluder;


	private final PlaceNodeConverter placeConverter;
	private final IndexManager index;

	public PlaceDAONeo4j(EmbeddedGraphDatabase db, IndexManager index,
			Neo4JNodeExcluder excluder, PlaceNodeConverter placeConverter) {
		this.db = db;
		this.index = index;
		this.excluder = excluder;
		this.placeConverter = placeConverter;
	}

	@Override
	public List<Place> all() {
		//relying on strings for such thing is not a good idea.
		Index<Node> typeIndex = index.forNodes(IndexTypes.PLACES.indexName());
		IndexHits<Node> nodes = typeIndex.get("type", Place.class.getName());

		List<Place> lista = new ArrayList<Place>();
		for (Node node : nodes) {
			lista.add(placeConverter.fromNode(node));
		}
		return lista;
	}

	@Override
	public void remove(long id) {
		excluder.exclude(id);
	}

	@Override
	public Place findById(long id) {
		Node node = db.getNodeById(id);
		return placeConverter.fromNode(node);
	}

	public void save(Place lugar) {
		Node node = db.createNode();
		node.setProperty("city", lugar.getCity());
		node.setProperty("country", lugar.getCountry());
		node.setProperty("type", Place.class.getName());
		Index<Node> placeIndex = index.forNodes(IndexTypes.PLACES.indexName());
		placeIndex.add(node, "type", Place.class.getName());
	}

	@Override
	public void update(Place lugar) {
		Node node = db.getNodeById(lugar.getId());
		node.setProperty("city", lugar.getCity());
		node.setProperty("country", lugar.getCountry());
	}

	@Override
	public List<Place> alsoVisitedFrom(Place place) {
		Node node = db.getNodeById(place.getId());
		List<Place> lugares = new ArrayList<Place>();
		Iterable<Node> nodes = Traversal.description()
				.evaluator(Evaluators.atDepth(2))
				.relationships(Relationships.TRAVELED_TO, Direction.BOTH)
				.traverse(node).nodes();
		
		for (Node res : nodes) {
			lugares.add(placeConverter.fromNode(res));
		}
		return lugares;
	}

}
