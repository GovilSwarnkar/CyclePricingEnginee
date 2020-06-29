package com.cycle.pricing.engine.dao;

import java.util.List;
import java.util.Map;

public interface CyclePricingEngineDao {

	public Map<Integer, Double> getPerPartCost(List<Integer> partIds, String dateOfPricing);
	
}
