package com.cycle.pricing.engine.dao.factory;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cycle.pricing.engine.dao.CyclePricingEngineDao;

public class CyclePricingEngineDaoFactory {

	public CyclePricingEngineDao cyclePricingEngineDaoFactory(){
		ConfigurableApplicationContext cap = new ClassPathXmlApplicationContext("resources/cycle_spring.xml");
		CyclePricingEngineDao cyclePricingEngineDao = (CyclePricingEngineDao)cap.getBean("cyclePricingEngineDaoImpl");
		cap.close();
		return cyclePricingEngineDao;
	}
}
