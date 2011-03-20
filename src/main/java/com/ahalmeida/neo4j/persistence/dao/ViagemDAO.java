package com.ahalmeida.neo4j.persistence.dao;

import java.util.List;

import com.ahalmeida.neo4j.model.Pessoa;
import com.ahalmeida.neo4j.model.Viagem;

public interface ViagemDAO {

	void salva(Viagem viagem);

	List<Viagem> paraOndeViajou(Pessoa pessoa);

}
