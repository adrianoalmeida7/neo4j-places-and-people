package com.ahalmeida.neo4j.persistence.dao;

import java.util.List;

import com.ahalmeida.neo4j.model.Person;

public interface PersonDAO {
	List<Person> all();

	void remove(long id);

	Person findById(long id);
	
	void save(Person person);

	void update(Person person);

	public abstract List<Person> whoTraveledToTheSamePlacesThan(Person p);
}
