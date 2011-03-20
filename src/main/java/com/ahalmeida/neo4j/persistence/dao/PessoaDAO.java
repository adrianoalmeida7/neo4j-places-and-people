package com.ahalmeida.neo4j.persistence.dao;

import java.util.List;

import com.ahalmeida.neo4j.model.Pessoa;

public interface PessoaDAO {
	List<Pessoa> todos();

	void remove(long id);

	Pessoa findById(long id);
	
	void salva(Pessoa pessoa);

	void atualiza(Pessoa pessoa);
}
