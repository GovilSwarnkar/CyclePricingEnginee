package com.cycle.pricing.engine.cost.calcuation;

import java.util.concurrent.ExecutionException;

public interface CyclePriceMutlithred {
	public void findingPriceOfCycleMultiThreaded( String cycleRequest, int number_of_cycle) throws InterruptedException, ExecutionException;
}
