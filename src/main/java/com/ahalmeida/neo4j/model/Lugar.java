package com.ahalmeida.neo4j.model;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class Lugar {

	private long id;
	private String cidade;
	private String pais;

	public Lugar() {
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
}
