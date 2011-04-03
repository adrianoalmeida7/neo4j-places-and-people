package com.ahalmeida.neo4j.components;

import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@ApplicationScoped
@Component
public class Neo4JIndexer implements ComponentFactory<IndexManager>{

	private IndexManager index;

	public Neo4JIndexer(EmbeddedGraphDatabase db) {
		index = db.index();
	}
	
	@Override
	public IndexManager getInstance() {
		return this.index;
	}
	
}
