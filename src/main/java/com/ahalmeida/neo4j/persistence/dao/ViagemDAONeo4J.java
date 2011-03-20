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
import com.ahalmeida.neo4j.model.infra.nodes.LugarNodeConverter;
import com.ahalmeida.neo4j.model.infra.nodes.PessoaNodeConverter;

@Component
@RequestScoped
public class ViagemDAONeo4J implements ViagemDAO {
	
	private final EmbeddedGraphDatabase db;
	private final PessoaNodeConverter pessoaConverter;
	private final LugarNodeConverter lugarConverter;
	
	public ViagemDAONeo4J(EmbeddedGraphDatabase db, LugarNodeConverter lugarConverter, PessoaNodeConverter pessoaConverter) {
		this.db = db;
		this.lugarConverter = lugarConverter;
		this.pessoaConverter = pessoaConverter;
	}
	
	@Override
	public void salva(Viagem viagem) {
		Node pessoa = db.getNodeById(viagem.getPessoa().getId());
		Node lugar = db.getNodeById(viagem.getLugar().getId());
		
		Relationship r = pessoa.createRelationshipTo(lugar, Relationships.VIAJOU_PARA);
		r.setProperty("data", viagem.getData().getTimeInMillis());
	}
	
	//TODO O paraOndeViajou e o quemViajouPara sao mto parecidos. Talvez possam ser simplificados
	@Override
	public List<Viagem> paraOndeViajou(Pessoa pessoa) {
		Node nodePessoa = db.getNodeById(pessoa.getId());

		List<Viagem> viagens = new ArrayList<Viagem>();
		Iterable<Relationship> relationships = nodePessoa.getRelationships(Relationships.VIAJOU_PARA, Direction.OUTGOING);
		
		for (Relationship relationship : relationships) {
			Node nodeLugar = relationship.getEndNode();
			Viagem viagem = montaViagem(nodeLugar, nodePessoa, relationship);
			viagens.add(viagem);
		}
		
		return viagens;
	}
	
	@Override
	public List<Viagem> quemViajouPara(Lugar lugar) {
		Node nodeLugar = db.getNodeById(lugar.getId());

		List<Viagem> viagens = new ArrayList<Viagem>();
		Iterable<Relationship> relationships = nodeLugar.getRelationships(Relationships.VIAJOU_PARA, Direction.INCOMING);
		
		for (Relationship relationship : relationships) {
			Node nodePessoa = relationship.getStartNode();
			Viagem viagem = montaViagem(nodeLugar, nodePessoa, relationship);
			viagens.add(viagem);
		}
		
		return viagens;
	}

	private Viagem montaViagem(Node nodeLugar, Node nodePessoa, Relationship relationship) {
		Viagem viagem = new Viagem();
		viagem.setLugar(lugarConverter.fromNode(nodeLugar));
		viagem.setPessoa(pessoaConverter.fromNode(nodePessoa));
		Calendar data = Calendar.getInstance();
		data.setTimeInMillis((Long)relationship.getProperty("data"));
		viagem.setData(data);
		return viagem;
	}

}
