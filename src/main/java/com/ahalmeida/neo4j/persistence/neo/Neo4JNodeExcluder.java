package com.ahalmeida.neo4j.persistence.neo;

import org.neo4j.graphdb.RelationshipType;

public interface Neo4JNodeExcluder {

	public abstract void exclude(long id, RelationshipType... types);

}