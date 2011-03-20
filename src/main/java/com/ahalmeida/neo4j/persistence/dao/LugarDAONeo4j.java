package com.ahalmeida.neo4j.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.evaluators.TodosDeUmTipoEvaluator;
import com.ahalmeida.neo4j.model.Lugar;
import com.ahalmeida.neo4j.model.Pessoa;
import com.ahalmeida.neo4j.model.Relationships;
import com.ahalmeida.neo4j.persistence.neo.Neo4JNodeExcluder;

@RequestScoped
@Component
public class LugarDAONeo4j implements LugarDAO {
	
	private static final Logger LOGGER = Logger.getLogger(LugarDAONeo4j.class); 

	private final EmbeddedGraphDatabase db;
	private final Neo4JNodeExcluder excluder;

	public LugarDAONeo4j(EmbeddedGraphDatabase db, Neo4JNodeExcluder excluder) {
		this.db = db;
		this.excluder = excluder;
	}
	
	@Override
	public List<Lugar> todos() {
		Node root = db.getReferenceNode();
		
		Traverser traverse = root.traverse(Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				new TodosDeUmTipoEvaluator(Lugar.class), Relationships.TEM_TIPO,
				Direction.INCOMING);

		List<Lugar> lista = new ArrayList<Lugar>();
		for (Node node : traverse) {
			lista.add(fromNode(node));
		}
		return lista;
	}
	
	@Override
	public void remove(long id) {
		excluder.exclude(id, Relationships.TEM_TIPO);
	}
	
	@Override
	public Lugar findById(long id) {
		Node node = db.getNodeById(id);
		return fromNode(node);
	}

	private Lugar fromNode(Node node) {
		Lugar lugar = new Lugar();
		lugar.setId(node.getId());
		lugar.setCidade((String)node.getProperty("cidade"));
		lugar.setPais((String)node.getProperty("pais"));
		
		return lugar;
	}
	
	public void salva(Lugar lugar) {
		LOGGER.info("Salvando o lugar: " + lugar.getCidade());
		Node node = db.createNode();
		node.setProperty("cidade", lugar.getCidade());
		node.setProperty("pais", lugar.getPais());
		Relationship relationship = node.createRelationshipTo(db.getReferenceNode(), Relationships.TEM_TIPO);
		relationship.setProperty("tipo", Lugar.class.getName());
	}

	@Override
	public void atualiza(Lugar lugar) {
		Node node = db.getNodeById(lugar.getId());
		node.setProperty("cidade", lugar.getCidade());		
		node.setProperty("pais", lugar.getPais());		
	}
	
}
