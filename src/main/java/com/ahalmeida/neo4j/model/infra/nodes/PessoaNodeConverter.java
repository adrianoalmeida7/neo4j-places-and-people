package com.ahalmeida.neo4j.model.infra.nodes;

import org.neo4j.graphdb.Node;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.model.Pessoa;

@Component
@RequestScoped
public class PessoaNodeConverter implements NodeConverter<Pessoa> {

	@Override
	public Pessoa fromNode(Node node) {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(node.getId());
		pessoa.setNome((String)node.getProperty("nome"));
		
		return pessoa;
	}

}
