package com.ahalmeida.neo4j.model;

import java.util.Calendar;

public class Travel {
	private Place place;
	private Person person;
	private Calendar date;

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place lugar) {
		this.place = lugar;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
}