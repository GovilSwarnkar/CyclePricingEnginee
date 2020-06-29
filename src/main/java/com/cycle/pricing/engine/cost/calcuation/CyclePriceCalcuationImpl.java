package com.cycle.pricing.engine.cost.calcuation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.cycle.pricing.engine.dao.CyclePricingEngineDao;
import com.cycle.pricing.engine.dao.factory.CyclePricingEngineDaoFactory;
import com.cycle.pricing.engine.model.Component;
import com.cycle.pricing.engine.model.Cycle;
import com.cycle.pricing.engine.model.Part;
import com.cycle.pricing.engine.utils.CycleEngineUtils;

public class CyclePriceCalcuationImpl implements CyclePriceCalcuation {

	public String cycleCostWithComponentCostCalculation(String cycleRequest) {
		try {

			List<Cycle> cycles = new CycleEngineUtils().getCycles(cycleRequest);
			
			JSONArray cyclePriceWithComponentCostList = new JSONArray();
			
			for(Cycle cycle : cycles) {
				// for getting all component-part ids
				List<Component> components = cycle.getComponents();
				List<Part> parts = new ArrayList<Part>();
				List<Integer> partIds = new ArrayList<Integer>();
				for (Component component : components) {
					parts = component.getParts();
					for (Part part : parts) {
						partIds.add(part.getPart_id());
					}
				}

				// for getting cost per part cost
				CyclePricingEngineDaoFactory cyclePricingEngineDaoFactory = new CyclePricingEngineDaoFactory();
				CyclePricingEngineDao cyclePricingEngineDao = cyclePricingEngineDaoFactory.cyclePricingEngineDaoFactory();
				Map<Integer, Double> cycleComponentPartCost = cyclePricingEngineDao.getPerPartCost(partIds,
						cycle.getPricingDate());
				System.out.println("cost per par parts : " + cycleComponentPartCost);

				// for finding cost of components and price of cycle
				List<Integer> partIdPerCostList = new ArrayList<Integer>();
				partIdPerCostList.addAll(cycleComponentPartCost.keySet());
				double componentCost;
				Map<Integer, Double> perComponentCostMap = new HashMap<Integer, Double>();
				double cyclePrice = 0;
				for (Component component : components) {
					componentCost = 0;
					parts = component.getParts();
					for (Integer partIdPerCost : partIdPerCostList) {
						for (Part part : parts) {
							if (part.getPart_id() == partIdPerCost) {
								componentCost += cycleComponentPartCost.get(partIdPerCost) * part.getNumber_of_part();
							}
						}
					}
					cyclePrice += componentCost;
					perComponentCostMap.put(component.getComponentId(), componentCost);
				}

				System.out.println("cycle cost: " + cyclePrice);
				System.out.println("cost per component: " + perComponentCostMap);

				// preparing json response
				JSONObject cyclePriceWithComponentCost = new JSONObject();
				cyclePriceWithComponentCost.put("price", cyclePrice);

				JSONObject componentJsonObj;
				JSONArray componentsCost = new JSONArray();
				
				for (Map.Entry<Integer, Double> entry : perComponentCostMap.entrySet()) {
					componentJsonObj = new JSONObject();
					componentJsonObj.put("component_id", entry.getKey());
					componentJsonObj.put("cost", entry.getValue());
					componentsCost.put(componentJsonObj);
				}
				
				cyclePriceWithComponentCost.put("components", componentsCost);
				
				cyclePriceWithComponentCostList.put(cyclePriceWithComponentCost);
				System.out.println(this.getClass().getName() + " :: json response for cycle price and components cost: "
						+ cyclePriceWithComponentCost);
			}

			return cyclePriceWithComponentCostList.toString();
		} catch (Exception e) {
			System.out.println("getting while price calculation:" + e);
			e.printStackTrace();
			return null;
		}
	}
}
