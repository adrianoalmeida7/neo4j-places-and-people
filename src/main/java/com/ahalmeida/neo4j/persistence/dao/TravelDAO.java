package com.ahalmeida.neo4j.persistence.dao;

import java.util.List;

import com.ahalmeida.neo4j.model.Place;
import com.ahalmeida.neo4j.model.Person;
import com.ahalmeida.neo4j.model.Travel;

public interface TravelDAO {

	void save(Travel travel);

	List<Travel> traveledToWhere(Person person);

	public abstract List<Travel> whoTraveledTo(Place place);

}
