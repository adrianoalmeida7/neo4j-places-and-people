package com.ahalmeida.neo4j.components;

import javax.annotation.PreDestroy;

import org.neo4j.index.lucene.LuceneIndexService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@ApplicationScoped
@Component
public class Neo4JIndexer implements ComponentFactory<LuceneIndexService>{

	private LuceneIndexService index;

	public Neo4JIndexer(EmbeddedGraphDatabase db) {
		index = new LuceneIndexService(db);
	}
	
	@Override
	public LuceneIndexService getInstance() {
		return this.index;
	}
	
	@PreDestroy
	public void destroy() {
		index.shutdown();
	}
}
