package com.ahalmeida.neo4j.persistence.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.model.Relationships;
import com.ahalmeida.neo4j.model.Viagem;

@Component
@RequestScoped
public class ViagemDAONeo4J implements ViagemDAO {
	private static final Logger LOGGER = Logger.getLogger(ViagemDAONeo4J.class);
	
	private final EmbeddedGraphDatabase db;
	public ViagemDAONeo4J(EmbeddedGraphDatabase db) {
		this.db = db;
	}
	
	@Override
	public void salva(Viagem viagem) {
		Node pessoa = db.getNodeById(viagem.getPessoa().getId());
		Node lugar = db.getNodeById(viagem.getLugar().getId());
		
		Relationship r = pessoa.createRelationshipTo(lugar, Relationships.VIAJOU_PARA);
		r.setProperty("data", viagem.getData().getTimeInMillis());
	}
	
	public List<Viagem> todos() {
		return null;
	}

}
