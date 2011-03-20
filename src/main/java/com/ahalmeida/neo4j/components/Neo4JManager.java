package com.ahalmeida.neo4j.components;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import com.ahalmeida.neo4j.model.Pessoa;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class Neo4JManager implements ComponentFactory<EmbeddedGraphDatabase> {
	private static final String DATABASE_DIR = "/Users/ahalmeida/Projects/java/neo4j-vraptor/graphdb";
	private Logger LOG = Logger.getLogger(Neo4JManager.class);
	private EmbeddedGraphDatabase db;

	@PostConstruct
	public void initialize() {
		LOG.info("Starting up the graph database at " + DATABASE_DIR);
		db = new EmbeddedGraphDatabase(DATABASE_DIR);
	}

	public EmbeddedGraphDatabase getInstance() {
		return this.db;
	}

	@PreDestroy
	public void destroy() {
		LOG.info("Shutting down the database at " + DATABASE_DIR);
		db.shutdown();
	}
	

	public static void main(String[] args) {
		EmbeddedGraphDatabase db = new EmbeddedGraphDatabase(DATABASE_DIR);
		Transaction tx = db.beginTx();
		
		try {
			Iterable<Node> nodes = db.getAllNodes();
			for (Node node : nodes) {
				for (String prop: node.getPropertyKeys()) {
					System.out.println(node.getProperty(prop));
				}
			}
			tx.success();
		} finally {
			tx.finish();
			db.shutdown();
		}
	}
}
