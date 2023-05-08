package com.jdbc;

public class MainApplication {

	public static void main(String[] args) {
		ProductJDBCUtils productJSBCUtils = new ProductJDBCUtils();
		try {
			// update
			// We add a product (p100, cd, 5) in Product and (p100, d2, 50) in Stock.
			// add
			productJSBCUtils.addOrUpdateProductAndStock("p100", "cd", 5, "p100", "d2", 50);

			// The product p1 changes its name to pp1 in Product and Stock.
			productJSBCUtils.addOrUpdateProductAndStock("p1", "ppl", 50, null, null, 0);

			// The product p1 is deleted from Product and Stock.
			productJSBCUtils.deleteProduct("p1");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
