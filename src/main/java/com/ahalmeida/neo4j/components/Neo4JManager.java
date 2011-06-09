package com.ahalmeida.neo4j.components;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class Neo4JManager implements ComponentFactory<EmbeddedGraphDatabase> {
	
	private final String databaseDirectory;
	private Logger LOG = Logger.getLogger(Neo4JManager.class);
	private EmbeddedGraphDatabase db;

	public Neo4JManager(ServletContext ctx) {
		this.databaseDirectory = ctx.getInitParameter("neo4j_database_dir");
	}
	
	@PostConstruct
	public void initialize() {
		LOG.info("Starting up the graph database at " + databaseDirectory);
		db = new EmbeddedGraphDatabase(databaseDirectory);
	}

	public EmbeddedGraphDatabase getInstance() {
		return this.db;
	}

	@PreDestroy
	public void destroy() {
		LOG.info("Shutting down the database at " + databaseDirectory);
		db.shutdown();
	}
}
