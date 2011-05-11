package com.ahalmeida.neo4j.model;

import org.neo4j.graphdb.RelationshipType;

public enum Relationships implements RelationshipType {
	TRAVELED_TO, START, LIVED_AT;
}
