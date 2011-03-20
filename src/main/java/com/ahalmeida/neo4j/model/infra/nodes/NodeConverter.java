package com.ahalmeida.neo4j.model.infra.nodes;

import org.neo4j.graphdb.Node;

public interface NodeConverter<T> {
	T fromNode(Node node);
}
