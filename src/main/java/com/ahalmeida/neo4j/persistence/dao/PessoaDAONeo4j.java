package com.ahalmeida.neo4j.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.evaluators.TodosDeUmTipoEvaluator;
import com.ahalmeida.neo4j.model.Pessoa;
import com.ahalmeida.neo4j.model.Relationships;
import com.ahalmeida.neo4j.persistence.neo.Neo4JNodeExcluder;

@RequestScoped
@Component
public class PessoaDAONeo4j implements PessoaDAO {

	private final EmbeddedGraphDatabase db;
	private final Neo4JNodeExcluder excluder;

	public PessoaDAONeo4j(EmbeddedGraphDatabase db, Neo4JNodeExcluder excluder) {
		this.db = db;
		this.excluder = excluder;
	}
	
	@Override
	public List<Pessoa> todos() {
		Node root = db.getReferenceNode();
		
		Traverser traverse = root.traverse(Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				new TodosDeUmTipoEvaluator(Pessoa.class), Relationships.TEM_TIPO,
				Direction.INCOMING);

		List<Pessoa> lista = new ArrayList<Pessoa>();
		for (Node node : traverse) {
			lista.add(fromNode(node));
		}
		return lista;
	}
	
	@Override
	public void remove(long id) {
		excluder.exclude(id, Relationships.TEM_TIPO);
	}
	
	@Override
	public Pessoa findById(long id) {
		Node node = db.getNodeById(id);
		return fromNode(node);
	}

	private Pessoa fromNode(Node node) {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(node.getId());
		pessoa.setNome((String)node.getProperty("nome"));
		
		return pessoa;
	}
	
	public void salva(Pessoa p) {
		Node node = db.createNode();
		node.setProperty("nome", p.getNome());
		Relationship relationship = node.createRelationshipTo(db.getReferenceNode(), Relationships.TEM_TIPO);
		relationship.setProperty("tipo", Pessoa.class.getName());
	}

	@Override
	public void atualiza(Pessoa pessoa) {
		Node node = db.getNodeById(pessoa.getId());
		node.setProperty("nome", pessoa.getNome());		
	}
	
}
