package com.ahalmeida.neo4j.persistence.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.model.Lugar;
import com.ahalmeida.neo4j.model.Pessoa;
import com.ahalmeida.neo4j.model.Relationships;
import com.ahalmeida.neo4j.model.Viagem;

@Component
@RequestScoped
public class ViagemDAONeo4J implements ViagemDAO {
	
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
	
	@Override
	public List<Viagem> paraOndeViajou(Pessoa pessoa) {
		List<Viagem> viagens = new ArrayList<Viagem>();
		Node nodePessoa = db.getNodeById(pessoa.getId());
		Iterable<Relationship> relationships = nodePessoa.getRelationships(Relationships.VIAJOU_PARA, Direction.OUTGOING);
		for (Relationship relationship : relationships) {
			Viagem viagem = montaViagem(pessoa, relationship);
			viagens.add(viagem);
		}
		
		return viagens;
	}

	private Viagem montaViagem(Pessoa pessoa, Relationship relationship) {
		Lugar lugar = new Lugar();
		Node nodeLugar = relationship.getEndNode();
		lugar.setCidade((String)nodeLugar.getProperty("cidade"));
		lugar.setPais((String)nodeLugar.getProperty("pais"));
		lugar.setId(nodeLugar.getId());
		
		Viagem viagem = new Viagem();
		viagem.setLugar(lugar);
		viagem.setPessoa(pessoa);
		Calendar data = Calendar.getInstance();
		data.setTimeInMillis((Long)relationship.getProperty("data"));
		viagem.setData(data);
		return viagem;
	}

}
