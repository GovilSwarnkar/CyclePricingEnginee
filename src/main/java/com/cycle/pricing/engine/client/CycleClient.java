package com.cycle.pricing.engine.client;

import java.util.Scanner;

import com.cycle.pricing.engine.client.find.price.FindPriceOfCycleImpl;

public class CycleClient {

	public static void main(String[] args) {

		Scanner scanner = null;
		try {
			System.out.println("from client side: : : ");
			
			FindPriceOfCycleImpl findPriceOfCycleImpl = new FindPriceOfCycleImpl();
			scanner = new Scanner(System.in);
			String inputFilePath = "resources/cycleJson.json"; 
			String cycleRequest = findPriceOfCycleImpl.getRequest(inputFilePath);
			String cycleCostWithComponentCost = null;
			System.out.println("1.for finding cycle price, 2. for finding cycle price with multithreading");
			int option = scanner.nextInt();
			switch (option) {
			case 1: {
				System.out.println("fiding cycle price");
				cycleCostWithComponentCost = findPriceOfCycleImpl.findPriceOfCycle(cycleRequest);
				System.out.println("\n\nfrom client side: : : ");
				System.out.println("json response for cycle price and components cost from CyclePriceEngine: "
						+ cycleCostWithComponentCost);
				break;
			}
			case 2: {
				System.out.println("fiding cycle price with mutlithreading");
				System.out.println("enter number of cycle");
				int number_of_cycle = scanner.nextInt();
				findPriceOfCycleImpl.findPriceOfCycle(cycleRequest);
				findPriceOfCycleImpl.findingPriceOfCycleMultiThreaded(cycleRequest, number_of_cycle);
				break;
			}
			default: {
				System.out.println("please choose correct option");
				break;
			}
			}
			scanner.close();
		} catch (Exception e) {
			System.out.println("getting error while try get input");
			e.printStackTrace();
		}
	}

}
