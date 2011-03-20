package com.ahalmeida.neo4j.model;

import java.util.Calendar;

public class Viagem {
	private Lugar lugar;
	private Pessoa pessoa;
	private Calendar data;

	public Lugar getLugar() {
		return lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Viagem [lugar=" + lugar + ", pessoa=" + pessoa + ", data="
				+ data + "]";
	}
}
