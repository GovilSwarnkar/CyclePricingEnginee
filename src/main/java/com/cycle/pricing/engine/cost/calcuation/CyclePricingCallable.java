package com.cycle.pricing.engine.cost.calcuation;

import java.util.concurrent.Callable;

public class CyclePricingCallable implements Callable<String>{
	
	private String cycleRequest;
	private CyclePriceCalcuation cyclePriceCalcuation;
	
	public CyclePricingCallable(String cycleRequest, CyclePriceCalcuation cyclePriceCalcuation) {
		this.cycleRequest = cycleRequest;
		this.cyclePriceCalcuation = cyclePriceCalcuation;
	}

	@Override
	public String call() throws Exception {
		String cycleResponse = cyclePriceCalcuation.cycleCostWithComponentCostCalculation(cycleRequest);
		return cycleResponse;
	}
}
