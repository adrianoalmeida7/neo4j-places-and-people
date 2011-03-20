package com.ahalmeida.neo4j.evaluators;

import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.TraversalPosition;

import com.ahalmeida.neo4j.model.Relationships;

public class TodosDeUmTipoEvaluator implements ReturnableEvaluator {

	private Class<?> clazz;

	public TodosDeUmTipoEvaluator(Class<?> clazz) {
		this.clazz = clazz;
	}

	public boolean isReturnableNode(TraversalPosition position) {
		if (position.notStartNode()) {
			Iterable<Relationship> relationships = position.currentNode()
					.getRelationships(Relationships.TEM_TIPO);
			for (Relationship r : relationships) {
				String tipo = (String) r.getProperty("tipo");
				if (clazz.getName().equals(tipo)) {
					return true;
				}
			}
		}
		return false;
	}

}
