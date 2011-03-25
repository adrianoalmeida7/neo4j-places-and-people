package com.ahalmeida.neo4j.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.index.IndexHits;
import org.neo4j.index.lucene.LuceneIndexService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.Traversal;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.model.Pessoa;
import com.ahalmeida.neo4j.model.Relationships;
import com.ahalmeida.neo4j.model.infra.nodes.PessoaNodeConverter;
import com.ahalmeida.neo4j.persistence.neo.Neo4JNodeExcluder;

@RequestScoped
@Component
public class PessoaDAONeo4j implements PessoaDAO {

	private final EmbeddedGraphDatabase db;
	private final Neo4JNodeExcluder excluder;
	private final LuceneIndexService index;
	private final PessoaNodeConverter pessoaConverter;

	public PessoaDAONeo4j(EmbeddedGraphDatabase db, LuceneIndexService index,
			Neo4JNodeExcluder excluder, PessoaNodeConverter pessoaConverter) {
		this.db = db;
		this.index = index;
		this.excluder = excluder;
		this.pessoaConverter = pessoaConverter;
	}

	@Override
	public List<Pessoa> todos() {
		IndexHits<Node> nodes = index.getNodes("tipo", Pessoa.class.getName());
		List<Pessoa> lista = new ArrayList<Pessoa>();
		for (Node node : nodes) {
			lista.add(pessoaConverter.fromNode(node));
		}
		return lista;
	}

	@Override
	public void remove(long id) {
		excluder.exclude(id, Relationships.START);
	}

	@Override
	public Pessoa findById(long id) {
		Node node = db.getNodeById(id);
		return pessoaConverter.fromNode(node);
	}

	public void salva(Pessoa p) {
		Node node = db.createNode();
		Node referenceNode = db.getReferenceNode();
		referenceNode.createRelationshipTo(node, Relationships.START);
		node.setProperty("nome", p.getNome());
		node.setProperty("tipo", Pessoa.class.getName());

		index.index(node, "tipo", Pessoa.class.getName());
	}

	@Override
	public void atualiza(Pessoa pessoa) {
		Node node = db.getNodeById(pessoa.getId());
		node.setProperty("nome", pessoa.getNome());
	}

	@Override
	public List<Pessoa> quemViajouProsMesmosLugaresQue(Pessoa p) {
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		Node me = db.getNodeById(p.getId());
		Iterable<Node> nodes = Traversal.description()
				.evaluator(Evaluators.excludeStartPosition())
				.evaluator(Evaluators.atDepth(2))
				.relationships(Relationships.VIAJOU_PARA, Direction.BOTH)
				.traverse(me).nodes();

		for (Node node : nodes) {
			pessoas.add(pessoaConverter.fromNode(node));
		}
		
		return pessoas;
	}
}
