package com.ahalmeida.neo4j.model;


public class Person {

	private long id;
	private String name;

	public Person() {
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String nome) {
		this.name = nome;
	}
	
}
