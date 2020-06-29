package com.cycle.pricing.engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Part {

	@JsonProperty("part_id")
	private int part_id;

	@JsonProperty("number_of_parts")
	private int number_of_part;

	public int getPart_id() {
		return part_id;
	}

	public int getNumber_of_part() {
		return number_of_part;
	}

	public void setNumber_of_part(int number_of_part) {
		this.number_of_part = number_of_part;
	}

	public void setPart_id(int part_id) {
		this.part_id = part_id;
	}

}
