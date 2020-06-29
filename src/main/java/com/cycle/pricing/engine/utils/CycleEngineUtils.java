package com.cycle.pricing.engine.utils;

import java.util.List;

import com.cycle.pricing.engine.model.Cycle;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CycleEngineUtils {

	public List<Cycle> getCycles(String cycleRequest) {
		ObjectMapper cycleObjectMapper = new ObjectMapper();
		List<Cycle> cycles = null;
		try {
			cycles = cycleObjectMapper.readValue(cycleRequest, new TypeReference<List<Cycle>>() {	
			});
		} catch (Exception e) {
			System.out.println("while maping json with java object: " + e);
			e.printStackTrace();
		}
		return cycles;
	}
	
}
