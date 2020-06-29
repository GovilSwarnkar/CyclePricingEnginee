package com.cycle.pricing.engine.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementSetter;

import com.cycle.pricing.engine.utils.JdbcTemplet;
import com.cycle.pricing.engine.utils.ResultHandler;

public class CyclePricingEngineDaoImpl implements CyclePricingEngineDao{

	private JdbcTemplet jdbcTemplet;

	public void setJdbcTemplet(JdbcTemplet jdbcTemplet) {
		this.jdbcTemplet = jdbcTemplet;
	}


	public Map<Integer, Double> getPerPartCost(final List<Integer> partIds, final String dateOfPricing) {
		final Map<Integer, Double> cycleComponentPartCostMap = new HashMap<Integer, Double>(); 
	try {
		final int noOfPart = partIds.size();
		String part_id_setter = "";
		for(int i = 0; i < noOfPart; i++) {
			part_id_setter += "?, ";
		}
		part_id_setter = part_id_setter.substring(0, part_id_setter.length()-2);
		System.out.println("partIds: " + partIds + " and date: " + dateOfPricing);
		String query = "SELECT p.part_id,  pc.cost FROM \r\n" + 
				"components c join \r\n" + 
				"parts p on c.component_id = p.component_id \r\n" + 
				"join part_cost pc on p.part_id = pc.part_id\r\n" + 
				"WHERE pc.part_id IN (" + part_id_setter + ")\r\n" + 
				"AND\r\n" + 
				"(? BETWEEN pc.start_date AND pc.end_date)";
		PreparedStatementSetter preparedStatementSetter = new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				int i = 0;
				for(i = 0; i < noOfPart; i++) {
					preparedStatement.setInt(i + 1, partIds.get(i));	
				}
				preparedStatement.setString(i + 1, dateOfPricing);
			}
		};
		ResultHandler handler = new ResultHandler(){
			public Object setResults(ResultSet resultSet) throws SQLException{
				while(resultSet.next()) {
					cycleComponentPartCostMap.put(resultSet.getInt("part_id") , resultSet.getDouble("cost"));
				}
				return resultSet;
			}
		};
		jdbcTemplet.selectData(query, preparedStatementSetter, handler);
		}catch(Exception e) {
			System.out.println("getting error while try to fetch reocrd:");
			e.printStackTrace();
		}
		return cycleComponentPartCostMap;
	}
}
