package com.cycle.pricing.engine.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("cycles")
public class Cycle {

	@JsonProperty("components")
	private List<Component> components;

	@JsonProperty("pricing_date")
	private String pricingDate;

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public String getPricingDate() {
		return pricingDate;
	}

	public void setPricingDate(String pricingDate) {
		this.pricingDate = pricingDate;
	}

}
