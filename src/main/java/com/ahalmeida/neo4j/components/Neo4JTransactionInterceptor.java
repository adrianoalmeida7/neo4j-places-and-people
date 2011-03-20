package com.ahalmeida.neo4j.components;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class Neo4JTransactionInterceptor implements Interceptor {

	private final Logger LOG = Logger.getLogger(Neo4JTransactionInterceptor.class); 
	private final EmbeddedGraphDatabase db;

	public Neo4JTransactionInterceptor(EmbeddedGraphDatabase db) {
		this.db = db;
	}

	@Override
	public boolean accepts(ResourceMethod method) {
		return true;
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object object) throws InterceptionException {
		Transaction tx = db.beginTx();
		LOG.info("Opening up a new Transaction for Neo4J");
		try {
			stack.next(method, object);
			tx.success();
			LOG.info("Neo4J Transaction succesfull");
		} finally {
			tx.finish();
			LOG.info("Neo4J Transaction finished");
		}
	}
}
