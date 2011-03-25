package com.ahalmeida.neo4j.persistence.dao;

import java.util.List;

import com.ahalmeida.neo4j.model.Place;

public interface PlaceDAO {
	List<Place> all();

	void remove(long id);

	Place findById(long id);
	
	void save(Place place);

	void update(Place place);
	
	List<Place> alsoVisitedFrom(Place place);
}
