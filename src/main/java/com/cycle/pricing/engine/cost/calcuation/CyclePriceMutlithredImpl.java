package com.cycle.pricing.engine.cost.calcuation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CyclePriceMutlithredImpl implements CyclePriceMutlithred {
	
	private static final BlockingQueue<String> cycleQueue = new ArrayBlockingQueue<String>(10);
	
	public void findingPriceOfCycleMultiThreaded(String cycleRequest, int number_of_cycle) throws InterruptedException, ExecutionException {
		getPriceOfCycle(cycleRequest, number_of_cycle);
	}
	
	private void getPriceOfCycle(String cycleRequest, int number_of_cycle) throws InterruptedException, ExecutionException {
		Thread cyclePriceProducerThread = new Thread(cyclePriceProducer(cycleRequest, number_of_cycle));
		cyclePriceProducerThread.start();
		 Thread cyclePriceConsumerThread = new Thread(cyclePriceConsumer(number_of_cycle));
		 cyclePriceConsumerThread.start();
	}
	
	private void cyclePriceCallable(String cycleRequest, int number_of_cycle) throws InterruptedException, ExecutionException {
		CyclePriceCalcuation cyclePriceCalcuation = new CyclePriceCalcuationImpl();
		int threadPoolSize = 10;
		if(number_of_cycle<=10) {
			threadPoolSize = number_of_cycle;
		}
     	CyclePricingCallable[] jobs = new CyclePricingCallable[number_of_cycle];
     	for(int i = 0; i < jobs.length; i++) {
     		jobs[i] = new CyclePricingCallable(cycleRequest, cyclePriceCalcuation);
     	}
     	ExecutorService service  = Executors.newFixedThreadPool(threadPoolSize);
     	for(CyclePricingCallable job : jobs) {
     		Future<String> futureCycleResponse = service.submit(job);
     		cycleQueue.add(futureCycleResponse.get());
     	}
     	service.shutdown();
	}
	
	private Runnable cyclePriceProducer(String cycleRequest, int number_of_cycle) {
		Runnable cycleRunnable = () -> {
			try {
				cyclePriceCallable(cycleRequest, number_of_cycle);
			} catch (Exception e) {
				System.out.println("got some problem" + e);
				e.printStackTrace();
			}
		};
		return cycleRunnable;
	}
	
	private Runnable cyclePriceConsumer(int number_of_cycle) {
		Runnable cycleRunnable = () -> {
			String cycleResponse="";
			for(int i=0 ;i < number_of_cycle; i++) {
				try {
					cycleResponse = cycleQueue.poll(1, TimeUnit.SECONDS);
					System.out.println("**************cycle cost: " + cycleResponse);
				} catch (InterruptedException e) {
					System.out.println("getting error while polling cycle queue");
					e.printStackTrace();
				}
			}
		};
		return cycleRunnable;
	}
}
