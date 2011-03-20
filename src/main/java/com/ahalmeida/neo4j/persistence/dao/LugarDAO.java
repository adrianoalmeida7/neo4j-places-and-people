package com.ahalmeida.neo4j.persistence.dao;

import java.util.List;

import com.ahalmeida.neo4j.model.Lugar;

public interface LugarDAO {
	List<Lugar> todos();

	void remove(long id);

	Lugar findById(long id);
	
	void salva(Lugar lugar);

	void atualiza(Lugar lugar);
}
