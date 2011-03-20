package com.ahalmeida.neo4j.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.index.IndexHits;
import org.neo4j.index.lucene.LuceneIndexService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.ahalmeida.neo4j.model.Pessoa;
import com.ahalmeida.neo4j.persistence.neo.Neo4JNodeExcluder;

@RequestScoped
@Component
public class PessoaDAONeo4j implements PessoaDAO {

	private final EmbeddedGraphDatabase db;
	private final Neo4JNodeExcluder excluder;
	private final LuceneIndexService index;

	public PessoaDAONeo4j(EmbeddedGraphDatabase db, LuceneIndexService index, Neo4JNodeExcluder excluder) {
		this.db = db;
		this.index = index;
		this.excluder = excluder;
	}
	
	@Override
	public List<Pessoa> todos() {
		IndexHits<Node> nodes = index.getNodes("tipo", Pessoa.class.getName());
		List<Pessoa> lista = new ArrayList<Pessoa>();
		for (Node node : nodes) {
			lista.add(fromNode(node));
		}
		return lista;
	}
	
	@Override
	public void remove(long id) {
		excluder.exclude(id);
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
		node.setProperty("tipo", Pessoa.class.getName());

		index.index(node, "tipo", Pessoa.class.getName());
	}

	@Override
	public void atualiza(Pessoa pessoa) {
		Node node = db.getNodeById(pessoa.getId());
		node.setProperty("nome", pessoa.getNome());		
	}
	
}
