package com.ahalmeida.neo4j.persistence.neo;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class DefaultNeo4JNodeExcluder implements Neo4JNodeExcluder {

	private final EmbeddedGraphDatabase db;

	public DefaultNeo4JNodeExcluder(EmbeddedGraphDatabase db) {
		this.db = db;
	}

	//TODO - Nao precisa receber o types (o proprio node já devolve os relacionamentos existentes)
	@Override
	public void exclude(long id, RelationshipType... types) {
		Node node = db.getNodeById(id);
		for (RelationshipType type : types) {
			Iterable<Relationship> relationships = node.getRelationships(type);
			deleteRelationships(relationships);
		}
		node.delete();
	}

	private void deleteRelationships(Iterable<Relationship> relationships) {
		for (Relationship relationship : relationships) {
			relationship.delete();
		}
	}
}
