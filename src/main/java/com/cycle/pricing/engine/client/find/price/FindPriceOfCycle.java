package com.cycle.pricing.engine.client.find.price;

import java.util.concurrent.ExecutionException;

public interface FindPriceOfCycle {

	public String findPriceOfCycle(String cycleRequest);
	
	public void findingPriceOfCycleMultiThreaded(String cycleRequest, int number_of_cycle) throws InterruptedException, ExecutionException;
	
}
