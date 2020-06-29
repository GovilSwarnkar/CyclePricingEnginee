package com.cycle.pricing.engine.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Component {

	@JsonProperty("component_id")
	private int componentId;

	@JsonProperty("parts")
	private List<Part> parts;

	public int getComponentId() {
		return componentId;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}
}
