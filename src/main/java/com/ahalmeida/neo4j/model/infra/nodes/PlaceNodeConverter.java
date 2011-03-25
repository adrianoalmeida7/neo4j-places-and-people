package com.ahalmeida.neo4j.model.infra.nodes;

import org.neo4j.graphdb.Node;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.model.Place;

@Component
@RequestScoped
public class PlaceNodeConverter implements NodeConverter<Place>{

	@Override
	public Place fromNode(Node node) {
		Place place = new Place();
		place.setId(node.getId());
		place.setCity((String)node.getProperty("city"));
		place.setCountry((String)node.getProperty("country"));
		
		return place;
	}

}
