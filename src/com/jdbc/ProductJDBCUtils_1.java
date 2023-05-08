package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductJDBCUtils {
	private Connection conn = null;
	private Statement statement = null;
	private PreparedStatement stmt = null;
	private ResultSet resultSet = null;

	public void addOrUpdateProductAndStock(String prod, String pname, int price, 
			String stockProdId, String dep, int quantity)
			throws Exception {
		try {
			// Load the SQL driver
			Class.forName("org.postgresql.Driver");
			// Connect to the default database with credentials
			conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/postgres?useSSL=false&amp;serverTimezone=UTC", "postgres",
					"Sarath_1990");

			// For atomicity
			conn.setAutoCommit(false);

			// For isolation
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			// Maybe a table student1 exist, maybe not
			// create table student(id integer, name varchar(10), primary key(Id))
			// Either the 2 following inserts are executed, or none of them are. This is
			// atomicity.

			// create sql statement

			String sql = "select * from product where prod=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, prod);

			// execute query
			resultSet = stmt.executeQuery();

			if (!resultSet.next()) {
				// create sql for Product insert (parent)
				sql = "insert into product " + "(prod, pname, price) " + "values (?, ?, ?)";

				stmt = conn.prepareStatement(sql);

				// We add a product (p100, cd, 5) in Product and (p100, d2, 50) in Stock.

				// set the param values for the product
				stmt.setString(1, prod);
				stmt.setString(2, pname);
				stmt.setInt(3, price);

				// execute sql insert
				stmt.execute();

				sql = "insert into stock " + "(prod, dep, quantity) " + "values (?, ?, ?)";
				stmt = conn.prepareStatement(sql);

				// set the param values for the product
				stmt.setString(1, stockProdId);
				stmt.setString(2, dep);
				stmt.setInt(3, quantity);
				// execute sql insert
				stmt.execute();

			} else {
				// The product p1 changes its name to pp1 in Product and Stock.
				// do the product updation

				// create SQL update statement
				sql = "update product " + "set pname=?, price=?" + " where prod=?";

				// prepare statement
				stmt = conn.prepareStatement(sql);

				// set params
				stmt.setString(1, pname);
				stmt.setInt(2, price);
				stmt.setString(3, prod);

				// execute SQL statement
				stmt.execute();
			}

			conn.commit();

		} catch (SQLException e) {
			System.out.println("An exception was thrown");
			// For atomicity
			conn.rollback();
		} finally {
			// clean up JDBC objects
			try {
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}

	public void deleteProduct(String prodId) throws Exception {
		try {
			// Load the MySQL driver
			Class.forName("org.postgresql.Driver");
			// Old driver
			// Class.forName("com.mysql.jdbc.Driver");

			// Connect to the default database with credentials
			conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/postgres?useSSL=false&amp;serverTimezone=UTC", "postgres",
					"Sarath_1990");

			// For atomicity
			conn.setAutoCommit(false);

			// For isolation
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			// create sql to delete stock (child table)
			String sql = "delete from stock where prod=?";

			// prepare statement
			stmt = conn.prepareStatement(sql);
			// set params
			stmt.setString(1, prodId);

			// preparedStatement sql statement
			stmt.execute();

			// create sql to delete product (parent table)
			sql = "delete from product where prod=?";

			// prepare statement
			stmt = conn.prepareStatement(sql);
			// set params
			stmt.setString(1, prodId);

			// execute sql statement
			stmt.execute();

			// Commit the transaction For atomocity, consistency, durability
			conn.commit();

		} catch (SQLException e) {
			System.out.println("An exception was thrown");
			// For atomicity
			conn.rollback();
		} finally {
			// clean up JDBC objects
			try {
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}
}