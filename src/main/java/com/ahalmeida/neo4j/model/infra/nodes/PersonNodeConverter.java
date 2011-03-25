package com.ahalmeida.neo4j.model.infra.nodes;

import org.neo4j.graphdb.Node;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.model.Person;

@Component
@RequestScoped
public class PersonNodeConverter implements NodeConverter<Person> {

	@Override
	public Person fromNode(Node node) {
		Person person = new Person();
		person.setId(node.getId());
		person.setName((String)node.getProperty("name"));
		
		return person;
	}

}
