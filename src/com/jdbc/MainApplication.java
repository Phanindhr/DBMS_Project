package com.jdbc;

public class MainApplication {

	public static void main(String[] args) {
		ProductJDBCUtils productJSBCUtils = new ProductJDBCUtils();
		try {
			// update
			// We add a product (p100, cd, 5) in Product and (p100, d2, 50) in Stock.
			// add
			productJSBCUtils.addOrUpdateProductAndStock("p2", "cd", 5, "p2", "d2", 50);

			// The product p1 changes its name to pp1 in Product and Stock.
			productJSBCUtils.addOrUpdateProductAndStock("pp1", "m", 50, "pp1", "d1", 0);

			// The product p1 is deleted from Product and Stock.
			productJSBCUtils.deleteProduct("p2");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
