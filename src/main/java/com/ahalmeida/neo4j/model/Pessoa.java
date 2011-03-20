package com.ahalmeida.neo4j.model;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class Pessoa {

	private long id;
	private String nome;

	public Pessoa() {
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void buildNode(EmbeddedGraphDatabase db) {
		Node node = db.createNode();
		node.setProperty("nome", this.nome);
		Relationship relationship = node.createRelationshipTo(db.getReferenceNode(), Relationships.TEM_TIPO);
		relationship.setProperty("tipo", Pessoa.class.getName());
	}

}
