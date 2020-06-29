package com.cycle.pricing.engine.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultHandler {
	
	public  Object setResults(ResultSet resultSet) throws SQLException;
	
}
