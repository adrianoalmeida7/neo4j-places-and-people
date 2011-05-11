package com.ahalmeida.neo4j.model;

import java.util.Calendar;

public class LivedAt {
	private Place place;
	private Person person;

	private Calendar startingAt;
	private Calendar until;

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Calendar getStartingAt() {
		return startingAt;
	}

	public void setStartingAt(Calendar startingAt) {
		this.startingAt = startingAt;
	}

	public Calendar getUntil() {
		return until;
	}

	public void setUntil(Calendar until) {
		this.until = until;
	}

}
