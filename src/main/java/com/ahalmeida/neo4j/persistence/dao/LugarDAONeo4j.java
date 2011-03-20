package com.ahalmeida.neo4j.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Node;
import org.neo4j.index.IndexHits;
import org.neo4j.index.lucene.LuceneIndexService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.model.Lugar;
import com.ahalmeida.neo4j.model.infra.nodes.LugarNodeConverter;
import com.ahalmeida.neo4j.persistence.neo.Neo4JNodeExcluder;

@RequestScoped
@Component
public class LugarDAONeo4j implements LugarDAO {
	
	private static final Logger LOGGER = Logger.getLogger(LugarDAONeo4j.class); 

	private final EmbeddedGraphDatabase db;
	private final Neo4JNodeExcluder excluder;

	private final LuceneIndexService index;

	private final LugarNodeConverter lugarConverter;

	public LugarDAONeo4j(EmbeddedGraphDatabase db, LuceneIndexService index , Neo4JNodeExcluder excluder, LugarNodeConverter lugarConverter) {
		this.db = db;
		this.index = index;
		this.excluder = excluder;
		this.lugarConverter = lugarConverter;
	}
	
	@Override
	public List<Lugar> todos() {
		IndexHits<Node> nodes = index.getNodes("tipo", Lugar.class.getName());

		List<Lugar> lista = new ArrayList<Lugar>();
		for (Node node : nodes) {
			lista.add(lugarConverter.fromNode(node));
		}
		return lista;
	}
	
	@Override
	public void remove(long id) {
		excluder.exclude(id);
	}
	
	@Override
	public Lugar findById(long id) {
		Node node = db.getNodeById(id);
		return lugarConverter.fromNode(node);
	}

	public void salva(Lugar lugar) {
		LOGGER.info("Salvando o lugar: " + lugar.getCidade());
		Node node = db.createNode();
		node.setProperty("cidade", lugar.getCidade());
		node.setProperty("pais", lugar.getPais());
		node.setProperty("tipo", Lugar.class.getName());
		
		index.index(node, "tipo", Lugar.class.getName());
	}

	@Override
	public void atualiza(Lugar lugar) {
		Node node = db.getNodeById(lugar.getId());
		node.setProperty("cidade", lugar.getCidade());		
		node.setProperty("pais", lugar.getPais());		
	}
	
}
