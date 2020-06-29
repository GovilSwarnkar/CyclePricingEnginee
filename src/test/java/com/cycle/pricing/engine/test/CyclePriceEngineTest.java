package com.cycle.pricing.engine.test;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.cycle.pricing.engine.client.find.price.FindPriceOfCycleImpl;


public class CyclePriceEngineTest {

	@Test
	public void findPriceOfCycle() {

		FindPriceOfCycleImpl findPriceOfCycleImpl = new FindPriceOfCycleImpl();
		String cycleRequest = null;
		try {
			String inputFilePath = "resources/cycleJson.json";
			cycleRequest = findPriceOfCycleImpl.getRequest(inputFilePath);
		} catch (Exception e1) {
			System.out.println("getting error while try to test");
			e1.printStackTrace();
		}

		String cycleCostWithComponentCostActul = new FindPriceOfCycleImpl().findPriceOfCycle(cycleRequest);

		String expected = "[{\"price\":1730,\"components\":[{\"component_id\":1,\"cost\":430},{\"component_id\":2,\"cost\":1300}]}]";

		try {
			JSONAssert.assertEquals(expected, cycleCostWithComponentCostActul, true);
		} catch (JSONException e) {
			System.out.println("getting error while junit testing");
			e.printStackTrace();
		}
	}

}
