package com.cycle.pricing.engine.client.find.price;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.cycle.pricing.engine.cost.calcuation.CyclePriceMutlithred;
import com.cycle.pricing.engine.cost.calcuation.CyclePriceMutlithredImpl;
import com.cycle.pricing.engine.cost.calcuation.CyclePriceCalcuation;
import com.cycle.pricing.engine.cost.calcuation.CyclePriceCalcuationImpl;

public class FindPriceOfCycleImpl implements FindPriceOfCycle{

	public String findPriceOfCycle(String cycleRequest){

		try {
			CyclePriceCalcuation priceCalcuation = new CyclePriceCalcuationImpl();
			String cycleCostWithComponentCost = priceCalcuation.cycleCostWithComponentCostCalculation(cycleRequest);
			if(cycleCostWithComponentCost != null) {
				return cycleCostWithComponentCost;
			}else {
				System.out.println("something went wrong while price calcuation of cycle");
				return null;
			}
			
		} catch (Exception e) {
			System.out.println("getting error while try to find price of cycle" + e);
			e.printStackTrace();
			return null;
		}
	}

	public String getRequest(String inputFilePath) throws IOException, JSONException {

		System.out.println("getting input:::");
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classloader.getResourceAsStream(inputFilePath);
		InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);
		String cycleJsonRequest = "";
		try {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();

			while (line != null) {
				sb.append(line.trim());
				line = reader.readLine();
			}
			cycleJsonRequest = sb.toString();
			System.out.println(cycleJsonRequest);
		} catch (Exception e) {
			System.out.println("getting error while reading json text file: " + e);
			e.printStackTrace();
		} finally {
			reader.close();
		}

		JSONArray cycleJsonObjet = new JSONArray(cycleJsonRequest);
		String cycleRequest = cycleJsonObjet.toString();
		System.out.println("cycle request:: " + cycleRequest);
		return cycleRequest;
	}

	@Override
	public void findingPriceOfCycleMultiThreaded(String cycleRequest, int number_of_cycle) throws InterruptedException, ExecutionException {
		CyclePriceMutlithred cyclePriceMutlithred = new  CyclePriceMutlithredImpl();
		cyclePriceMutlithred.findingPriceOfCycleMultiThreaded(cycleRequest, number_of_cycle);
	}

	
}
