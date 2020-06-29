package com.cycle.pricing.engine.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.PreparedStatementSetter;


public class JdbcTemplet {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {

		this.dataSource = dataSource;
	}

	public Object selectQuery(String query, PreparedStatementSetter preparedStatementSetter, ResultHandler handler) throws SQLException {
		System.out.println("query: " + query);
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			if(preparedStatementSetter != null) {
				preparedStatementSetter.setValues(preparedStatement);
			}
			System.out.println(this + "select query" + preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();
			System.out.println(this + "query executed");
			return handler.setResults(resultSet);
		} catch (SQLException e) {
			System.out.println("getting error while qauerying: ");
			e.printStackTrace();
			throw e;
		}finally {
			closeAll(preparedStatement, resultSet, connection);
		}
	}

	public Object selectData(String query, PreparedStatementSetter preparedStatementSetter, ResultHandler handler) throws SQLException{
		return this.selectQuery(query, preparedStatementSetter, handler);
	}
	
	private void closeAll(PreparedStatement preparedStatement, ResultSet resultSet, Connection connection) {
		if(resultSet != null) {
			try {
				resultSet.close();
			}catch(SQLException e) {
				System.out.println("the resultSet cannot be closed: " + e);
			}
		}
		
		if(preparedStatement != null) {
			try {
				preparedStatement.close();
			}catch(SQLException e) {
				System.out.println("the preparedStatement cannot be closed: ");
				e.printStackTrace();
			}
		}
		
		if(connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				System.out.println("the connection cannot be closed: ");
				e.printStackTrace();
			}
		}
	}
}
