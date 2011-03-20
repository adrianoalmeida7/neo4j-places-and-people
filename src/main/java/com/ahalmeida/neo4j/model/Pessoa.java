package com.ahalmeida.neo4j.model;


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
	
}
