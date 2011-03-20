package com.ahalmeida.neo4j.model.infra.nodes;

import org.neo4j.graphdb.Node;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.model.Lugar;

@Component
@RequestScoped
public class LugarNodeConverter implements NodeConverter<Lugar>{

	@Override
	public Lugar fromNode(Node node) {
		Lugar lugar = new Lugar();
		lugar.setId(node.getId());
		lugar.setCidade((String)node.getProperty("cidade"));
		lugar.setPais((String)node.getProperty("pais"));
		
		return lugar;
	}

}
