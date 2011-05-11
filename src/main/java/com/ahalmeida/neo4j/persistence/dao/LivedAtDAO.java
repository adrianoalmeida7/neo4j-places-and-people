package com.ahalmeida.neo4j.persistence.dao;

import java.util.List;

import com.ahalmeida.neo4j.model.LivedAt;
import com.ahalmeida.neo4j.model.Person;
import com.ahalmeida.neo4j.model.Place;

public interface LivedAtDAO {
	void save(LivedAt livedAt);

	List<LivedAt> livedWhere(Person person);

	List<LivedAt> whoLivedAt(Place place);
}
